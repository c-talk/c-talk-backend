package me.a632079.ctalk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: ExchangeAndQueueConfig
 * @description: ExchangeAndQueueConfig - 交换机和队列配置
 * @version: v1.0.0
 * @author: haoduor
 */
@Slf4j
@Component
public class ExchangeAndQueueConfig {

    @Resource
    private RabbitAdmin rabbitAdmin;

    /**
     * 生成交换机 绑定队列
     *
     * @param exchange   交换机
     * @param routingKey 路由
     * @param queueName  队列
     */
    public void createDirectBindQueue(String exchange, String routingKey, String queueName) {
        DirectExchange directExchange = new DirectExchange(exchange, true, false);
        Queue queue = new Queue(queueName, false, false, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange)
                                        .with(routingKey);

        //创建
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        log.info("创建私聊交换机{} 队列{}", exchange, queueName);
    }

    public void removeDirectBindQueue(String exchange, String routingKey, String queueName) {
        DirectExchange directExchange = new DirectExchange(exchange, true, false);
        Queue queue = new Queue(queueName, false, false, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange)
                                        .with(routingKey);
        //删除
        rabbitAdmin.removeBinding(binding);
        rabbitAdmin.deleteQueue(queueName);
    }

    public void createFanoutBindQueue(String exchange, String queueName) {
        FanoutExchange directExchange = new FanoutExchange(exchange, false, false);
        Queue queue = new Queue(queueName, false, false, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange);

        //创建
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        log.info("创建群聊交换机{} 队列{}", exchange, queueName);
    }

    public void removeFanoutBindQueue(String exchange, String queueName) {
        FanoutExchange directExchange = new FanoutExchange(exchange, false, false);
        Queue queue = new Queue(queueName, false, false, true);
        Binding binding = BindingBuilder.bind(queue)
                                        .to(directExchange);

        //删除
        rabbitAdmin.removeBinding(binding);
        rabbitAdmin.deleteQueue(queueName);
    }

    public void createPrivateMessageBind(Long uid) {
        this.createDirectBindQueue("message.private", "user.private." + uid, "user.private." + uid);
    }

    public void removePrivateMessageBind(Long uid) {
        this.removeDirectBindQueue("message.private", "user.private." + uid, "user.private." + uid);
    }

    public void createGroupMessageBind(Long gid, Long uid) {
        this.createFanoutBindQueue("message.group." + gid, "user.group." + uid);
    }

    public void removeGroupMessageBind(Long gid, Long uid) {
        this.removeFanoutBindQueue("message.group." + gid, "user.group." + uid);
    }
}
