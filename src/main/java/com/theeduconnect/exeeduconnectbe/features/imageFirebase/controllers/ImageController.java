package com.theeduconnect.exeeduconnectbe.features.imageFirebase.controllers;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageController {

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String blobName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Blob blob = bucket.create(blobName, file.getInputStream(), file.getContentType());
        return "File uploaded successfully: " + blob.getMediaLink();
    }
}

