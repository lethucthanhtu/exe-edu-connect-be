package com.theeduconnect.exeeduconnectbe.features.teacherProfile.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theeduconnect.exeeduconnectbe.constants.teacherProfile.TeacherProfileEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.JwtService;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.dtos.UpdateTeacherProfileDto;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.payload.response.TeacherProfileServiceResponse;
import com.theeduconnect.exeeduconnectbe.features.teacherProfile.services.TeacherProfileService;
import com.theeduconnect.exeeduconnectbe.utils.TimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TeacherProfileController {

    private final TeacherProfileService teacherProfileService;
    private final JwtService jwtService;

    @Autowired
    public TeacherProfileController(
            TeacherProfileService teacherProfileService, JwtService jwtService) {
        this.teacherProfileService = teacherProfileService;
        this.jwtService = jwtService;
    }

    @PutMapping(TeacherProfileEndpoints.UPDATE_PROFILE)
    @Operation(summary = "Updates the profile of a newly registered Teacher.")
    public ResponseEntity<TeacherProfileServiceResponse> GetTeachersByCourseCategory(
            @RequestHeader("Authorization") String rawJwtToken,
            @RequestParam String request,
            //            @RequestParam("cardphoto") MultipartFile cardphoto,
            //            @RequestParam("nationalid") MultipartFile nationalid,
            //            @RequestParam("cv") MultipartFile cv,
            @RequestParam("certificates") MultipartFile[] certificates) {

        try {
            int userId = jwtService.extractUserId(rawJwtToken);
            UpdateTeacherProfileDto dto =
                    BuildUpdateTeacherProfileDto(request, userId, certificates);
            TeacherProfileServiceResponse response = teacherProfileService.update(dto);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new TeacherProfileServiceResponse(500, e.getMessage(), null),
                    HttpStatusCode.valueOf(500));
        }
    }

    private UpdateTeacherProfileDto BuildUpdateTeacherProfileDto(
            String request,
            int userId,
            //                                                                 MultipartFile
            // cardphoto,
            //                                                                 MultipartFile
            // nationalid,
            //                                                                 MultipartFile cv,
            MultipartFile[] certificates)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonData = objectMapper.readValue(request, Map.class);
        UpdateTeacherProfileDto dto = new UpdateTeacherProfileDto();
        dto.setTeacherId(userId);
        dto.setFullname(jsonData.get("fullname").toString());
        dto.setDateofbirth(TimeUtils.StringToLocalDate(jsonData.get("dateofbirth").toString()));
        //        dto.setSex(jsonData.get("sex").toString());
        dto.setAddress(jsonData.get("address").toString());
        //        dto.setEmail(jsonData.get("email").toString());
        dto.setPhone(jsonData.get("phone").toString());
        dto.setOccupation(jsonData.get("occupation").toString());
        dto.setSchool(jsonData.get("school").toString());
        dto.setSpecialization(jsonData.get("specialization").toString());
        //        dto.setCardphoto(cardphoto);
        //        dto.setNationalid(nationalid);
        //        dto.setCv(cv);
        dto.setCertificates(certificates);
        return dto;
    }
}
