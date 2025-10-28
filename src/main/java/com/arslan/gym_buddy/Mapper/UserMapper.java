package com.arslan.gym_buddy.Mapper;

import com.arslan.gym_buddy.Entity.Users;
import com.arslan.gym_buddy.DTO.UserDTO;
import com.arslan.gym_buddy.DTO.UpdateUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Convert entity to DTO
    public UserDTO toDTO(Users user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setFitnessGoal(user.getFitnessGoal());
        dto.setAge(user.getAge());
        dto.setWeight(user.getWeight());
        dto.setHeight(user.getHeight());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender());
        return dto;
    }

    // Update entity fields from Update DTO
    public void updateEntityFromDTO(UpdateUserDTO dto, Users user, PasswordEncoder encoder) {
        if (dto.getFullName() != null && !dto.getFullName().isBlank()) {
            user.setFullName(dto.getFullName());
        }

        if (dto.getFitnessGoal() != null && !dto.getFitnessGoal().isBlank()) {
            user.setFitnessGoal(dto.getFitnessGoal());
        }
        if (dto.getAge() != null) {
            user.setAge(dto.getAge());
        }
        if (dto.getWeight() != null) {
            user.setWeight(dto.getWeight());
        }
        if (dto.getHeight() != null) {
            user.setHeight(dto.getHeight());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getGender() != null && !dto.getGender().isBlank()) {
            user.setGender(dto.getGender());
        }
    }
}
