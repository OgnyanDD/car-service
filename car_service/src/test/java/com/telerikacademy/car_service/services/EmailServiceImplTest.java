package com.telerikacademy.car_service.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Mock
    JavaMailSender mailSender;

    @Mock
    MimeMessage mimeMessage;

    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    public void sendMessageWithAttachment_shouldSendEmailMessage() {

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendMessageWithAttachment("user@user.cs", "test", "test");

        Mockito.verify(mailSender, times(1)).createMimeMessage();
    }
}