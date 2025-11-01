package com.arslan.gymbuddy.repository;

import com.arslan.gymbuddy.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    List<WorkoutSession> findByUserIdOrderBySessionDateDesc(Long userId);
    
    @Query("SELECT ws FROM WorkoutSession ws WHERE ws.user.id = :userId AND ws.sessionDate BETWEEN :startDate AND :endDate")
    List<WorkoutSession> findByUserIdAndDateRange(@Param("userId") Long userId, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(ws.caloriesBurned) FROM WorkoutSession ws WHERE ws.user.id = :userId AND ws.sessionDate BETWEEN :startDate AND :endDate")
    Double getTotalCaloriesBurnedByUserAndDateRange(@Param("userId") Long userId, 
                                                   @Param("startDate") LocalDateTime startDate, 
                                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT DISTINCT ws.workout.id FROM WorkoutSession ws WHERE ws.user.id = :userId")
    List<Long> findDistinctWorkoutIdsByUserId(@Param("userId") Long userId);
}