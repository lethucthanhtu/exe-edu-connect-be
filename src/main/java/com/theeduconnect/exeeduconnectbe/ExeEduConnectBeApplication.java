package com.theeduconnect.exeeduconnectbe;

import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ExeEduConnectBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExeEduConnectBeApplication.class, args);
    }
}
