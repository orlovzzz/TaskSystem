package org.diploma.taskservice.app.impl.listener.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.diploma.taskservice.entity.Task;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdatedTaskEvent {
    private EventType eventType;
    private Task task;
    private String fieldName;
    private Object oldValue;
    private Object newValue;
    private String ownerLogin;
    private List<String> assigneeLogins;
}
