package com.theeduconnect.exeeduconnectbe.utils;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FirebaseUtils {
    public static String UploadFileToFirebase(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String blobName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Blob blob = bucket.create(blobName, file.getInputStream(), file.getContentType());
        return blob.getMediaLink();
    }
}
