package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.UserRegistrationRequestDto;
import org.example.springbootintro.dto.UserResponseDto;
import org.example.springbootintro.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}
