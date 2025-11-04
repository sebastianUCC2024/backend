package Jar.userservice.patterns.observer;

import Jar.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserEvent {
    private String eventType;
    private User user;
    private LocalDateTime timestamp;
}