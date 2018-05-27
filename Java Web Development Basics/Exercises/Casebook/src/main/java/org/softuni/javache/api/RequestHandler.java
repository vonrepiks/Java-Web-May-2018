package org.softuni.javache.api;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public interface RequestHandler {
    byte[] handleRequest(String requestContent) throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException;

    boolean hasIntercepted();
}
