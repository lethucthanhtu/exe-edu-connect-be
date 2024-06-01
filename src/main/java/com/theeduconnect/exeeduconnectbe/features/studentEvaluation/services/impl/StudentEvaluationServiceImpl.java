package com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services.impl;

import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import com.theeduconnect.exeeduconnectbe.domain.StudentEvaluation;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.dtos.StudentEvaluationDto;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.request.StudentEvaluationRequest;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.payload.response.StudentEvaluationResponse;
import com.theeduconnect.exeeduconnectbe.features.studentEvaluation.services.StudentEvaluationService;
import com.theeduconnect.exeeduconnectbe.repositories.CourseScheduleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.StudentEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentEvaluationServiceImpl implements StudentEvaluationService {

    @Autowired
    private StudentEvaluationRepository studentEvaluationRepository;

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Override
    public StudentEvaluationResponse getAllEvaluations() {
        List<StudentEvaluation> evaluations = studentEvaluationRepository.findAll();
        List<StudentEvaluationDto> dtos = evaluations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new StudentEvaluationResponse(HttpStatus.OK.value(), "All evaluations found", dtos);
    }

    @Override
    public StudentEvaluationResponse getEvaluationById(int id) {
        Optional<StudentEvaluation> evaluation = studentEvaluationRepository.findById(id);
        if (evaluation.isPresent()) {
            return new StudentEvaluationResponse(HttpStatus.OK.value(), "Evaluation found", convertToDto(evaluation.get()));
        } else {
            return new StudentEvaluationResponse(HttpStatus.NOT_FOUND.value(), "Evaluation not found", null);
        }
    }

    @Override
    public StudentEvaluationResponse createEvaluation(StudentEvaluationRequest request) {
        StudentEvaluation evaluation = new StudentEvaluation();
        evaluation.setIspresent(request.getIsPresent());
        evaluation.setComment(request.getComment());

        Optional<CourseSchedule> courseSchedule = courseScheduleRepository.findById(request.getCourseScheduleId());
        if (courseSchedule.isPresent()) {
            evaluation.setCourseschedule(courseSchedule.get());
        } else {
            return new StudentEvaluationResponse(HttpStatus.NOT_FOUND.value(), "Course schedule not found", null);
        }

        studentEvaluationRepository.save(evaluation);
        return new StudentEvaluationResponse(HttpStatus.CREATED.value(), "Evaluation created", convertToDto(evaluation));
    }

    @Override
    public StudentEvaluationResponse updateEvaluation(int id, StudentEvaluationRequest request) {
        Optional<StudentEvaluation> optionalEvaluation = studentEvaluationRepository.findById(id);
        if (optionalEvaluation.isPresent()) {
            StudentEvaluation evaluation = optionalEvaluation.get();
            evaluation.setIspresent(request.getIsPresent());
            evaluation.setComment(request.getComment());

            Optional<CourseSchedule> courseSchedule = courseScheduleRepository.findById(request.getCourseScheduleId());
            if (courseSchedule.isPresent()) {
                evaluation.setCourseschedule(courseSchedule.get());
            } else {
                return new StudentEvaluationResponse(HttpStatus.NOT_FOUND.value(), "Course schedule not found", null);
            }

            studentEvaluationRepository.save(evaluation);
            return new StudentEvaluationResponse(HttpStatus.OK.value(), "Evaluation updated", convertToDto(evaluation));
        } else {
            return new StudentEvaluationResponse(HttpStatus.NOT_FOUND.value(), "Evaluation not found", null);
        }
    }

    @Override
    public StudentEvaluationResponse deleteEvaluation(int id) {
        Optional<StudentEvaluation> evaluation = studentEvaluationRepository.findById(id);
        if (evaluation.isPresent()) {
            studentEvaluationRepository.delete(evaluation.get());
            return new StudentEvaluationResponse(HttpStatus.OK.value(), "Evaluation deleted", null);
        } else {
            return new StudentEvaluationResponse(HttpStatus.NOT_FOUND.value(), "Evaluation not found", null);
        }
    }

    private StudentEvaluationDto convertToDto(StudentEvaluation evaluation) {
        StudentEvaluationDto dto = new StudentEvaluationDto();
        dto.setId(evaluation.getId());
        dto.setIsPresent(evaluation.getIspresent());
        dto.setComment(evaluation.getComment());
        dto.setCourseScheduleId(evaluation.getCourseschedule().getId());
        return dto;
    }
}

