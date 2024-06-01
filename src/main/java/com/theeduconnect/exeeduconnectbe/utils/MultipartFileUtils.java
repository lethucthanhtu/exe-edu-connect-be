package com.theeduconnect.exeeduconnectbe.utils;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileUtils {
    public static boolean DoesFileMatchExtensions(MultipartFile file, List<String> extensions) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String fileExtension = getFileExtension(originalFilename);
            if (extensions.contains(fileExtension.toLowerCase())) return true;
        }
        return false;
    }

    private static String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1);
        }
        return ""; // No extension found
    }
}
