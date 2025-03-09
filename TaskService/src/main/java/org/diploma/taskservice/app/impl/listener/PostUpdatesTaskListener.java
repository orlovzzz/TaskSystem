package org.diploma.taskservice.app.impl.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.diploma.taskservice.app.api.ProjectService;
import org.diploma.taskservice.app.api.SecurityService;
import org.diploma.taskservice.app.api.UserService;
import org.diploma.taskservice.app.impl.listener.dto.UserNotification;
import org.diploma.taskservice.app.impl.listener.event.UpdatedTaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostUpdatesTaskListener {
    private static final String TOPIC_NAME = "notifications";
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;
    private final ProjectService projectService;
    private final SecurityService securityService;

    @SneakyThrows
    @EventListener
    public void send(UpdatedTaskEvent event) {
        if (isEmpty(event.getAssigneeLogins())) {
            event.setAssigneeLogins(new ArrayList<>());
        }
        var updater = securityService.getAuthorizedUser();
        var usersNotification = UserNotification.builder()
            .assigneeEmails(userService.getUserEmails(event.getAssigneeLogins()))
            .ownerEmail(userService.getUserEmails(List.of(event.getOwnerLogin())).get(0))
            .eventType(event.getEventType())
            .fieldName(event.getFieldName())
            .oldValue(event.getOldValue())
            .newValue(event.getNewValue())
            .updater(updater.getLogin())
            .taskId(event.getTask().getId())
            .projectName(projectService.getProjectName(event.getTask().getProjectId()))
            .build();
        log.info("Sending message to kafka {}", usersNotification);
        kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(usersNotification));
    }
}