package Jar.userservice.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ProfileResponse {
    private Long id;
    private String location;
    private String phoneNumber;
    private String address;
    private Map<String, Object> paymentMethods;
    private Map<String, Object> specificData;
}

