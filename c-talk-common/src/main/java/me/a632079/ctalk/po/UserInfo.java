package me.a632079.ctalk.po;

import com.corundumstudio.socketio.SocketIOClient;
import com.rabbitmq.client.Channel;
import lombok.Data;
import org.springframework.amqp.rabbit.connection.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className: UserInfo
 * @description: UserInfo - 用户信息
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class UserInfo {
    private Long id;

    private SocketIOClient client;

    private Channel privateChannel;
    private Channel groupChannel;

    private Connection connection;

    public void close() throws IOException, TimeoutException {
        privateChannel.close();
        groupChannel.close();
        connection.close();
    }
}
