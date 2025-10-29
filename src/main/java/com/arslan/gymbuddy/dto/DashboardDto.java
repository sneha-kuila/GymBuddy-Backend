package com.arslan.gymbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {
    private Double totalCaloriesBurned;
    private Integer totalWorkoutTime;
    private Integer weeklyGoalProgress;
    private Integer currentStreak;
}