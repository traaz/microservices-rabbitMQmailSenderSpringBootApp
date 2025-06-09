package com.order.order_service.Service;

import com.order.order_service.DTOs.AddOrderRequest;
import com.order.order_service.DTOs.CustomerResponse;
import com.order.order_service.DTOs.OrderMessage;
import com.order.order_service.DTOs.ProductResponse;
import com.order.order_service.FlientClients.CustomerClient;
import com.order.order_service.FlientClients.ProductClient;
import com.order.order_service.RabbitMQConfig.RabbitMQConfig;
import com.order.order_service.Repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

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
        log.info("Siparis istegi alindi");
        this.orderRepository.addOrder(addOrderRequest);
        log.info("Siparis db'ye yazildi customerId: {}, productId: {}", addOrderRequest.getCustomerId(), addOrderRequest.getProductId());

        CustomerResponse customer;
        try{
            customer = customerClient.getCustomerById(addOrderRequest.getCustomerId());
            log.info("Customer bilgisi alindi email: {}, id:{}", customer.getEmail(),customer.getId());
        } catch (Exception e) {
            log.error("Customer bilgisi alinamadi {}" ,e.getMessage());
            throw new RuntimeException("Customer bilgisi alınamadı");
        }

        ProductResponse product;
        try{
            product = productClient.getProductById(addOrderRequest.getProductId());
            log.info("Product bilgisi alindi id:{}, productName: {}, price: {}", product.getId(), product.getProductName(), product.getPrice());
        } catch (Exception e) {
            log.error("Product bilgisi alinamadi {}" ,e.getMessage());
            throw new RuntimeException("Product bilgisi alınamadı");
        }

        sendMessageToQueue(customer, product);
    }

    public void sendMessageToQueue(CustomerResponse customer, ProductResponse product){
        OrderMessage message= new OrderMessage();
        message.setEmail(customer.getEmail());
        message.setPrice(product.getPrice());
        message.setProductName(product.getProductName());
        log.info("Kuyruk mesaji hazirlandi");

        try{
            // Mesaji kuyruga gonder. messagı otomatik jsona cevircek rabbitmq'daki jsonconverter
            rabbitTemplate.convertAndSend(
                    directExchange.getName(),   //exchange bean oldugu icin bunu boyle de alabilirim. 2. yol RabbitMQConfig.EXCHANGE_NAME
                    RabbitMQConfig.ROUTING_KEY, //routing key bean olmadigi icin bunu boyle aldim.
                    message
            );
            log.info("Mesaj kuyruga iletildi queueName: {}, message: {}", RabbitMQConfig.QUEUE_NAME, message.toString());
        } catch (Exception e) {
            log.error("Mesaj kuyruga iletilmedi: {}", e.getMessage());
            throw new RuntimeException("Mesaj iletilmedi");
        }

    }



}