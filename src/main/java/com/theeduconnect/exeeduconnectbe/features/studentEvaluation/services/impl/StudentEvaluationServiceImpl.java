package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services.impl;

import com.theeduconnect.exeeduconnectbe.constants.studentEvaluation.StudentEvaluationMessages;
import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.domain.StudentEvaluation;
import com.theeduconnect.exeeduconnectbe.domain.User;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.dtos.StudentEvaluationDto;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request.StudentEvaluationRequest;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response.StudentEvaluationResponse;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services.StudentEvaluationService;
import com.theeduconnect.exeeduconnectbe.repositories.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentEvaluationServiceImpl implements StudentEvaluationService {

    @Autowired private StudentEvaluationRepository studentEvaluationRepository;

    @Autowired private StudentRepository studentRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private CourseScheduleRepository courseScheduleRepository;

    @Autowired private CourseRepository courseRepository;

    @Override
    public StudentEvaluationResponse getAllEvaluations() {
        List<StudentEvaluation> evaluations = studentEvaluationRepository.findAll();
        List<StudentEvaluationDto> dtos =
                evaluations.stream().map(this::convertToDto).collect(Collectors.toList());
        return new StudentEvaluationResponse(
                HttpStatus.OK.value(), StudentEvaluationMessages.ALL_EVALUATION_FOUND, dtos);
    }

    @Override
    public StudentEvaluationResponse getAllEvaluationsByStudentId(int studentId) {
        Optional<User> userOptional = userRepository.findById(studentId);
        if (userOptional.isPresent()) {
            List<StudentEvaluation> evaluations =
                    studentEvaluationRepository.findByCourseschedule_Student_Id(studentId);
            List<StudentEvaluationDto> dtos =
                    evaluations.stream().map(this::convertToDto).collect(Collectors.toList());
            return new StudentEvaluationResponse(
                    HttpStatus.OK.value(),
                    StudentEvaluationMessages.ALL_EVALUATION_FOR_A_STUDENT + studentId,
                    dtos);
        } else {
            return new StudentEvaluationResponse(
                    HttpStatus.NOT_FOUND.value(),
                    StudentEvaluationMessages.STUDENT_WITH_ID
                            + studentId
                            + StudentEvaluationMessages.NOT_FOUND,
                    null);
        }
    }

    @Override
    public StudentEvaluationResponse getEvaluationById(int id) {
        Optional<StudentEvaluation> evaluation = studentEvaluationRepository.findById(id);
        if (evaluation.isPresent()) {
            return new StudentEvaluationResponse(
                    HttpStatus.OK.value(),
                    StudentEvaluationMessages.EVALUATION_FOUND,
                    convertToDto(evaluation.get()));
        } else {
            return new StudentEvaluationResponse(
                    HttpStatus.NOT_FOUND.value(),
                    StudentEvaluationMessages.EVALUATION_NOT_FOUND,
                    null);
        }
    }

    @Override
    public StudentEvaluationResponse createEvaluation(StudentEvaluationRequest request) {
        StudentEvaluation evaluation = new StudentEvaluation();
        evaluation.setIspresent(request.getIspresent());
        evaluation.setComment(request.getComment());

        boolean exists =
                studentEvaluationRepository.existsByCourseschedule_Id(
                        request.getCoursescheduleid());
        Optional<CourseSchedule> courseSchedule =
                courseScheduleRepository.findById(request.getCoursescheduleid());
        if (exists) {
            return new StudentEvaluationResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    StudentEvaluationMessages.STUDENT_IS_EVALUATED,
                    null);
        } else {
            if (courseSchedule.isPresent()) {
                evaluation.setCourseschedule(courseSchedule.get());
            } else {
                return new StudentEvaluationResponse(
                        HttpStatus.NOT_FOUND.value(),
                        StudentEvaluationMessages.COURSE_SCHEDULE_NOT_FOUND,
                        null);
            }
        }

        studentEvaluationRepository.save(evaluation);
        return new StudentEvaluationResponse(
                HttpStatus.CREATED.value(),
                StudentEvaluationMessages.EVALUATION_CREATED,
                convertToDto(evaluation));
    }

    @Override
    public StudentEvaluationResponse updateEvaluation(int id, StudentEvaluationRequest request) {
        Optional<StudentEvaluation> optionalEvaluation = studentEvaluationRepository.findById(id);
        if (optionalEvaluation.isPresent()) {
            StudentEvaluation evaluation = optionalEvaluation.get();
            evaluation.setIspresent(request.getIspresent());
            evaluation.setComment(request.getComment());

            Optional<CourseSchedule> courseSchedule =
                    courseScheduleRepository.findById(request.getCoursescheduleid());
            if (courseSchedule.isPresent()) {
                evaluation.setCourseschedule(courseSchedule.get());
            } else {
                return new StudentEvaluationResponse(
                        HttpStatus.NOT_FOUND.value(),
                        StudentEvaluationMessages.COURSE_SCHEDULE_NOT_FOUND,
                        null);
            }

            studentEvaluationRepository.save(evaluation);
            return new StudentEvaluationResponse(
                    HttpStatus.OK.value(),
                    StudentEvaluationMessages.EVALUATION_UPDATED,
                    convertToDto(evaluation));
        } else {
            return new StudentEvaluationResponse(
                    HttpStatus.NOT_FOUND.value(),
                    StudentEvaluationMessages.EVALUATION_NOT_FOUND,
                    null);
        }
    }

    @Override
    public StudentEvaluationResponse deleteEvaluation(int id) {
        Optional<StudentEvaluation> evaluation = studentEvaluationRepository.findById(id);
        if (evaluation.isPresent()) {
            studentEvaluationRepository.delete(evaluation.get());
            return new StudentEvaluationResponse(
                    HttpStatus.OK.value(), StudentEvaluationMessages.EVALUATION_DELETED, null);
        } else {
            return new StudentEvaluationResponse(
                    HttpStatus.NOT_FOUND.value(),
                    StudentEvaluationMessages.EVALUATION_NOT_FOUND,
                    null);
        }
    }

    public StudentEvaluationResponse getEvaluationsByStudentIdAndCourseId(
            int studentId, int courseId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if (!studentExists) {
            return new StudentEvaluationResponse(
                    HttpStatus.NOT_FOUND.value(),
                    StudentEvaluationMessages.STUDENT_WITH_ID
                            + studentId
                            + StudentEvaluationMessages.NOT_FOUND
                            + StudentEvaluationMessages.IN_COURSE_WITH_ID
                            + courseId,
                    null);
        }
        boolean courseExists = courseRepository.existsById(courseId);
        if (!courseExists) {
            return new StudentEvaluationResponse(
                    HttpStatus.NOT_FOUND.value(),
                    StudentEvaluationMessages.COURSE_WITH_ID
                            + courseId
                            + StudentEvaluationMessages.NOT_FOUND,
                    null);
        }
        List<StudentEvaluation> evaluations =
                studentEvaluationRepository
                        .findByCourseschedule_Student_IdAndCourseschedule_Course_Id(
                                studentId, courseId);
        List<StudentEvaluationDto> dtos =
                evaluations.stream().map(this::convertToDto).collect(Collectors.toList());
        return new StudentEvaluationResponse(
                HttpStatus.OK.value(),
                StudentEvaluationMessages.ALL_EVALUATION_FOR_A_STUDENT
                        + studentId
                        + StudentEvaluationMessages.AND_COURSE_WITH_ID
                        + courseId,
                dtos);
    }

    private StudentEvaluationDto convertToDto(StudentEvaluation evaluation) {
        StudentEvaluationDto dto = new StudentEvaluationDto();
        dto.setId(evaluation.getId());
        dto.setIspresent(evaluation.getIspresent());
        dto.setComment(evaluation.getComment());
        dto.setCoursescheduleid(evaluation.getCourseschedule().getId());
        return dto;
    }
}
