package com.example.cash.web.mappers;

import com.example.cash.domain.user.User;
import com.example.cash.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
