package com.arslan.gymbuddy.controller;

import com.arslan.gymbuddy.dto.CreateWorkoutSessionRequest;
import com.arslan.gymbuddy.dto.WorkoutDto;
import com.arslan.gymbuddy.dto.WorkoutSessionDto;
import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.entity.Workout;
import com.arslan.gymbuddy.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<WorkoutDto>> getWorkoutsByType(@PathVariable Workout.WorkoutType type) {
        return ResponseEntity.ok(workoutService.getWorkoutsByType(type));
    }

    @PostMapping("/sessions")
    public ResponseEntity<WorkoutSessionDto> createWorkoutSession(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateWorkoutSessionRequest request) {
        WorkoutSessionDto session = workoutService.createWorkoutSession(user, request);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<WorkoutSessionDto>> getUserWorkoutSessions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(workoutService.getUserWorkoutSessions(user.getId()));
    }

    @GetMapping("/calories/total")
    public ResponseEntity<Map<String, Double>> getTotalCalories(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Double total = workoutService.getTotalCaloriesBurned(user.getId(), startDate, endDate);
        return ResponseEntity.ok(Map.of("totalCalories", total));
    }

    @GetMapping("/my-workouts")
    public ResponseEntity<List<WorkoutDto>> getMyWorkouts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(workoutService.getUserWorkouts(user.getId()));
    }

    @PostMapping("/my-workouts/{workoutId}")
    public ResponseEntity<Void> addWorkoutToMyList(
            @AuthenticationPrincipal User user,
            @PathVariable Long workoutId) {
        workoutService.addWorkoutToUser(user.getId(), workoutId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/my-workouts/{workoutId}")
    public ResponseEntity<Void> removeWorkoutFromMyList(
            @AuthenticationPrincipal User user,
            @PathVariable Long workoutId) {
        workoutService.removeWorkoutFromUser(user.getId(), workoutId);
        return ResponseEntity.ok().build();
    }
}