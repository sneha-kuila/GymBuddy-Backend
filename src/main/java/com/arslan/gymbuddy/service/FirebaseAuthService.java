package com.arslan.gymbuddy.service;

import com.arslan.gymbuddy.dto.AuthenticationResponse;
import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.repository.UserRepository;
import com.arslan.gymbuddy.config.JwtService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationResponse loginWithFirebase(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            String email = decodedToken.getEmail();
            if (email == null) {
                throw new IllegalArgumentException("Firebase token does not contain an email");
            }

            String name = (String) decodedToken.getClaims()
                    .getOrDefault("name", email);
            
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> userRepository.save(
                            User.builder()
                                    .email(email)
                                    .fullName(name)
                                    .password("")
                                    .build()
                    ));
            
            String jwtToken = jwtService.generateToken(user);
            
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Invalid Firebase ID token", e);
        }
    }
}
