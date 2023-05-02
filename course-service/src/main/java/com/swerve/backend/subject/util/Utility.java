package com.swerve.backend.subject.util;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class Utility {

     private static Map<String,MediaType> fileExtensionToMediaMapper=new HashMap<String,MediaType>() {{
        put("pdf", MediaType.APPLICATION_PDF);
        put("jpeg", MediaType.IMAGE_JPEG);
        put("jpg", MediaType.IMAGE_JPEG);
        put("png", MediaType.IMAGE_PNG);
        put("txt", MediaType.TEXT_PLAIN);
        put("html", MediaType.TEXT_HTML);
    }};
    public static Double roundToTwoDecimals(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
    public static String getFileExtension(String filename) {
        if(filename == null || filename.isEmpty()) {
            return "";
        }
        int dotIndex = filename.lastIndexOf(".");
        if(dotIndex == -1 || dotIndex == 0 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }

    public static MediaType getMediaTypeForFileName(String fileName) {
        String extension = getFileExtension(fileName);
        return fileExtensionToMediaMapper.get(extension)!=null ? fileExtensionToMediaMapper.get(extension):MediaType.APPLICATION_OCTET_STREAM;
    }
}
