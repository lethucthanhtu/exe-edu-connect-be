package com.theeduconnect.exeeduconnectbe.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_evaluation")
public class StudentEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentevaluationid", nullable = false)
    private Integer id;

    @Column(name = "ispresent")
    private Boolean ispresent;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coursescheduleid")
    private CourseSchedule courseschedule;
}
