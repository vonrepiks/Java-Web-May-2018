package org.softuni.javache.utility;

import org.softuni.javache.WebConstants;
import org.softuni.javache.api.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class RequestHandlerLoadingService {
    private static final String LIB_FOLDER_PATH = WebConstants.SERVER_ROOT_FOLDER_PATH + "lib/";
    private static final String LIB_FOLDER_NOT_FOUND_EXCEPTION_MESSAGE = "Library folder not exist or is not a folder!";

    private Set<RequestHandler> requestHandlers;

    public RequestHandlerLoadingService() {
    }

    private String getFileNameWithoutExtension(String fileName) {
        return fileName.substring(0, fileName.indexOf("."));
    }

    private boolean isJarFile(File file) {
        return file.isFile() && file.getName().endsWith(".jar");
    }

    private void loadRequestHandler(Class<?> handlerClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        RequestHandler handlerObject =
                (RequestHandler) handlerClass
                        .getConstructor()
                        .newInstance();

        this.requestHandlers.add(handlerObject);
    }

    private void loadJarFile(JarFile jarFile, String cannonicalPath) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Enumeration<JarEntry> jarFileElements = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + cannonicalPath + "!/")};
        URLClassLoader ucl = URLClassLoader.newInstance(urls);

        while (jarFileElements.hasMoreElements()) {
            JarEntry currentEntry = jarFileElements.nextElement();

            if (!currentEntry.isDirectory() && currentEntry.getRealName().endsWith(".class")) {

                String className = currentEntry
                        .getRealName()
                        .replace(".class", "")
                        .replace("/", ".");

                Class<?> handlerClass = ucl.loadClass(className);

                if (RequestHandler.class.isAssignableFrom(handlerClass)) {
                    this.loadRequestHandler(handlerClass);
                }
            }
        }
    }

    private void loadLibraryFiles(Set<String> requestHandlerPriority) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File libraryFolder = new File(LIB_FOLDER_PATH);

        if (!libraryFolder.exists() || !libraryFolder.isDirectory()) {
            throw new IllegalArgumentException(LIB_FOLDER_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        File[] files = libraryFolder.listFiles();

        if (files == null) {
            return;
        }

        List<File> jarFiles = Arrays.stream(files).filter(this::isJarFile).collect(Collectors.toList());

        for (String currentRequestHandlerName : requestHandlerPriority) {
            File jarFile = jarFiles.stream()
                    .filter(x -> this.getFileNameWithoutExtension(x.getName()).equals(currentRequestHandlerName))
                    .findFirst()
                    .orElse(null);

            if (jarFile != null) {
                JarFile fileAsJar = new JarFile(jarFile.getCanonicalPath());
                this.loadJarFile(fileAsJar, jarFile.getCanonicalPath());
            }
        }
    }

    public Set<RequestHandler> getRequestHandlers() {
        return Collections.unmodifiableSet(this.requestHandlers);
    }

    public void loadRequestHandlers(Set<String> requestHandlersPriority) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        this.requestHandlers = new LinkedHashSet<>();

        this.loadLibraryFiles(requestHandlersPriority);
    }
}
