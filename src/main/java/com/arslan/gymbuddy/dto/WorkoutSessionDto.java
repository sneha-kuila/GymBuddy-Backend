package com.arslan.gymbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionDto {
    private Long id;
    private Long workoutId;
    private String workoutName;
    private Integer durationMinutes;
    private Double caloriesBurned;
    private LocalDateTime sessionDate;
    private String notes;
}