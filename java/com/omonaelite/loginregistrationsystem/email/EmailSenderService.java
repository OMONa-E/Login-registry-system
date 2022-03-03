package com.omonaelite.loginregistrationsystem.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);
    @Autowired
    private final JavaMailSender javaMailSender;
    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            messageHelper.setText(email, true);
            messageHelper.setTo(to);
            messageHelper.setSubject("Confirm your email");
            messageHelper.setFrom("omonaemm555@gmail.com");
            javaMailSender.send(mimeMessage); //Is sent to maildev at localhost:1025
        } catch (MessagingException exception) {
            LOGGER.error("Failed to send email", exception);
            throw new IllegalStateException("failed to send email");
        }
    }
}
