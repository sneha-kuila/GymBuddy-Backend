package com.arslan.gymbuddy.controller;

import com.arslan.gymbuddy.dto.AuthenticationRequest;
import com.arslan.gymbuddy.dto.AuthenticationResponse;
import com.arslan.gymbuddy.dto.FirebaseLoginRequest;
import com.arslan.gymbuddy.dto.RegisterRequest;
import com.arslan.gymbuddy.security.AuthenticationService;
import com.arslan.gymbuddy.service.FirebaseAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    private final FirebaseAuthService firebaseAuthService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    
    @PostMapping("/firebase-login")
    public ResponseEntity<AuthenticationResponse> firebaseLogin(
            @Valid @RequestBody FirebaseLoginRequest request) {
        AuthenticationResponse response =
                firebaseAuthService.loginWithFirebase(request.getIdToken());
        return ResponseEntity.ok(response);
    }
}
