package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.entities.Teacher;
import com.theeduconnect.exeeduconnectbe.domain.entities.User;
import com.theeduconnect.exeeduconnectbe.features.teacher.dtos.TeacherDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(source = "user.fullname", target = "fullname")
    @Mapping(source = "user.dateofbirth", target = "dateofbirth")
    @Mapping(source = "user.avatarurl", target = "avatarurl")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "teacher.occupation", target = "occupation")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "teacher.id", target = "id")
    @Mapping(source = "teacher.school", target = "school")
    @Mapping(source = "teacher.specialty", target = "specialty")
    @Mapping(source = "teacher.bio", target = "bio")
    TeacherDetailsDto TeacherAndUserEntityToTeacherDetailsDto(Teacher teacher, User user);
}
