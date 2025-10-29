package com.arslan.gymbuddy.controller;

import com.arslan.gymbuddy.dto.UpdateUserDto;
import com.arslan.gymbuddy.dto.UserDto;
import com.arslan.gymbuddy.service.UserService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    // Get current user profile
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userProfile = userService.getUserProfileByEmail(email);
        return CustomResponse("User profile fetched successfully", userProfile, HttpStatus.OK);
    }

    // Update current user profile
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody UpdateUserDto updateUserDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto updatedProfile = userService.updateUserProfile(email, updateUserDto);
        return CustomResponse("User profile updated successfully", updatedProfile, HttpStatus.OK);
    }

    // Delete current user account
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUserProfile(email);
        SecurityContextHolder.clearContext(); // Logout after delete
        return CustomResponse("User account deleted successfully", null, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> CustomResponse(String message, Object data, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        body.put("data", data);
        return new ResponseEntity<>(body, status);
    }
}