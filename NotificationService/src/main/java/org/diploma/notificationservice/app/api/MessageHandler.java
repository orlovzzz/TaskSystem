package org.diploma.notificationservice.app.api;

import org.diploma.notificationservice.domain.Message;

public interface MessageHandler {
    boolean canHandle(Message message);
    void handle(Message message);
}
