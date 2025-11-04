package Jar.userservice.patterns.observer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener implements UserEventListener {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationListener.class);

    @Override
    public void onUserEvent(UserEvent event) {
        logger.info("Sending email notification for event: {} to user: {}",
                event.getEventType(), event.getUser().getEmail());
        // Aquí iría la lógica real de envío de email
    }
}

