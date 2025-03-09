package org.diploma.taskservice.app.impl.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.taskservice.app.impl.listener.event.EventType;
import org.diploma.taskservice.app.impl.listener.event.UpdatedTaskEvent;
import org.diploma.taskservice.entity.Task;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskInsertListener implements PostInsertEventListener {
    private final ApplicationEventPublisher publisher;

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        if (!(postInsertEvent.getEntity() instanceof Task task)) {
            return;
        }
        var newEvent = new UpdatedTaskEvent(EventType.CREATE, task, null, null, task.getName(), task.getOwner(), task.getAssignee());
        publisher.publishEvent(newEvent);
        log.info("Post insert {}", newEvent);
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
