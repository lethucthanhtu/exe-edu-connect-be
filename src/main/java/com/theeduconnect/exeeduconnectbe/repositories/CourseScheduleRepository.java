package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.CourseSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Integer> {
    List<CourseSchedule> findByStudent_Id(int studentId);

    List<CourseSchedule> findByStudent_IdAndCourse_Id(int studentId, int courseId);
}
