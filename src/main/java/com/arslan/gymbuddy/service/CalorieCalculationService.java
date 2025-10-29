package com.arslan.gymbuddy.service;

import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.entity.Workout;
import org.springframework.stereotype.Service;

@Service
public class CalorieCalculationService {

    public double calculateCaloriesBurned(User user, Workout workout, int durationMinutes) {
        double bmr = calculateBMR(user);
        double activityMultiplier = getActivityMultiplier(workout.getType());
        double caloriesPerMinute = (bmr / 1440) * activityMultiplier; // BMR per minute * activity factor
        
        // Adjust based on workout's base calories per minute
        double adjustedCaloriesPerMinute = (caloriesPerMinute + workout.getCaloriesPerMinute()) / 2;
        
        return adjustedCaloriesPerMinute * durationMinutes;
    }

    private double calculateBMR(User user) {
        if (user.getAge() == null || user.getWeight() == null || user.getHeight() == null) {
            return 2000; // Default BMR if user data is incomplete
        }

        // Mifflin-St Jeor Equation
        if ("MALE".equalsIgnoreCase(user.getGender())) {
            return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }
    }

    private double getActivityMultiplier(Workout.WorkoutType type) {
        return switch (type) {
            case CARDIO -> 8.0;
            case STRENGTH -> 6.0;
            case SPORTS -> 7.0;
            case FLEXIBILITY -> 3.0;
            case OTHER -> 5.0;
        };
    }
}