package com.mail.mail_service.RabbitListener;

import com.mail.mail_service.MailService.EmailService;
import com.mail.mail_service.Model.OrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderListener {


    private EmailService emailService;

    public OrderListener(EmailService emailService) {
        this.emailService = emailService;
    }
    @RabbitListener(queues = "order-mail-queue")
    public void recieveMessageFromQueue(OrderMessage orderMessage){
        String emailText = "Merhaba " +
                "\n Sipariş başarılı şekilde alındı." +
                "\n Ürün: " + orderMessage.getProductName() +
                "\n Fiyat: " + orderMessage.getPrice() +
                "\n Bizi tercih ettiğiniz için teşekkürler.";
        emailService.sendEmail(orderMessage.getEmail(), emailText);

        System.out.println("Mail gonderildi");
    }
}