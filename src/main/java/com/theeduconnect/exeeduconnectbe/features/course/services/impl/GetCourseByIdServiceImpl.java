package com.theeduconnect.exeeduconnectbe.features.course.services.impl;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseDto;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.CourseScheduleDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseScheduleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import com.theeduconnect.exeeduconnectbe.utils.TimeUtils;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class GetCourseByIdServiceImpl {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private Optional<Course> courseOptional;
    private int courseId;
    private CourseDto courseDto;

    public GetCourseByIdServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            UserRepository userRepository,
            CourseScheduleRepository courseScheduleRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userRepository = userRepository;
        this.courseScheduleRepository = courseScheduleRepository;
    }

    public CourseServiceResponse Handle(int courseId) {
        this.courseId = courseId;
        try {
            if (!IsCourseAvailableInDatabase()) return CourseNotFoundResult();
            MapCourseEntityToCourseDto();
            return GetCourseByIdSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsCourseAvailableInDatabase() {
        courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) return false;
        return true;
    }

    private void MapCourseEntityToCourseDto() {
        Course course = courseOptional.get();
        courseDto = courseMapper.CourseEntityToCourseDto(course);
        courseDto.setTeacherid(course.getTeacher().getId());
        courseDto.setCategoryname(course.getCoursecategory().getCategoryname());
        courseDto.setTeachername(GetTeacherNameByTeacherId(course.getTeacher().getId()));
        courseDto.setCoursescheduledtos(GetCourseScheduleDtosByCourse());
    }

    private String GetTeacherNameByTeacherId(int teacherId) {
        return userRepository.findById(teacherId).get().getFullname();
    }

    private List<CourseScheduleDto> GetCourseScheduleDtosByCourse() {
        Set<CourseScheduleDto> result = new HashSet<>();
        Course course = courseOptional.get();
        List<CourseSchedule> courseSchedules = courseScheduleRepository.findByCourse(course);
        for (CourseSchedule courseSchedule : courseSchedules) {
            CourseScheduleDto courseScheduleDto =
                    BuildCourseScheduleDtoFromCourseSchedule(courseSchedule);
            result.add(courseScheduleDto);
        }
        return new ArrayList<>(result);
    }

    private CourseScheduleDto BuildCourseScheduleDtoFromCourseSchedule(
            CourseSchedule courseSchedule) {
        Instant startTime = courseSchedule.getStarttime();
        Duration duration = Duration.ofMinutes(courseSchedule.getDuration());
        Instant endTime = startTime.plus(duration);
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(startTime));
        String weekday = TimeUtils.GetDayOfWeekString(cal.get(Calendar.DAY_OF_WEEK));
        String startTimeStr = TimeUtils.InstantToTimeString(startTime);
        String endTimeStr = TimeUtils.InstantToTimeString(endTime);
        return new CourseScheduleDto(weekday, startTimeStr, endTimeStr);
    }

    private CourseServiceResponse GetCourseByIdSuccessfulResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.FOUND_COURSE_BY_ID_SUCCESSFUL,
                CourseServiceMessages.FOUND_COURSE_BY_ID_SUCCESSFUL,
                courseDto);
    }

    private CourseServiceResponse CourseNotFoundResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.NO_COURSES_FOUND,
                CourseServiceMessages.NO_COURSES_FOUND,
                null);
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
