package com.telerikacademy.car_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration for e-mail client, with the help of which each newly registered user in the application will receive
 * information necessary for authentication.
 *
 * The email client used is Gmail.
 *
 * For the proper functioning of the application it is necessary:
 * 1. The properties "app.email.user" and "app.email.password" in the file application.properties to be set;
 * 2. Access to "More insecure applications" must be allowed from the email account settings in Gmail;
 *
 * @author  Ognyan Dimitrov
 * @see org.springframework.mail
 * @since   2021-06-20
 */

@Configuration
public class EmailConfig {

    @Value("${app.email.user}")
    private String appEmail;

    @Value("${app.email.password}")
    private String appPassword;

    private static final String EMAIL_REGISTER_MESSAGE =
            "<p>Dear Customer,</p>\n" +
                    "<p>Welcome to <span style=\"font-weight: bold; height: 20px\">\"Car Service\"</span>!</p>\n" +
                    "<p>We have created your personal account:</p>\n" +
                    "<div style=\"background: rgba(0, 0, 0, 0.1); border: 2px solid black; border-radius: 10px; width: 300px; overflow: auto;\">\n" +
                    "    <p style=\"margin-left: 5px;\"><u>USERNAME</u></p>\n" +
                    "    <p style=\"margin-left: 5px; margin-right: 5px; font-weight: bold; background: rgba(0, 0, 0, 0.2);\">%s</p>\n" +
                    "    <p style=\"margin-left: 5px\"><u>PASSWORD</u></p>\n" +
                    "    <p style=\"margin-left: 5px; margin-right: 5px; font-weight: bold; background: rgba(0, 0, 0, 0.2);\">%s</p>\n" +
                    "</div>\n" +
                    "<p style=\"font-style: italic\">You can change your temporary password after your first login.</p>\n" +
                    "<p>Kind regards,</p>\n" +
                    "<p style=\"font-weight: bold\">Car Service Team</p>\n" +
                    "<a href=\"https://imgbb.com/\"><img src=\"https://i.ibb.co/9V4pjq3/footer-logo.png\" alt=\"footer-logo\" border=\"0\"></a>";

    @Bean
    public SimpleMailMessage templateSimpleMessage() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(EMAIL_REGISTER_MESSAGE);

        return message;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(appEmail);
        mailSender.setPassword(appPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
