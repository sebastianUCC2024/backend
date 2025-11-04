package Jar.userservice.dto;

import Jar.userservice.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private ProfileResponse profile;
}
