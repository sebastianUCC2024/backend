package Jar.userservice.patterns.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditLogListener implements UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogListener.class);

    @Override
    public void onUserEvent(UserEvent event) {
        logger.info("AUDIT LOG - Event: {}, User: {}, Time: {}",
                event.getEventType(),
                event.getUser().getUsername(),
                event.getTimestamp());
        // Aquí se guardaría en una tabla de auditoría
    }
}
