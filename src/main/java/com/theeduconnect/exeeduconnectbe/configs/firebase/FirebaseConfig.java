package com.theeduconnect.exeeduconnectbe.configs.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(
                        "src/main/resources/educonnectdb-firebase-adminsdk-vcnpg-39111bcd74.json");

        FirebaseOptions options =
                new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setStorageBucket("educonnectdb.appspot.com")
                        .build();

        FirebaseApp.initializeApp(options);
    }
}
