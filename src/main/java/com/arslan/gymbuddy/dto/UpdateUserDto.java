package com.arslan.gymbuddy.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String fullName;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String gender;
    private String fitnessGoal;
    private String phone;
}