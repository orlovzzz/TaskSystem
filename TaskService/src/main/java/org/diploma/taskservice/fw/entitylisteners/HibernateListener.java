package org.diploma.taskservice.fw.entitylisteners;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diploma.taskservice.app.impl.listener.TaskInsertListener;
import org.diploma.taskservice.app.impl.listener.TaskUpdateListener;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Service;

//public class EventListenerIntegration implements Integrator {
//    @Override
//    public void integrate(
//        @NonNull Metadata metadata,
//        @NonNull BootstrapContext bootstrapContext,
//        @NonNull SessionFactoryImplementor sessionFactory)
//    {
//        var registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//        registry.appendListeners(EventType.POST_INSERT, TaskInsertListener.class);
//        registry.appendListeners(EventType.POST_UPDATE, TaskUpdateListener.class);
//    }
//
//    @Override
//    public void disintegrate(@NonNull SessionFactoryImplementor sessionFactoryImplementor,
//        @NonNull SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
//    }
//}
@RequiredArgsConstructor
@Service
@Slf4j
public class HibernateListener {
    private final TaskInsertListener taskInsertListener;
    private final TaskUpdateListener taskUpdateListener;
    private final EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
        var sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        var registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        if (registry == null) {
            log.error("Registry is null");
            return;
        }
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(taskInsertListener);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(taskUpdateListener);
    }
}