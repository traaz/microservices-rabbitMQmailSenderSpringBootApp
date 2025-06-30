package com.order.order_service.RabbitMQConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //kanal acmasini spring amqb otomatik yapar.

    public static final String EXCHANGE_NAME = "order-exchange";
    public static final String ROUTING_KEY = "order.created";
    public static final String QUEUE_NAME = "order-mail-queue";


    //dead letter exchange
    public static final String DLX_EXHANGE_NAME = "order-dlx-exchange";
    //dead letter queue
    public static final String DLQ_ROUTING_KEY = "order.dlx.routing";
    public static final String DLQ_NAME = "order-mail-dlq";


    @Bean(name = "orderExchange")
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    //dlx icin gelismis ozell. icin queuebuilder kullandim
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-max-length", 1)
                .deadLetterExchange(DLX_EXHANGE_NAME)
                .deadLetterRoutingKey(DLQ_ROUTING_KEY)
                .build();
    }

    //kuyrukla excahnge baglantisi
    @Bean
    public Binding binding(Queue queue, @Qualifier("orderExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
    }


    //for dlx
    @Bean(name = "orderDlxExchange")
    public DirectExchange dlxExchange(){
        return new DirectExchange(DLX_EXHANGE_NAME);
    }

    @Bean
    public Queue dlxQueue(){
     //   return new Queue(DLQ_NAME);
        return QueueBuilder.durable(DLQ_NAME)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)   //dlx'ten ana kuyurga
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY)  // Ana routing key
                .build();
    }

    @Bean
    public Binding dlxBinding(Queue dlxQueue, @Qualifier("orderDlxExchange") DirectExchange dlxDirectExchange){
        return BindingBuilder.bind(dlxQueue).to(dlxDirectExchange).with(DLQ_ROUTING_KEY);
    }




    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}