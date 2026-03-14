package org.example.springbootintro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.UserRegistrationRequestDto;
import org.example.springbootintro.dto.UserResponseDto;
import org.example.springbootintro.exception.RegistrationException;
import org.example.springbootintro.mapper.UserMapper;
import org.example.springbootintro.model.User;
import org.example.springbootintro.repository.UserRepository;
import org.example.springbootintro.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }

        User user = userMapper.toModel(request);
        userRepository.save(user);

        return userMapper.toDto(user);
    }
}
