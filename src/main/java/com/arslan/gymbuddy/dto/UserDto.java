package com.arslan.gymbuddy.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String gender;
    private String fitnessGoal;
    private Integer phone;
}