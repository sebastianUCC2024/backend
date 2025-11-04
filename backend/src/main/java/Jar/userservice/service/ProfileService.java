package Jar.userservice.service;

import Jar.userservice.dto.ProfileResponse;
import Jar.userservice.model.Profile;
import Jar.userservice.model.User;
import Jar.userservice.repository.ProfileRepository;
import Jar.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public ProfileResponse getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToProfileResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long userId, Map<String, Object> updates) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (updates.containsKey("location")) {
            profile.setLocation((String) updates.get("location"));
        }
        if (updates.containsKey("phoneNumber")) {
            profile.setPhoneNumber((String) updates.get("phoneNumber"));
        }
        if (updates.containsKey("address")) {
            profile.setAddress((String) updates.get("address"));
        }
        if (updates.containsKey("paymentMethods")) {
            profile.setPaymentMethods((Map<String, Object>) updates.get("paymentMethods"));
        }
        if (updates.containsKey("specificData")) {
            profile.setSpecificData((Map<String, Object>) updates.get("specificData"));
        }

        profile = profileRepository.save(profile);
        return mapToProfileResponse(profile);
    }

    private ProfileResponse mapToProfileResponse(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .location(profile.getLocation())
                .phoneNumber(profile.getPhoneNumber())
                .address(profile.getAddress())
                .paymentMethods(profile.getPaymentMethods())
                .specificData(profile.getSpecificData())
                .build();
    }
}
