package org.softuni.javache.utility;

public class LoggingService {
    public void info(String content) {
        System.out.println(String.format("\u001B[36m[INFO]: %s", content));
    }

    public void warning(String content) {
        System.out.println(String.format("\u001B[33m[WARNING]: %s", content));
    }

    public void error(String content) {
        System.out.println(String.format("\u001B[31m[ERROR] %s", content));
    }
}
