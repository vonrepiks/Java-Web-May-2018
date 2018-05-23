package org.softuni.casebook.utility;

public final class MimeTypeManager {
    private MimeTypeManager() {}

    public static String getMimeType(String fileName) {
        if(fileName.endsWith("css")) {
            return "text/css";
        } else if (fileName.endsWith("html")) {
            return "text/html";
        } else if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        }

        return "text/plain";
    }
}
