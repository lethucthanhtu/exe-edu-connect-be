package com.theeduconnect.exeeduconnectbe.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course_category")
public class CourseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coursecategoryid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "categoryname", length = 50)
    private String categoryname;

    @OneToMany(mappedBy = "coursecategory")
    private Set<Course> courses = new LinkedHashSet<>();
}
