package com.arslan.gymbuddy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkoutSessionRequest {
    @NotNull
    private Long workoutId;

    @NotNull
    @Positive
    private Integer durationMinutes;

    private LocalDateTime sessionDate;
    private String notes;
}