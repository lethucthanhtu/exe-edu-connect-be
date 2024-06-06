package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.StudentEvaluation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluation, Integer> {
    List<StudentEvaluation> findByCourseschedule_Student_Id(int studentId);
    List<StudentEvaluation> findByCourseschedule_Student_IdAndCourseschedule_Course_Id(int studentId, int courseId);
    boolean existsByCourseschedule_Id(Integer courseScheduleId);
}
