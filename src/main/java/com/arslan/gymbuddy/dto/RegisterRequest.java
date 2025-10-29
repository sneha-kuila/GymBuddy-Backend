package com.arslan.gymbuddy.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String fitnessGoal; // "WEIGHT_LOSS", "MUSCLE_GAIN", etc.
}