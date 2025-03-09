package org.diploma.taskservice.app.impl.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.taskservice.app.impl.listener.event.UpdatedTaskEvent;
import org.diploma.taskservice.entity.Task;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static org.diploma.taskservice.app.impl.listener.event.EventType.UPDATE;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskUpdateListener implements PostUpdateEventListener {
    private final ApplicationEventPublisher publisher;

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (!(event.getEntity() instanceof Task task)) return;

        String[] propertyNames = event.getPersister().getPropertyNames();
        Object[] oldValues = event.getOldState();
        Object[] newValues = event.getState();
        var ownerLogin = task.getOwner();
        var assigneeLogins = task.getAssignee();

        for (int i = 0; i < propertyNames.length; i++) {
            if (oldValues[i] != null && !oldValues[i].equals(newValues[i])) {
                var newEvent = new UpdatedTaskEvent(UPDATE, task, propertyNames[i], oldValues[i], newValues[i], ownerLogin, assigneeLogins);
                log.info("Post update event {}", newEvent);
                publisher.publishEvent(newEvent);
            }
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
