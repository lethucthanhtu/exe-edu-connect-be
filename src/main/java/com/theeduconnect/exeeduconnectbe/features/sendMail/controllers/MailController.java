package com.theeduconnect.exeeduconnectbe.features.sendMail.controllers;

import com.theeduconnect.exeeduconnectbe.constants.mail.endpoints.MailEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.mail.responseCodes.MailServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.mail.serviceMessages.MailServiceMessage;
import com.theeduconnect.exeeduconnectbe.constants.user.responseCodes.UserServiceHttpResponseCodes;
import com.theeduconnect.exeeduconnectbe.constants.user.serviceMessages.UserServiceMessages;
import com.theeduconnect.exeeduconnectbe.features.sendMail.payload.request.SendMailRequest;
import com.theeduconnect.exeeduconnectbe.features.sendMail.payload.response.MailServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.user.payload.request.NewUserRequest;
import com.theeduconnect.exeeduconnectbe.features.user.payload.response.UserServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {
    @Autowired
    JavaMailSender mailSender;

    @PostMapping(MailEndpoints.SEND_MAIL)
    @Operation(summary = "Send mail")
    public ResponseEntity<MailServiceResponse> sendMail(@Valid @RequestBody SendMailRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());
            mailSender.send(message);
            return ResponseEntity.ok(new MailServiceResponse(MailServiceHttpResponseCodes.SEND_MAIL_SUCCESSFUL, MailServiceMessage.SEND_MAIL_SUCCESSFUL, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MailServiceResponse(MailServiceHttpResponseCodes.SEND_MAIL_FAILED, MailServiceMessage.SEND_MAIL_FAILED, null));
        }
    }
}