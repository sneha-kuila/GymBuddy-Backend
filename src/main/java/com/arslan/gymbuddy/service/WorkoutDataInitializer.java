package com.arslan.gymbuddy.service;

import com.arslan.gymbuddy.entity.Workout;
import com.arslan.gymbuddy.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkoutDataInitializer implements CommandLineRunner {

    private final WorkoutRepository workoutRepository;

    @Override
    public void run(String... args) {
        if (workoutRepository.count() == 0) {
            initializeWorkouts();
        }
    }

    private void initializeWorkouts() {
        List<Workout> workouts = List.of(
            Workout.builder().name("Running").description("Outdoor or treadmill running").caloriesPerMinute(10.0).type(Workout.WorkoutType.CARDIO).build(),
            Workout.builder().name("Cycling").description("Indoor or outdoor cycling").caloriesPerMinute(8.0).type(Workout.WorkoutType.CARDIO).build(),
            Workout.builder().name("Swimming").description("Pool swimming").caloriesPerMinute(12.0).type(Workout.WorkoutType.CARDIO).build(),
            Workout.builder().name("Weight Training").description("Strength training with weights").caloriesPerMinute(6.0).type(Workout.WorkoutType.STRENGTH).build(),
            Workout.builder().name("Push-ups").description("Bodyweight push-ups").caloriesPerMinute(5.0).type(Workout.WorkoutType.STRENGTH).build(),
            Workout.builder().name("Yoga").description("Yoga practice").caloriesPerMinute(3.0).type(Workout.WorkoutType.FLEXIBILITY).build(),
            Workout.builder().name("Basketball").description("Basketball game").caloriesPerMinute(9.0).type(Workout.WorkoutType.SPORTS).build(),
            Workout.builder().name("Tennis").description("Tennis match").caloriesPerMinute(8.0).type(Workout.WorkoutType.SPORTS).build(),
            Workout.builder().name("Walking").description("Casual walking").caloriesPerMinute(4.0).type(Workout.WorkoutType.CARDIO).build(),
            Workout.builder().name("Stretching").description("General stretching").caloriesPerMinute(2.0).type(Workout.WorkoutType.FLEXIBILITY).build()
        );
        
        workoutRepository.saveAll(workouts);
    }
}