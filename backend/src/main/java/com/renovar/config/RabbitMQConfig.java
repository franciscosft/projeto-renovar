package com.renovar.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class RabbitMQConfig {

    public static final String QUEUE       = "reading.queue";
    public static final String EXCHANGE    = "reading.exchange";
    public static final String ROUTING_KEY = "reading.key";

    @Bean
    public Queue readingQueue() {
        return new Queue(QUEUE, true); // durable
    }

    @Bean
    public TopicExchange readingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding readingBinding(Queue readingQueue, TopicExchange readingExchange) {
        return BindingBuilder.bind(readingQueue).to(readingExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonMessageConverter());
        return template;
    }

}