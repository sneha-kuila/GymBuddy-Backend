package com.arslan.gym_buddy.DTO;

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

