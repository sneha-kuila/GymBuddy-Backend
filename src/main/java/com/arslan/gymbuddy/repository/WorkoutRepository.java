package com.arslan.gymbuddy.repository;

import com.arslan.gymbuddy.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByType(Workout.WorkoutType type);
    List<Workout> findByNameContainingIgnoreCase(String name);
}