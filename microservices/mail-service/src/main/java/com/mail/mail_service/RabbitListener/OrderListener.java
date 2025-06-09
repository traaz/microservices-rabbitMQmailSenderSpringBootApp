package com.mail.mail_service.RabbitListener;

import com.mail.mail_service.MailService.EmailService;
import com.mail.mail_service.Model.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderListener {
    private static final Logger log = LoggerFactory.getLogger(OrderListener.class);


    private EmailService emailService;

    public OrderListener(EmailService emailService) {
        this.emailService = emailService;
    }
    @RabbitListener(queues = "order-mail-queue")
    public void recieveMessageFromQueue(OrderMessage orderMessage){
        try{
            String emailText = "Merhaba " +
                    "\n Sipariş başarılı şekilde alındı." +
                    "\n Ürün: " + orderMessage.getProductName() +
                    "\n Fiyat: " + orderMessage.getPrice() +
                    "\n Bizi tercih ettiğiniz için teşekkürler.";
            emailService.sendEmail(orderMessage.getEmail(), emailText);
            log.info("Mail gonderildi to: {}", orderMessage.getEmail());
        } catch (Exception e) {
            log.error("Mail gonderilmedi to: {}", orderMessage.getEmail());

            throw new RuntimeException("Mail gonderiminde hata..");
        }



    }
}