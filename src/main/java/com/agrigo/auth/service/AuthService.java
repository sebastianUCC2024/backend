package com.agrigo.auth.service;

import com.agrigo.auth.dto.AuthResponse;
import com.agrigo.auth.dto.LoginRequest;
import com.agrigo.auth.dto.RegisterRequest;
import com.agrigo.auth.entity.User;
import com.agrigo.auth.entity.UserRole;
import com.agrigo.auth.repository.UserRepository;
import com.agrigo.auth.security.JwtUtil;
import com.agrigo.common.exception.BadRequestException;
import com.agrigo.farmer.entity.Farmer;
import com.agrigo.farmer.repository.FarmerRepository;
import com.agrigo.store.entity.Store;
import com.agrigo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .active(true)
                .build();
        
        user = userRepository.save(user);
        
        if (request.getRole() == UserRole.FARMER) {
            Farmer farmer = Farmer.builder()
                    .user(user)
                    .build();
            farmerRepository.save(farmer);
        } else if (request.getRole() == UserRole.STORE) {
            Store store = Store.builder()
                    .user(user)
                    .storeName(request.getFullName())
                    .build();
            storeRepository.save(store);
        }
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        return new AuthResponse(token, user.getUsername(), user.getRole().name(), user.getId());
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        return new AuthResponse(token, user.getUsername(), user.getRole().name(), user.getId());
    }
}
