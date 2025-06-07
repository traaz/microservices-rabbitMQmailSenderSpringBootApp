package com.mail.mail_service.MailService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@tepsiburada.com");
        message.setTo(to);
        message.setSubject("Siparişiniz Oluşturuldu :)");
        message.setText(text);
        mailSender.send(message);
    }

}