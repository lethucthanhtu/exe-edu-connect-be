package com.theeduconnect.exeeduconnectbe.repositories;

import com.theeduconnect.exeeduconnectbe.domain.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer> {
    CourseCategory findByCategoryname(String name);
}
