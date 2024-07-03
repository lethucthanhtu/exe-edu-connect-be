package com.theeduconnect.exeeduconnectbe.features.course.services.impl.create;

import com.theeduconnect.exeeduconnectbe.configs.mappers.CourseMapper;
import com.theeduconnect.exeeduconnectbe.configs.mappers.ScheduleMapper;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.domain.Teacher;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseScheduleRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.course.services.impl.join.GoogleMeetServiceImpl;
import com.theeduconnect.exeeduconnectbe.repositories.CourseCategoryRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseScheduleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.TeacherRepository;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class CreateCourseServiceImpl {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseCategoryRepository courseCategoryRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final CourseMapper courseMapper;
    private final GoogleMeetServiceImpl googleMeetServiceImpl;
    private final ScheduleMapper scheduleMapper;
    private NewCourseRequest request;
    private Course course;
    private CourseCategory courseCategory;
    private Teacher teacher;
    private List<NewCourseScheduleRequest> newCourseScheduleRequestList;
    private CreateCourseScheduleServiceImpl createCourseScheduleServiceImpl;
    private Set<CourseSchedule> courseSchedules;

    public CreateCourseServiceImpl(
            CourseRepository courseRepository,
            CourseMapper courseMapper,
            ScheduleMapper scheduleMapper,
            CourseCategoryRepository courseCategoryRepository,
            CourseScheduleRepository courseScheduleRepository,
            TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.teacherRepository = teacherRepository;
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.googleMeetServiceImpl = new GoogleMeetServiceImpl();
        this.createCourseScheduleServiceImpl = new CreateCourseScheduleServiceImpl();
    }

    public CourseServiceResponse Handle(NewCourseRequest request) {
        try {
            this.request = request;
            if (!IsStartDateBeforeEndDate()) return StartDateNotBeforeEndDateResult();
            if (!IsCourseCategoryIdValid()) return InvalidCourseCategoryIdResult();
            MapNewCourseRequestToCourseEntity();
            ConvertCourseScheduleRequestsToCourseScheduleEntities();
            course.setCourseSchedules(courseSchedules);
            courseRepository.save(course);
            courseScheduleRepository.saveAll(courseSchedules);
            return CreateCourseSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsStartDateBeforeEndDate() {
        if (request.getStartdate().isBefore(request.getEnddate())) return true;
        return false;
    }

    private boolean IsCourseCategoryIdValid() {
        Optional<CourseCategory> courseCategoryOptional =
                courseCategoryRepository.findById(request.getCategoryid());
        if (courseCategoryOptional.isEmpty()) return false;
        courseCategory = courseCategoryOptional.get();
        return true;
    }

    //    private boolean IsStartTimeBetweenStartDateAndEndDate() {
    //        newCourseScheduleRequestList = request.getSchedulerequests();
    //        LocalDate startDate = request.getStartdate();
    //        LocalDate endDate = request.getEnddate();
    //        for (NewCourseScheduleRequest newCourseScheduleRequest : newCourseScheduleRequestList)
    // {
    //            Instant startTime = newCourseScheduleRequest.getStarttime();
    //            if (!TimeUtils.IsInstantBetweenLocalDates(startTime, startDate, endDate)) return
    // false;
    //        }
    //        return true;
    //    }

    private void MapNewCourseRequestToCourseEntity() {
        course = courseMapper.NewCourseRequestToCourseEntity(request);
        course.setCoursecategory(courseCategory);
        teacher = teacherRepository.findById(request.getTeacherid()).get();
        course.setTeacher(teacher);
    }

    private void ConvertCourseScheduleRequestsToCourseScheduleEntities()
            throws IOException, GeneralSecurityException {
        courseSchedules =
                createCourseScheduleServiceImpl.Handle(
                        new CreateCourseScheduleServiceImpl.CreateCourseScheduleServiceParams(
                                request.getStartdate(),
                                request.getEnddate(),
                                request.getMeeturl(),
                                course,
                                request.getSchedulerequests()));
    }

    private CourseServiceResponse CreateCourseSuccessfulResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.CREATE_COURSE_SUCCESSFUL,
                CourseServiceMessages.CREATE_COURSE_SUCCESSFUL,
                null);
    }

    private CourseServiceResponse StartDateNotBeforeEndDateResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.START_DATE_BEFORE_END_DATE,
                CourseServiceMessages.START_DATE_BEFORE_END_DATE,
                null);
    }

    private CourseServiceResponse InvalidCourseCategoryIdResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INVALID_COURSE_CATEGORY_ID,
                CourseServiceMessages.INVALID_COURSE_CATEGORY_ID,
                null);
    }

    private CourseServiceResponse InvalidStartTimeResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INVALID_START_TIME,
                CourseServiceMessages.INVALID_START_TIME,
                null);
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
