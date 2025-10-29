package com.arslan.gymbuddy.service;

import com.arslan.gymbuddy.dto.CreateWorkoutSessionRequest;
import com.arslan.gymbuddy.dto.WorkoutDto;
import com.arslan.gymbuddy.dto.WorkoutSessionDto;
import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.entity.Workout;
import com.arslan.gymbuddy.entity.WorkoutSession;
import com.arslan.gymbuddy.repository.WorkoutRepository;
import com.arslan.gymbuddy.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    
    private final WorkoutRepository workoutRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final CalorieCalculationService calorieCalculationService;

    public List<WorkoutDto> getAllWorkouts() {
        return workoutRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<WorkoutDto> getWorkoutsByType(Workout.WorkoutType type) {
        return workoutRepository.findByType(type).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public WorkoutSessionDto createWorkoutSession(User user, CreateWorkoutSessionRequest request) {
        Workout workout = workoutRepository.findById(request.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        double caloriesBurned = calorieCalculationService.calculateCaloriesBurned(
                user, workout, request.getDurationMinutes());

        WorkoutSession session = WorkoutSession.builder()
                .user(user)
                .workout(workout)
                .durationMinutes(request.getDurationMinutes())
                .caloriesBurned(caloriesBurned)
                .sessionDate(request.getSessionDate() != null ? request.getSessionDate() : LocalDateTime.now())
                .notes(request.getNotes())
                .build();

        WorkoutSession saved = workoutSessionRepository.save(session);
        return mapToSessionDto(saved);
    }

    public List<WorkoutSessionDto> getUserWorkoutSessions(Long userId) {
        return workoutSessionRepository.findByUserIdOrderBySessionDateDesc(userId).stream()
                .map(this::mapToSessionDto)
                .toList();
    }

    public Double getTotalCaloriesBurned(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        Double total = workoutSessionRepository.getTotalCaloriesBurnedByUserAndDateRange(userId, startDate, endDate);
        return total != null ? total : 0.0;
    }

    private WorkoutDto mapToDto(Workout workout) {
        return WorkoutDto.builder()
                .id(workout.getId())
                .name(workout.getName())
                .description(workout.getDescription())
                .caloriesPerMinute(workout.getCaloriesPerMinute())
                .type(workout.getType())
                .build();
    }

    private WorkoutSessionDto mapToSessionDto(WorkoutSession session) {
        return WorkoutSessionDto.builder()
                .id(session.getId())
                .workoutId(session.getWorkout().getId())
                .workoutName(session.getWorkout().getName())
                .durationMinutes(session.getDurationMinutes())
                .caloriesBurned(session.getCaloriesBurned())
                .sessionDate(session.getSessionDate())
                .notes(session.getNotes())
                .build();
    }
}