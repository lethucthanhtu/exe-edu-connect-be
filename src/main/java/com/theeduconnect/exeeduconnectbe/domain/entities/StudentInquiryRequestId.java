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
public class StudentInquiryRequestId implements Serializable {
    private static final long serialVersionUID = -7513518372980592365L;
    @NotNull
    @Column(name = "studentinquiryid", nullable = false)
    private Integer studentinquiryid;

    @NotNull
    @Column(name = "teacherid", nullable = false)
    private Integer teacherid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentInquiryRequestId entity = (StudentInquiryRequestId) o;
        return Objects.equals(this.teacherid, entity.teacherid) &&
                Objects.equals(this.studentinquiryid, entity.studentinquiryid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherid, studentinquiryid);
    }

}