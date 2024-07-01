package com.theeduconnect.exeeduconnectbe.features.jitsi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @PostMapping("/create")
    public ResponseEntity<String> createMeeting() {
        // Generate unique meeting ID
        String meetingId = UUID.randomUUID().toString();
        // Return the meeting ID to the client
        String meetingLink = "https://meet.jit.si/" + meetingId;
        return ResponseEntity.ok(meetingLink);
    }
}

