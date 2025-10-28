package com.arslan.gym_buddy.Service;


import com.arslan.gym_buddy.DTO.UpdateUserDTO;
import com.arslan.gym_buddy.DTO.UserDTO;
import com.arslan.gym_buddy.Entity.Users;
import com.arslan.gym_buddy.Exception.UserNotFoundException;
import com.arslan.gym_buddy.Mapper.UserMapper;
import com.arslan.gym_buddy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO getUserProfileByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    public UserDTO updateUserProfile(String email, UpdateUserDTO dto) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        userMapper.updateEntityFromDTO(dto, user, passwordEncoder);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    public void deleteUserProfile(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        userRepository.delete(user);
    }
}
