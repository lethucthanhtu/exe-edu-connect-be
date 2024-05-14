package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
    @EmbeddedId
    private LikeId id;

    @MapsId("studentid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentid", nullable = false)
    private Student student;

    @MapsId("courseid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courseid", nullable = false)
    private Course course;

}