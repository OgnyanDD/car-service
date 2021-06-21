package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.services.contracts.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


//Source: https://www.baeldung.com/spring-email?fbclid=IwAR0AJDtlo-zQp8pHyqtefvJ0ASSP1ZSz5gE-CSPuMETQ86Wbuwgzau5ggm4
@Component
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text) {

        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
