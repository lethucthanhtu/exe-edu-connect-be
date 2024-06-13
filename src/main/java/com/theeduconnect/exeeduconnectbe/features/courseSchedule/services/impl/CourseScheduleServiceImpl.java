package com.theeduconnect.exeeduconnectbe.features.courseSchedule.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.courseSchedule.CourseScheduleMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.domain.Student;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.dtos.CourseScheduleDto;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.request.CourseScheduleRequest;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.response.CourseScheduleResponse;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.services.CourseScheduleService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseScheduleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public CourseScheduleResponse getAllCourseSchedules() {
        List<CourseSchedule> schedules = courseScheduleRepository.findAll();
        List<CourseScheduleDto> dtos = schedules.stream().map(this::convertToDto).collect(Collectors.toList());
        return new CourseScheduleResponse(HttpStatus.OK.value(), CourseScheduleMessages.ALL_SCHEDULES_FOUND, dtos);
    }

    @Override
    public CourseScheduleResponse getCourseScheduleById(int id) {
        Optional<CourseSchedule> schedule = courseScheduleRepository.findById(id);
        if (schedule.isPresent()) {
            return new CourseScheduleResponse(HttpStatus.OK.value(), CourseScheduleMessages.SCHEDULE_FOUND, convertToDto(schedule.get()));
        } else {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.SCHEDULE_NOT_FOUND, null);
        }
    }

    @Override
    public CourseScheduleResponse createCourseSchedule(CourseScheduleRequest request) {
        CourseSchedule schedule = new CourseSchedule();
        schedule.setStarttime(request.getStarttime());
        schedule.setDuration(request.getDuration());
        schedule.setMeeturl(request.getMeeturl());

        Optional<Course> course = courseRepository.findById(request.getCourseId());
        if (course.isPresent()) {
            schedule.setCourse(course.get());
        } else {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.COURSE_NOT_FOUND, null);
        }

        Optional<Student> student = studentRepository.findById(request.getStudentId());
        if (student.isPresent()) {
            schedule.setStudent(student.get());
        } else {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.STUDENT_NOT_FOUND, null);
        }

        courseScheduleRepository.save(schedule);
        return new CourseScheduleResponse(HttpStatus.CREATED.value(), CourseScheduleMessages.SCHEDULE_CREATED, convertToDto(schedule));
    }

    @Override
    public CourseScheduleResponse updateCourseSchedule(int id, CourseScheduleRequest request) {
        Optional<CourseSchedule> optionalSchedule = courseScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            CourseSchedule schedule = optionalSchedule.get();
            schedule.setStarttime(request.getStarttime());
            schedule.setDuration(request.getDuration());
            schedule.setMeeturl(request.getMeeturl());

            Optional<Course> course = courseRepository.findById(request.getCourseId());
            if (course.isPresent()) {
                schedule.setCourse(course.get());
            } else {
                return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.COURSE_NOT_FOUND, null);
            }

            Optional<Student> student = studentRepository.findById(request.getStudentId());
            if (student.isPresent()) {
                schedule.setStudent(student.get());
            } else {
                return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.STUDENT_NOT_FOUND, null);
            }

            courseScheduleRepository.save(schedule);
            return new CourseScheduleResponse(HttpStatus.OK.value(), CourseScheduleMessages.SCHEDULE_UPDATED, convertToDto(schedule));
        } else {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.SCHEDULE_NOT_FOUND, null);
        }
    }

    @Override
    public CourseScheduleResponse deleteCourseSchedule(int id) {
        Optional<CourseSchedule> schedule = courseScheduleRepository.findById(id);
        if (schedule.isPresent()) {
            courseScheduleRepository.delete(schedule.get());
            return new CourseScheduleResponse(HttpStatus.OK.value(), CourseScheduleMessages.SCHEDULE_DELETED, null);
        } else {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.SCHEDULE_NOT_FOUND, null);
        }
    }

    @Override
    public CourseScheduleResponse getAllSchedulesByStudentId(int studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if (!studentExists) {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.STUDENT_NOT_FOUND, null);
        }
        List<CourseSchedule> schedules = courseScheduleRepository.findByStudent_Id(studentId);
        List<CourseScheduleDto> dtos = schedules.stream().map(this::convertToDto).collect(Collectors.toList());
        return new CourseScheduleResponse(HttpStatus.OK.value(), CourseScheduleMessages.ALL_SCHEDULES_FOUND, dtos);
    }

    @Override
    public CourseScheduleResponse getAllSchedulesByStudentIdAndCourseId(int studentId, int courseId) {
        boolean studentExists = studentRepository.existsById(studentId);
        boolean courseExists = courseRepository.existsById(courseId);
        if (!studentExists) {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.STUDENT_NOT_FOUND, null);
        }
        if (!courseExists) {
            return new CourseScheduleResponse(HttpStatus.NOT_FOUND.value(), CourseScheduleMessages.COURSE_NOT_FOUND, null);
        }
        List<CourseSchedule> schedules = courseScheduleRepository.findByStudent_IdAndCourse_Id(studentId, courseId);
        List<CourseScheduleDto> dtos = schedules.stream().map(this::convertToDto).collect(Collectors.toList());
        return new CourseScheduleResponse(HttpStatus.OK.value(), CourseScheduleMessages.ALL_SCHEDULES_FOUND, dtos);
    }

    private CourseScheduleDto convertToDto(CourseSchedule schedule) {
        CourseScheduleDto dto = new CourseScheduleDto();
        dto.setId(schedule.getId());
        dto.setStarttime(schedule.getStarttime());
        dto.setDuration(schedule.getDuration());
        dto.setMeeturl(schedule.getMeeturl());
        dto.setCourseId(schedule.getCourse().getId());
        dto.setStudentId(schedule.getStudent().getId());
        return dto;
    }
}
