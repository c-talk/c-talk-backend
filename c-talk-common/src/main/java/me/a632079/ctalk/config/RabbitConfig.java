package me.a632079.ctalk.config;

import com.rabbitmq.client.ShutdownSignalException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: RabbitConfig
 * @description: RabbitConfig - rabbitMq配置
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@Data
@Configuration
@ConfigurationProperties("spring.rabbitmq")
public class RabbitConfig {

    private String  host;
    private Integer port;
    private String  username;
    private String  password;
    private String  virtualHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setConnectionTimeout(3000);

        connectionFactory.setChannelCacheSize(10);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);

        connectionFactory.setPublisherReturns(true);

        connectionFactory.addConnectionListener(new ConnectionListener() {

            @Override
            public void onCreate(Connection connection) {
            }

            @Override
            public void onClose(Connection connection) {
                log.info("[连接关闭] {}", connection.getDelegate().getId());
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                log.info("{}", signal.getMessage());
            }

            @Override
            public void onFailed(Exception exception) {
                log.error(exception.getMessage());
            }
        });
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }
}
