package com.arslan.gym_buddy.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private Long id;
    private String fullName;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String gender;
    private String fitnessGoal;
    private Integer Phone;

}

