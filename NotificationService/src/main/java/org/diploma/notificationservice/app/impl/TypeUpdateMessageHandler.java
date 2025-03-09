package org.diploma.notificationservice.app.impl;

import lombok.RequiredArgsConstructor;
import org.diploma.notificationservice.app.api.EmailSender;
import org.diploma.notificationservice.app.api.MessageHandler;
import org.diploma.notificationservice.domain.Message;
import org.diploma.notificationservice.domain.Notification;
import org.springframework.stereotype.Component;

import static org.diploma.notificationservice.domain.enums.MessageType.UPDATE;

@Component
@RequiredArgsConstructor
public class TypeUpdateMessageHandler implements MessageHandler {
    private final EmailSender emailSender;

    @Override
    public boolean canHandle(Message message) {
        return message.getMessageType() == UPDATE;
    }

    @Override
    public void handle(Message message) {
        var to = message.getAssigneeEmails();
        to.add(message.getOwnerEmail());
        var subject = "Изменение по задаче";
        var body = "Задача " + message.getProjectName() + "-" + message.getTaskId() + " была изменена." +
            "\nАвтор изменений: " + message.getUpdater() +
            "\nИзменено: " + message.getFieldName() +
            "\nБыло: " + message.getOldValue() +
            "\nСтало: " + message.getNewValue();
        emailSender.send(new Notification(to, subject, body));
    }
}
