package com.theeduconnect.exeeduconnectbe.features.authentication.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.constants.authentication.messages.AuthenticationServiceMessages;
import com.theeduconnect.exeeduconnectbe.constants.authentication.responseCodes.AuthenticationHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.domain.entities.Role;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.RoleDto;
import com.theeduconnect.exeeduconnectbe.features.authentication.payload.response.AuthenticationServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

public class GetRolesServiceImpl {
    private RoleRepository roleRepository;
    private AuthenticationMapper authenticationMapper;
    private List<Role> roles;
    private List<RoleDto> roleDtos;
    public GetRolesServiceImpl(RoleRepository roleRepository,
                               AuthenticationMapper authenticationMapper){
        this.roleRepository = roleRepository;
        this.authenticationMapper = authenticationMapper;
    }
    public AuthenticationServiceResponse Handle(){
        try{
            roles = roleRepository.findAll();
            if(roles.size()==0) return NoRolesFoundResult();
            MapRolesToRoleDtos();
            return AllRolesFoundResult();
        }catch(Exception e){
            return InternalServerErrorResult(e);
        }

    }
    private void MapRolesToRoleDtos(){
        roleDtos = new ArrayList<RoleDto>();
        for(Role role : roles){
            RoleDto roleDto = authenticationMapper.RoleEntityToRoleDto(role);
            roleDtos.add(roleDto);
        }
    }
    private AuthenticationServiceResponse NoRolesFoundResult(){
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.NO_ROLES_FOUND,
                AuthenticationServiceMessages.NO_ROLES_FOUND,
                null
        );
    }
    private AuthenticationServiceResponse AllRolesFoundResult(){
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.ALL_ROLES_FOUND,
                AuthenticationServiceMessages.ALL_ROLES_FOUND,
                roleDtos
        );
    }
    private AuthenticationServiceResponse InternalServerErrorResult(Exception e) {
        return new AuthenticationServiceResponse(
                AuthenticationHttpResponseCodes.INTERNAL_SERVER_ERROR,
                AuthenticationServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
    }
}
