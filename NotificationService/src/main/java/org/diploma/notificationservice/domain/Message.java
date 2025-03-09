package org.diploma.notificationservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.diploma.notificationservice.domain.enums.MessageType;

import java.util.List;

@Data
@ToString
public class Message {
    private List<String> assigneeEmails;
    private String ownerEmail;
    private String fieldName;
    private Object oldValue;
    private Object newValue;
    @JsonProperty("eventType")
    private MessageType messageType;
    private Long taskId;
    private String projectName;
    private String updater;
}
