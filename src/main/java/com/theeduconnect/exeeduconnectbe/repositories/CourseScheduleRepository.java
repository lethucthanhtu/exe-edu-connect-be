package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Integer> {
    List<CourseSchedule> findByStudent_Id(int studentId);
    List<CourseSchedule> findByStudent_IdAndCourse_Id(int studentId, int courseId);
}
