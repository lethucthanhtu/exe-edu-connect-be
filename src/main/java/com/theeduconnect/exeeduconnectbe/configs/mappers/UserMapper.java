package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.user.dtos.UserDto;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntityToUserDto(User user);

    User newUserRequestToUserEntity(NewUserRequest request);
}
