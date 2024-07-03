package com.theeduconnect.exeeduconnectbe.features.attendingCourse.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveAttendingCourseTransactionRequest {
    private int transactionid;
    private int attendingcourseid;
}
