package Jar.userservice.patterns.observer;

import Jar.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserEventPublisher {

    private List<UserEventListener> listeners = new ArrayList<>();

    @Autowired
    public UserEventPublisher(List<UserEventListener> listeners) {
        this.listeners = listeners;
    }

    public void publishUserCreated(User user) {
        UserEvent event = new UserEvent("USER_CREATED", user, LocalDateTime.now());
        notifyListeners(event);
    }

    public void publishUserUpdated(User user) {
        UserEvent event = new UserEvent("USER_UPDATED", user, LocalDateTime.now());
        notifyListeners(event);
    }

    public void publishUserDeleted(User user) {
        UserEvent event = new UserEvent("USER_DELETED", user, LocalDateTime.now());
        notifyListeners(event);
    }

    private void notifyListeners(UserEvent event) {
        for (UserEventListener listener : listeners) {
            listener.onUserEvent(event);
        }
    }
}
