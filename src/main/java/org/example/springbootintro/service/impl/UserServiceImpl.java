package org.example.springbootintro.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.UserRegistrationRequestDto;
import org.example.springbootintro.dto.UserResponseDto;
import org.example.springbootintro.exception.RegistrationException;
import org.example.springbootintro.mapper.UserMapper;
import org.example.springbootintro.model.Role;
import org.example.springbootintro.model.RoleName;
import org.example.springbootintro.model.User;
import org.example.springbootintro.repository.RoleRepository;
import org.example.springbootintro.repository.UserRepository;
import org.example.springbootintro.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }

        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RegistrationException("Role USER not found"));

        user.setRoles(Set.of(userRole));
        userRepository.save(user);

        return userMapper.toDto(user);
    }
}
