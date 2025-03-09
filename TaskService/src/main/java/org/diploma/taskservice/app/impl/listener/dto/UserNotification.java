package org.diploma.taskservice.app.impl.listener.dto;

import lombok.Builder;
import lombok.Data;
import org.diploma.taskservice.app.impl.listener.event.EventType;

import java.util.List;

@Data
@Builder
public class UserNotification {
    private List<String> assigneeEmails;
    private String ownerEmail;
    private String fieldName;
    private Object oldValue;
    private Object newValue;
    private String updater;
    private EventType eventType;
    private Long taskId;
    private String projectName;
}
