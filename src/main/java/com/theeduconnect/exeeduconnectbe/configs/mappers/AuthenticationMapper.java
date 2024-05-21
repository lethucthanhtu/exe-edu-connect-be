package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.entities.Role;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.RoleDto;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    @Mapping(target = "role", ignore = true)
    User RegisterRequestToUserEntity(RegisterRequest dto);

    RoleDto RoleEntityToRoleDto(Role role);
}
