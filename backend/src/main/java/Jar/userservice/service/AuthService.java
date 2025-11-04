package Jar.userservice.service;

import Jar.userservice.dto.*;
import Jar.userservice.model.Profile;
import Jar.userservice.model.User;
import Jar.userservice.patterns.factory.ProfileFactory;
import Jar.userservice.patterns.observer.UserEventPublisher;
import Jar.userservice.repository.ProfileRepository;
import Jar.userservice.repository.UserRepository;
import Jar.userservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserEventPublisher eventPublisher;

    @Transactional
    public JwtAuthResponse register(RegisterRequest request) {
        // Validar que el usuario no exista
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Crear usuario usando Builder Pattern
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

        user = userRepository.save(user);

        // Crear perfil usando Factory Pattern
        Profile profile = ProfileFactory.createProfile(request.getRole(), user);
        profile.setLocation(request.getLocation());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());

        profileRepository.save(profile);

        // Notificar evento usando Observer Pattern
        eventPublisher.publishUserCreated(user);

        // Autenticar y generar token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return new JwtAuthResponse(token, mapToUserResponse(user, profile));
    }

    public JwtAuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);

        return new JwtAuthResponse(token, mapToUserResponse(user, profile));
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
