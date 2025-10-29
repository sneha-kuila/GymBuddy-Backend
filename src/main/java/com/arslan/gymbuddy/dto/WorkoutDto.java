package com.arslan.gymbuddy.dto;

import com.arslan.gymbuddy.entity.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {
    private Long id;
    private String name;
    private String description;
    private Double caloriesPerMinute;
    private Workout.WorkoutType type;
}