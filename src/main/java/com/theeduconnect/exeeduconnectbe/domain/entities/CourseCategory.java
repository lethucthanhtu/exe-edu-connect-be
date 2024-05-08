package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "coursecategoryid")
    private Set<com.theeduconnect.exeeduconnectbe.domain.entities.Course> courses = new LinkedHashSet<>();

}