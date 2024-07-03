package com.theeduconnect.exeeduconnectbe.features.course.services.impl.create;

import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.features.course.payload.request.NewCourseScheduleRequest;
import java.time.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CreateCourseScheduleServiceImpl {
    private CreateCourseScheduleServiceParams params;

    private Set<CourseSchedule> courseScheduleSet;

    public CreateCourseScheduleServiceImpl() {}

    public Set<CourseSchedule> Handle(CreateCourseScheduleServiceParams params) {
        this.params = params;
        LocalDate startDate = params.getStartDate();
        LocalDate endDate = params.getEndDate();
        courseScheduleSet = new HashSet<>();
        for (NewCourseScheduleRequest request : params.getNewCourseScheduleRequestList()) {
            List<Instant> instants =
                    GetInstantsBy(
                            request.getDayofweek(), startDate, endDate, request.getStarttime());
            AddToCourseScheduleSetFrom(instants, request);
        }
        return courseScheduleSet;
    }

    private List<Instant> GetInstantsBy(
            DayOfWeek targetDay, LocalDate startDate, LocalDate endDate, LocalTime time) {
        List<Instant> result = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() == targetDay) {
                LocalDateTime dateTime = LocalDateTime.of(currentDate, time);
                ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
                Instant instant = zonedDateTime.toInstant();
                result.add(instant);
            }
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }

    private void AddToCourseScheduleSetFrom(
            List<Instant> instants, NewCourseScheduleRequest request) {
        for (Instant instant : instants) {
            CourseSchedule courseSchedule = new CourseSchedule();
            courseSchedule.setMeeturl(params.getMeetUrl());
            courseSchedule.setStarttime(instant);
            courseSchedule.setDuration(request.getDuration());
            courseSchedule.setCourse(params.getCourse());
            courseScheduleSet.add(courseSchedule);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateCourseScheduleServiceParams {
        private LocalDate startDate;
        private LocalDate endDate;
        private String meetUrl;
        private Course course;
        private List<NewCourseScheduleRequest> newCourseScheduleRequestList;
    }
}
