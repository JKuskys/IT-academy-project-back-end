package com.project.service;

import com.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
    }

    @Override
    public void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(recipient);
        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);
    }

    @Override
    public void constructResetTokenEmail(String contextPath, String token, User user) {
        String url = String.format("%s/change-password;id=%d;token=%s", contextPath, user.getId(), token);
        String message = messageSource.getMessage("emailService.passwordResetMessage", null, null);
        sendEmail(user.getEmail(), messageSource.getMessage("emailService.passwordResetHeader", null, null),
                message + "\r\n" + url);
    }
}
