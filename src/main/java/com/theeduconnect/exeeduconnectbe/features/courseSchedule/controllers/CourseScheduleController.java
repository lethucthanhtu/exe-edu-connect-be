package com.theeduconnect.exeeduconnectbe.features.courseSchedule.controllers;

import com.theeduconnect.exeeduconnectbe.constants.courseSchedule.CourseScheduleEndpoints;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.request.CourseScheduleRequest;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.payload.response.CourseScheduleResponse;
import com.theeduconnect.exeeduconnectbe.features.courseSchedule.services.CourseScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CourseScheduleEndpoints.COURSE_SCHEDULE_ROOT)
public class CourseScheduleController {

    private final CourseScheduleService courseScheduleService;

    @Autowired
    public CourseScheduleController(CourseScheduleService courseScheduleService) {
        this.courseScheduleService = courseScheduleService;
    }

    @GetMapping
    @Operation(summary = "Get all course schedule")
    public ResponseEntity<CourseScheduleResponse> getAllSchedules() {
        CourseScheduleResponse response = courseScheduleService.getAllCourseSchedules();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(CourseScheduleEndpoints.GET_SCHEDULE_BY_ID)
    @Operation(summary = "Get course schedule by id")
    public ResponseEntity<CourseScheduleResponse> getScheduleById(@PathVariable int id) {
        CourseScheduleResponse response = courseScheduleService.getCourseScheduleById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping
    @Operation(summary = "Create a new course schedule")
    public ResponseEntity<CourseScheduleResponse> createSchedule(@Valid @RequestBody CourseScheduleRequest request) {
        CourseScheduleResponse response = courseScheduleService.createCourseSchedule(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping(CourseScheduleEndpoints.UPDATE_SCHEDULE_BY_ID)
    @Operation(summary = "Update course schedule by id")
    public ResponseEntity<CourseScheduleResponse> updateSchedule(@PathVariable int id, @Valid @RequestBody CourseScheduleRequest request) {
        CourseScheduleResponse response = courseScheduleService.updateCourseSchedule(id, request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping(CourseScheduleEndpoints.DELETE_SCHEDULE_BY_ID)
    @Operation(summary = "Delete a course schedule by id")
    public ResponseEntity<CourseScheduleResponse> deleteSchedule(@PathVariable int id) {
        CourseScheduleResponse response = courseScheduleService.deleteCourseSchedule(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(CourseScheduleEndpoints.GET_ALL_SCHEDULES_BY_STUDENT_ID)
    @Operation(summary = "Get all course schedules by student ID")
    public ResponseEntity<CourseScheduleResponse> getAllSchedulesByStudentId(@PathVariable int studentId) {
        CourseScheduleResponse response = courseScheduleService.getAllSchedulesByStudentId(studentId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping(CourseScheduleEndpoints.GET_ALL_SCHEDULES_BY_STUDENT_ID_AND_COURSE_ID)
    @Operation(summary = "Get all course schedules by student ID and course ID")
    public ResponseEntity<CourseScheduleResponse> getAllSchedulesByStudentIdAndCourseId(@PathVariable int studentId, @PathVariable int courseId) {
        CourseScheduleResponse response = courseScheduleService.getAllSchedulesByStudentIdAndCourseId(studentId, courseId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
