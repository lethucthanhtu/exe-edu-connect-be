package com.theeduconnect.exeeduconnectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RequestMapping("/")
@RestController
public class ExeEduConnectBeApplication {

	@GetMapping("/hello")
	public ResponseEntity<?> hello() {
		return ResponseEntity.ok().body("hello");
	}

	public static void main(String[] args) {
		SpringApplication.run(ExeEduConnectBeApplication.class, args);
	}

}
