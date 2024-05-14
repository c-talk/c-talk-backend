package me.a632079.ctalk.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.UserInfo;

/**
 * @className: GroupMessageConsumer
 * @description: GroupMessageConsumer - 群组消息消费
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
public class GroupMessageConsumer extends DefaultConsumer {

    private final UserInfo userInfo;

    public GroupMessageConsumer(Channel channel, UserInfo userInfo) {
        super(channel);
        this.userInfo = userInfo;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) {
        String content = new String(body);
        log.info("用户{} 收到 群组消息 {}", userInfo.getId(), content);

        userInfo.getClient()
                .sendEvent("group", content);
    }
}
