package com.arslan.gymbuddy.config;

import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String token = authorizationHeader.substring(7);
        
        // If already authenticated, skip
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 1) Try your own JWT first
        boolean authenticated = tryAuthenticateWithLocalJwt(token, request);
        
        // 2) If that failed, try Firebase ID token
        if (!authenticated) {
            tryAuthenticateWithFirebase(token, request);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean tryAuthenticateWithLocalJwt(String token, HttpServletRequest request) {
        try {
            String userEmail = jwtService.getUserNameFromToken(token);
            
            if (userEmail == null) {
                return false;
            }
            
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                return true;
            }
            
        } catch (Exception ignored) {
            // If anything goes wrong, just return false and let Firebase try
        }
        return false;
    }
    
    private boolean tryAuthenticateWithFirebase(String token, HttpServletRequest request) {
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
            
            String email = firebaseToken.getEmail();
            if (email == null) {
                return false;
            }
            
            String name = (String) firebaseToken.getClaims()
                    .getOrDefault("name", email);
            
            // Find or create local User
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> userRepository.save(
                            User.builder()
                                    .email(email)
                                    .fullName(name)
                                    .password("")
                                    .build()
                    ));
            
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            return true;
            
        } catch (Exception ignored) {
        }
        return false;
    }
}
