package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.AttendingCourse;
import com.theeduconnect.exeeduconnectbe.domain.Course;
import com.theeduconnect.exeeduconnectbe.domain.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendingCourseRepository extends JpaRepository<AttendingCourse, Integer> {
    Optional<AttendingCourse> findByStudent(Student student);

    List<AttendingCourse> findByCourse(Course course);
}
