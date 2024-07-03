package com.theeduconnect.exeeduconnectbe.features.jitsi;

import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @PostMapping("/create")
    @Operation(summary = "Create a new meeting by jitsi")
    public ResponseEntity<String> createMeeting() {
        // Generate unique meeting ID
        String meetingId = UUID.randomUUID().toString();
        // Return the meeting ID to the client
        String meetingLink = "https://meet.jit.si/" + meetingId;
        return ResponseEntity.ok(meetingLink);
    }
}
