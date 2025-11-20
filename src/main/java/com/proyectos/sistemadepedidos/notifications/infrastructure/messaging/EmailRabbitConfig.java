package com.proyectos.sistemadepedidos.notifications.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailRabbitConfig {

    @Value("${rabbitmq.email.exchange}")
    private String emailExchangeName;

    @Value("${rabbitmq.email.recovery-password.queue}")
    private String recoveryQueueName;

    @Value("${rabbitmq.email.recovery-password.routing-key}")
    private String recoveryRoutingKey;

    @Value("${rabbitmq.email.order.queue}")
    private String orderQueueName;

    @Value("${rabbitmq.email.order.routing-key}")
    private String orderRoutingKey;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(emailExchangeName, true, false);
    }

    @Bean
    public Queue recoveryPasswordQueue() {
        return QueueBuilder.durable(recoveryQueueName).build();
    }

    @Bean
    public Binding recoveryPasswordBinding() {
        return BindingBuilder
                .bind(recoveryPasswordQueue())
                .to(emailExchange())
                .with(recoveryRoutingKey);
    }

    @Bean
    public Queue orderEmailQueue() {
        return QueueBuilder.durable(orderQueueName).build();
    }

    @Bean
    public Binding orderEmailBinding() {
        return BindingBuilder
                .bind(orderEmailQueue())
                .to(emailExchange())
                .with(orderRoutingKey);
    }
}
