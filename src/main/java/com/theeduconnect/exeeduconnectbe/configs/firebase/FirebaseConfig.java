package com.theeduconnect.exeeduconnectbe.configs.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource serviceAccountResource = new ClassPathResource("firebaseConfig.json");

        try (InputStream serviceAccount = serviceAccountResource.getInputStream()) {
            FirebaseOptions options =
                    new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setStorageBucket("educonnectdb.appspot.com")
                            .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
