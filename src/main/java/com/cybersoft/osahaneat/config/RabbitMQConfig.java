package com.cybersoft.osahaneat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    // Tạo ra queue trên hệ thống rabbitmq
    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    // Tạo ra Exchange trên hệ thống rabbitMQ
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    //Tạo binding liên kết exchange với queue
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }
}
