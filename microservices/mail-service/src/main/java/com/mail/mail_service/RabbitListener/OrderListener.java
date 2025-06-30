package com.mail.mail_service.RabbitListener;

import com.mail.mail_service.MailService.EmailService;
import com.mail.mail_service.Model.OrderMessage;
import com.netflix.spectator.impl.PatternExpr;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderListener {
    private static final Logger log = LoggerFactory.getLogger(OrderListener.class);


    private EmailService emailService;
    private RabbitTemplate rabbitTemplate;
    public OrderListener(EmailService emailService, RabbitTemplate rabbitTemplate) {
        this.emailService = emailService;
        this.rabbitTemplate = rabbitTemplate;
    }
    // manual acknowledge, basicNack, basicReject gibi işlemler yapacaksan: Channel kullanilir
    @RabbitListener(queues = "order-mail-queue", ackMode = "MANUAL")
    public void recieveMessageFromQueue(OrderMessage orderMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try{
            String emailText = "Merhaba " +
                    "\n Sipariş başarılı şekilde alındı." +
                    "\n Ürün: " + orderMessage.getProductName() +
                    "\n Fiyat: " + orderMessage.getPrice() +
                    "\n Bizi tercih ettiğiniz için teşekkürler.";
            emailService.sendEmail(orderMessage.getEmail(), emailText);
            log.info("Mail gonderildi to: {}", orderMessage.getEmail());
            channel.basicAck(deliveryTag, false); //false multiple mesaj degil demek bir mesaj
        } catch (Exception e) {
            log.error("Mail gonderilmedi to: {}", orderMessage.getEmail());
            channel.basicNack(deliveryTag, false, false);  // requeue=false → DLX'e gider
            throw new RuntimeException("Mail gonderiminde hata..");
        }

    }


    @RabbitListener(queues = "order-mail-dlq", ackMode = "MANUAL")
    public void recieveMessageFromDLQ(OrderMessage orderMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try{
            Thread.sleep(5000);
            rabbitTemplate.convertAndSend("order-exchange", "order.created", orderMessage);
            channel.basicAck(deliveryTag, false);
            log.info("Mail DLQ'dan Order kuyruguna  gonderildi to: {}", orderMessage.getEmail());
        } catch (Exception e) {
            log.error("Mail DLQ'dan Order kuyruguna  gonderilmedi to: {}", orderMessage.getEmail());
            channel.basicNack(deliveryTag, false, false);  // requeue=false → DLX'e gider

            throw new RuntimeException("DLQ'dan  Order'a gonderiminde hata..");
        }
    }


}