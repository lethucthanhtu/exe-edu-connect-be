package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "course_schedules")
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coursescheduleid", nullable = false)
    private Integer id;

    @Column(name = "starttime")
    private Instant starttime;

    @Column(name = "duration")
    private Integer duration;

    @Size(max = 255)
    @Column(name = "meeturl")
    private String meeturl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

}