package com.taskloom.controller;

import com.taskloom.entity.UserEntity;
import com.taskloom.model.request.LoginRequest;
import com.taskloom.model.request.RegisterRequest;
import com.taskloom.repository.UserRepository;
import com.taskloom.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String token = jwtService.generate(loginRequest.getUsername(),
                Map.of("roles", "USER"));

        return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "tokenType", "Bearer"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            return ResponseEntity.status(409).body(Map.of("message","Username already in use"));

        if(userRepository.existsByMail(registerRequest.getMail()))
            return ResponseEntity.status(409).body(Map.of("message","Email already in use"));


        userRepository.save(UserEntity
                .builder()
                .username(registerRequest.getUsername())
                .mail(registerRequest.getMail())
                .password(encoder.encode(registerRequest.getPassword()))
                .build());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","User registered successfully"));
    }
}
