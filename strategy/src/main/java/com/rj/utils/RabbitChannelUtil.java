package com.rj.utils;

import com.rabbitmq.client.ConnectionFactory;

/**
 * 用于动态创建队列
 */
public class RabbitChannelUtil {
    public static ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("121.43.42.151");
        connectionFactory.setPort(9001);
        connectionFactory.setUsername("rj");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/rj");
        return connectionFactory;
    }
}
