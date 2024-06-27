package com.theeduconnect.exeeduconnectbe.features.course.services.impl.join;

import com.google.api.client.util.DateTime;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseServiceMessages;
import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.domain.Student;
import com.theeduconnect.exeeduconnectbe.features.course.dtos.NewGoogleMeetUrlDto;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.JoinCourseRequest;
import com.theeduconnect.exeeduconnectbe.features.course.payload.response.CourseServiceResponse;
import com.theeduconnect.exeeduconnectbe.repositories.AttendingCourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseRepository;
import com.theeduconnect.exeeduconnectbe.repositories.CourseScheduleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentRepository;
import com.theeduconnect.exeeduconnectbe.utils.TimeUtils;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Optional;

public class JoinCourseServiceImpl {
    private final AttendingCourseRepository attendingCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private GoogleMeetServiceImpl googleMeetServiceImpl;
    private JoinCourseRequest request;
    private Optional<CourseSchedule> courseScheduleOptional;
    private CourseSchedule courseSchedule;
    private Student student;
    private Course course;
    private String meetUrl;

    public JoinCourseServiceImpl(
            AttendingCourseRepository attendingCourseRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            CourseScheduleRepository courseScheduleRepository) {
        this.attendingCourseRepository = attendingCourseRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.googleMeetServiceImpl = new GoogleMeetServiceImpl();
    }

    public CourseServiceResponse Handle(JoinCourseRequest request) {
        try {
            this.request = request;
            if (!IsCourseScheduleAvailable()) return CourseScheduleNotFoundResult();
            courseSchedule = courseScheduleOptional.get();
            if (IsCourseScheduleTaken()) return CourseScheduleTakenResult();
            FindStudentById();
            FindCourseById();
            AddStudentToAttendingCourses();
            GenerateMeetUrlForStudent();
            UpdateMeetUrlAndStudentToCourseSchedule();
            return JoinCourseSuccessfulResult();
        } catch (Exception e) {
            return InternalServerErrorResult(e);
        }
    }

    private boolean IsCourseScheduleAvailable() {
        courseScheduleOptional = courseScheduleRepository.findById(request.getCoursescheduleid());
        if (courseScheduleOptional.isEmpty()) return false;
        return true;
    }

    private boolean IsCourseScheduleTaken() {

        String testMeetUrl = courseSchedule.getMeeturl();
        if (testMeetUrl == null || testMeetUrl.isEmpty()) return false;
        return true;
    }

    private void FindStudentById() {
        student = studentRepository.findById(request.getStudentid()).get();
    }

    private void FindCourseById() {
        course = courseRepository.findById(request.getCourseid()).get();
    }

    private void AddStudentToAttendingCourses() {
        AttendingCourse attendingCourse = new AttendingCourse();
        attendingCourse.setStudent(student);
        attendingCourse.setCourse(course);
        attendingCourse.setStatus(true);
        attendingCourseRepository.save(attendingCourse);
    }

    private void GenerateMeetUrlForStudent() throws IOException, GeneralSecurityException {
        NewGoogleMeetUrlDto newGoogleMeetUrlDto = BuildGoogleMeetUrlRequest();
        meetUrl = googleMeetServiceImpl.GetCalendarUrl(newGoogleMeetUrlDto);
    }

    private void UpdateMeetUrlAndStudentToCourseSchedule() {
        courseSchedule.setMeeturl(meetUrl);
        courseSchedule.setStudent(student);
        courseScheduleRepository.save(courseSchedule);
    }

    private NewGoogleMeetUrlDto BuildGoogleMeetUrlRequest() {
        DateTime startTimeInGoogleDateTime =
                TimeUtils.InstantToGoogleDateTime(courseSchedule.getStarttime());
        DateTime endTimeInGoogleDateTime =
                TimeUtils.InstantToGoogleDateTime(GetEndTime(courseSchedule));
        String summary = courseSchedule.getCourse().getName();
        String description = courseSchedule.getCourse().getDescription();
        String teacherEmail = courseSchedule.getCourse().getTeacher().getUser().getEmail();
        String studentEmail = student.getUser().getEmail();
        return new NewGoogleMeetUrlDto(
                summary,
                description,
                startTimeInGoogleDateTime,
                endTimeInGoogleDateTime,
                teacherEmail,
                studentEmail);
    }

    private Instant GetEndTime(CourseSchedule courseSchedule) {
        Instant startTime = courseSchedule.getStarttime();
        int duration = courseSchedule.getDuration();
        int secondsPerMinute = 60;
        return startTime.plusSeconds((long) duration * secondsPerMinute);
    }

    private CourseServiceResponse JoinCourseSuccessfulResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.JOIN_COURSE_SUCCESSFUL,
                CourseServiceMessages.JOIN_COURSE_SUCCESSFUL,
                null);
    }

    private CourseServiceResponse CourseScheduleNotFoundResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.COURSE_SCHEDULE_NOT_FOUND,
                CourseServiceMessages.COURSE_SCHEDULE_NOT_FOUND,
                null);
    }

    private CourseServiceResponse CourseScheduleTakenResult() {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.COURSE_SCHEDULE_TAKEN,
                CourseServiceMessages.COURSE_SCHEDULE_TAKEN,
                null);
    }

    private CourseServiceResponse InternalServerErrorResult(Exception e) {
        return new CourseServiceResponse(
                CourseServiceHttpResponseCodes.INTERNAL_SERVER_ERROR,
                CourseServiceMessages.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
