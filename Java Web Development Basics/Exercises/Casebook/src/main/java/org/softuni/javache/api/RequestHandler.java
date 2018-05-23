package org.softuni.javache.api;

import java.lang.reflect.InvocationTargetException;

public interface RequestHandler {
    byte[] handleRequest(String requestContent) throws InvocationTargetException, IllegalAccessException;

    boolean hasIntercepted();
}
