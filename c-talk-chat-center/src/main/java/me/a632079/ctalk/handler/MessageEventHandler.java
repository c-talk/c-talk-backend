package me.a632079.ctalk.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.config.ExchangeAndQueueConfig;
import me.a632079.ctalk.consumer.GroupMessageConsumer;
import me.a632079.ctalk.consumer.PrivateMessageConsumer;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.po.Token;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.repository.GroupMemberRepository;
import me.a632079.ctalk.service.LockService;
import me.a632079.ctalk.service.TokenService;
import me.a632079.ctalk.vo.IdForm;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @className: MessageEventHandler
 * @description: MessageEventHandler - 事件消息接收器
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@Component
public class MessageEventHandler {

    @Resource
    private TokenService tokenService;

    @Resource
    private ConcurrentHashMap<Long, UserInfo> userInfoMap;

    @Resource
    private ExchangeAndQueueConfig exchangeAndQueueConfig;

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource
    private GroupMemberRepository groupMemberRepository;

    @Resource
    private LockService lockService;

    /**
     * 成功创建连接时调用
     *
     * @param client 用户连接
     */
    @OnConnect
    public void onConnect(SocketIOClient client) throws IOException, TimeoutException {
        String token = client.getHandshakeData()
                             .getSingleUrlParam("token");

        Optional<Token> tokenOptional = tokenService.getToken(token);
        // 令牌不存在
        if (tokenOptional.isEmpty()) {
            //TODO 异常原因返回
            client.disconnect();
        }

        Long uid = tokenOptional.get()
                                .getUid();

        log.info("用户: {} 接入服务器", uid);
        lockService.lock("user#" + uid, () -> {
            UserInfo info = userInfoMap.getOrDefault(uid, new UserInfo());

            // 释放之前的连接
            if (Objects.nonNull(info.getId())) {
                log.info("[释放用户连接] uid: {}", info.getId());
                info.close();
            }

            info.setId(uid);
            info.setClient(client);

            client.set("id", uid);
            // 创建消息队列 接受私聊消息
            exchangeAndQueueConfig.createPrivateMessageBind(uid);

            Connection privateConnection = connectionFactory.createConnection();
            privateConnection.getDelegate().setId("user.private." + uid);

            Channel privateChannel = privateConnection.createChannel(true);

            privateChannel.basicConsume("user.private." + uid, true, new PrivateMessageConsumer(privateChannel, info));

            info.setPrivateChannel(privateChannel);
            info.setPrivateConnection(privateConnection);

            Connection groupConnection = connectionFactory.createConnection();
            groupConnection.getDelegate().setId("user.group." + uid);

            Channel groupChannel = privateConnection.createChannel(true);

            // 绑定群组消息队列
            List<GroupMember> members = groupMemberRepository.findAllByUid(uid);
            log.info("用户群组: {}", members);
            for (GroupMember member : members) {
                exchangeAndQueueConfig.createGroupMessageBind(member.getGid(), member.getUid());
            }

            if (!members.isEmpty()) {
                groupChannel.basicConsume("user.group." + uid, true, new GroupMessageConsumer(groupChannel, info));
            }

            info.setGroupChannel(groupChannel);
            info.setGroupConnection(groupConnection);

            // 保存用户连接
            userInfoMap.put(uid, info);

            return 1;
        });
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) throws IOException, TimeoutException {
        Long uid = client.get("id");

        UserInfo info = userInfoMap.getOrDefault(uid, null);

        if (Objects.nonNull(info)) {
            info.close();
            exchangeAndQueueConfig.removePrivateMessageBind(uid);
        }

        userInfoMap.remove(uid);

        log.info("用户: {} 离开服务器", uid);
    }


    @OnEvent("join_group")
    public void addGroup(SocketIOClient client, AckRequest request, IdForm form) throws IOException, TimeoutException {
        Long uid = client.get("id");

        UserInfo info = userInfoMap.getOrDefault(uid, null);

        if (Objects.nonNull(info)) {
            lockService.lock("user#" + uid, () -> {
                log.info("[用户新加入群组] uid:{} gid:{}", uid, form.getId());
                exchangeAndQueueConfig.createGroupMessageBind(form.getId(), uid);

                return 1;
            });
        }
    }
}
