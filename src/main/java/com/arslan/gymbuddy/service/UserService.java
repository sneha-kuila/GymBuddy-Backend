package com.arslan.gymbuddy.service;

import com.arslan.gymbuddy.dto.UpdateUserDto;
import com.arslan.gymbuddy.dto.UserDto;
import com.arslan.gymbuddy.entity.User;
import com.arslan.gymbuddy.exception.UserNotFoundException;
import com.arslan.gymbuddy.mapper.UserMapper;
import com.arslan.gymbuddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto getUserProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toDto(user);
    }

    public UserDto updateUserProfile(String email, UpdateUserDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        userMapper.updateEntityFromDto(dto, user, passwordEncoder);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void deleteUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        userRepository.delete(user);
    }
}