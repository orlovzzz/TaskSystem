package org.diploma.notificationservice.app.impl;

import lombok.RequiredArgsConstructor;
import org.diploma.notificationservice.app.api.EmailSender;
import org.diploma.notificationservice.app.api.MessageHandler;
import org.diploma.notificationservice.domain.Message;
import org.diploma.notificationservice.domain.Notification;
import org.springframework.stereotype.Component;

import static org.diploma.notificationservice.domain.enums.MessageType.CREATE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class TypeCreateMessageHandler implements MessageHandler {
    private final EmailSender emailSender;

    @Override
    public boolean canHandle(Message message) {
        return message.getMessageType() == CREATE && !isEmpty(message.getAssigneeEmails());
    }

    @Override
    public void handle(Message message) {
        var to = message.getAssigneeEmails();
        var subject = "Новая задача";
        var body = "Новая задача " + message.getProjectName() + "-" + message.getTaskId() + "\nАвтор задачи: " + message.getUpdater();
        emailSender.send(new Notification(to, subject, body));
    }
}
