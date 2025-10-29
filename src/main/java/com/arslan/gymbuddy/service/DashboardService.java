package com.arslan.gymbuddy.service;

import com.arslan.gymbuddy.dto.DashboardDto;
import com.arslan.gymbuddy.entity.WorkoutSession;
import com.arslan.gymbuddy.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final WorkoutSessionRepository workoutSessionRepository;

    public DashboardDto getDashboardData(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.truncatedTo(ChronoUnit.DAYS).minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDateTime weekEnd = weekStart.plusDays(7);

        List<WorkoutSession> weekSessions = workoutSessionRepository
                .findByUserIdAndDateRange(userId, weekStart, weekEnd);

        Double totalCalories = weekSessions.stream()
                .mapToDouble(WorkoutSession::getCaloriesBurned)
                .sum();

        Integer totalTime = weekSessions.stream()
                .mapToInt(WorkoutSession::getDurationMinutes)
                .sum();

        Integer weeklyGoal = calculateWeeklyGoalProgress(weekSessions);
        Integer streak = calculateStreak(userId);

        return DashboardDto.builder()
                .totalCaloriesBurned(totalCalories)
                .totalWorkoutTime(totalTime)
                .weeklyGoalProgress(weeklyGoal)
                .currentStreak(streak)
                .build();
    }

    private Integer calculateWeeklyGoalProgress(List<WorkoutSession> sessions) {
        int workoutDays = (int) sessions.stream()
                .map(s -> s.getSessionDate().toLocalDate())
                .distinct()
                .count();
        return Math.min(workoutDays, 5); // Assuming 5-day weekly goal
    }

    private Integer calculateStreak(Long userId) {
        List<WorkoutSession> recentSessions = workoutSessionRepository
                .findByUserIdOrderBySessionDateDesc(userId);
        
        if (recentSessions.isEmpty()) return 0;

        int streak = 0;
        LocalDateTime currentDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        
        for (WorkoutSession session : recentSessions) {
            LocalDateTime sessionDate = session.getSessionDate().toLocalDate().atStartOfDay();
            long daysDiff = ChronoUnit.DAYS.between(sessionDate, currentDate);
            
            if (daysDiff <= streak + 1) {
                if (daysDiff == streak) {
                    streak++;
                    currentDate = sessionDate;
                }
            } else {
                break;
            }
        }
        
        return streak;
    }
}