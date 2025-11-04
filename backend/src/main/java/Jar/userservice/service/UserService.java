package Jar.userservice.service;

import Jar.userservice.dto.UserResponse;
import Jar.userservice.dto.ProfileResponse;
import Jar.userservice.model.User;
import Jar.userservice.model.Profile;
import Jar.userservice.model.enums.Role;
import Jar.userservice.repository.UserRepository;
import Jar.userservice.repository.ProfileRepository;
import Jar.userservice.datastructures.UserLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    // Estructura de datos personalizada para caché de usuarios
    private UserLinkedList userCache = new UserLinkedList();

    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);
        return mapToUserResponse(user, profile);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);
                    return mapToUserResponse(user, profile);
                })
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(user -> {
                    Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);
                    return mapToUserResponse(user, profile);
                })
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);
        return mapToUserResponse(user, profile);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, User updateData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateData.getEmail() != null) {
            user.setEmail(updateData.getEmail());
        }
        if (updateData.getEnabled() != null) {
            user.setEnabled(updateData.getEnabled());
        }

        user = userRepository.save(user);
        Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);
        return mapToUserResponse(user, profile);
    }

    private UserResponse mapToUserResponse(User user, Profile profile) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .profile(profile != null ? mapToProfileResponse(profile) : null)
                .build();
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
