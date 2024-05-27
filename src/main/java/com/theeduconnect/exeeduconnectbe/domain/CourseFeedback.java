package com.theeduconnect.exeeduconnectbe.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course_feedbacks")
public class CourseFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbackid", nullable = false)
    private Integer id;

    @Column(name = "star")
    private Integer star;

    @Size(max = 255)
    @Column(name = "content")
    private String content;

    @Column(name = "postdate")
    private LocalDate postdate;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attendingcourseid", nullable = false)
    private AttendingCourse attendingcourse;
}
