package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class LikeId implements Serializable {
    private static final long serialVersionUID = -1504027821090745551L;
    @NotNull
    @Column(name = "studentid", nullable = false)
    private Integer studentid;

    @NotNull
    @Column(name = "courseid", nullable = false)
    private Integer courseid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LikeId entity = (LikeId) o;
        return Objects.equals(this.studentid, entity.studentid) &&
                Objects.equals(this.courseid, entity.courseid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentid, courseid);
    }

}