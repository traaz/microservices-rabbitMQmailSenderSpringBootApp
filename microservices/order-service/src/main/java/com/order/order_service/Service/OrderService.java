package com.order.order_service.Service;

import com.order.order_service.DTOs.AddOrderRequest;
import com.order.order_service.DTOs.CustomerResponse;
import com.order.order_service.DTOs.OrderMessage;
import com.order.order_service.DTOs.ProductResponse;
import com.order.order_service.FlientClients.CustomerClient;
import com.order.order_service.FlientClients.ProductClient;
import com.order.order_service.RabbitMQConfig.RabbitMQConfig;
import com.order.order_service.Repository.OrderRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CustomerClient customerClient;
    private ProductClient productClient;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    public OrderService(OrderRepository orderRepository,
                        RabbitTemplate rabbitTemplate,
                        CustomerClient customerClient,
                        ProductClient productClient,
                        DirectExchange directExchange) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.directExchange = directExchange;
    }
    public void addOrder(AddOrderRequest addOrderRequest) {
        this.orderRepository.addOrder(addOrderRequest);

        CustomerResponse customer = customerClient.getCustomerById(addOrderRequest.getCustomerId());
        ProductResponse product = productClient.getProductById(addOrderRequest.getProductId());

        sendMessageToQueue(customer, product);
    }

    public void sendMessageToQueue(CustomerResponse customer, ProductResponse product){
        OrderMessage message= new OrderMessage();
        message.setEmail(customer.getEmail());
        message.setPrice(product.getPrice());
        message.setProductName(product.getProductName());

        // Mesaji kuyruga gonder. messagı otomatik jsona cevircek rabbitmq'daki jsonconverter
        rabbitTemplate.convertAndSend(
                directExchange.getName(),   //exchange bean oldugu icin bunu boyle de alabilirim. 2. yol RabbitMQConfig.EXCHANGE_NAME
                RabbitMQConfig.ROUTING_KEY, //routing key bean olmadigi icin bunu boyle aldim.
                message
        );
    }



}