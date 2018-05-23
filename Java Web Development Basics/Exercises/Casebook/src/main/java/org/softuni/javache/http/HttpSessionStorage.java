package org.softuni.javache.http;

import java.util.Map;

public interface HttpSessionStorage {
    HttpSession getById(String sessionId);

    void addSession(HttpSession session);

    void refreshSessions();

    Map<String, HttpSession> getAllSessions();
}
