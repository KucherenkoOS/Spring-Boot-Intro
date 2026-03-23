package org.example.springbootintro.service;

import org.example.springbootintro.dto.UserRegistrationRequestDto;
import org.example.springbootintro.dto.UserResponseDto;
import org.example.springbootintro.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;
}
