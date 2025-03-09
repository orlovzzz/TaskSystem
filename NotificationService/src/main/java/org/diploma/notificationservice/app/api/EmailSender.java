package org.diploma.notificationservice.app.api;

import org.diploma.notificationservice.domain.Notification;

public interface EmailSender {
    void send(Notification notification);
}
