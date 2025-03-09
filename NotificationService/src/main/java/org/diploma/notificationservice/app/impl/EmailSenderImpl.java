package org.diploma.notificationservice.app.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.diploma.notificationservice.app.api.EmailSender;
import org.diploma.notificationservice.domain.Notification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    private final JavaMailSender mailSender;

    @Override
    @SneakyThrows
    public void send(Notification notification) {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        helper.setTo(notification.getTo().toArray(String[]::new));
        helper.setSubject(notification.getSubject());
        helper.setText(notification.getBody(), false);
        mailSender.send(message);
    }
}
