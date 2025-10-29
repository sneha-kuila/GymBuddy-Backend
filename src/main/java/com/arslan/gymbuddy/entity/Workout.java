package com.arslan.gymbuddy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workouts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double caloriesPerMinute; // Base calories per minute for average person

    @Enumerated(EnumType.STRING)
    private WorkoutType type;

    public enum WorkoutType {
        CARDIO, STRENGTH, FLEXIBILITY, SPORTS, OTHER
    }
}