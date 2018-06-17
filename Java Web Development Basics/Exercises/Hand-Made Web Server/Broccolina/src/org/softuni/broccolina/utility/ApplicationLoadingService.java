package org.softuni.broccolina.utility;

import org.softuni.broccolina.solet.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ApplicationLoadingService {
    private String applicationFolderPath;
    private static final String LIB_FOLDER_NOT_FOUND_EXCEPTION_MESSAGE = "Library folder not exist or is not a folder!";

    private Map<String, HttpSolet> loadedApplications;

    private JarFileUnzipService jarFileUnzipService;

    private List<URL> libraryClassUrls;

    public ApplicationLoadingService() {
        this.jarFileUnzipService = new JarFileUnzipService();
    }

    private boolean isJarFile(File file) {
        return file.isFile() && file.getName().endsWith(".jar");
    }

    private void loadSolet(Class<?> soletClass, String applicationName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (soletClass == null
                || (soletClass.getSuperclass() == null)
                || (!soletClass
                .getSuperclass()
                .getSimpleName()
                .equals(BaseHttpSolet.class.getSimpleName()))) {
            return;
        }

        Object soletObject = soletClass
                .getDeclaredConstructor()
                .newInstance();

        Object soletAnnotation = Arrays.stream(soletClass.getAnnotations())
                .filter(x -> x.annotationType().getSimpleName().equals(WebSolet.class.getSimpleName()))
                .findFirst()
                .orElse(null);

        String soletRoute = soletAnnotation
                .getClass()
                .getMethod("route")
                .invoke(soletAnnotation)
                .toString();

        if (!applicationName.equals("ROOT")) {
            soletRoute = "/" + applicationName + soletRoute;
        }

        HttpSolet httpSoletProxy = (HttpSolet) Proxy.newProxyInstance(
                HttpSolet.class.getClassLoader(),
                new Class[]{HttpSolet.class},
                (proxy, method, args) -> {
                    Method extractedMethod = Arrays.stream(soletClass.getMethods())
                            .filter(x -> x.getName().equals(method.getName()))
                            .findFirst()
                            .orElse(null);

                    if (extractedMethod.getName().equals("service")) {

                        Class<?>[] requestedParameters = extractedMethod.getParameterTypes();

                        Object proxyRequest = Proxy.newProxyInstance(
                                requestedParameters[0].getClassLoader(),
                                new Class[]{requestedParameters[0]},
                                (requestProxy, requestMethod, requestArgs) -> {
                                    Method extractedRequestMethod = Arrays.stream(args[0].getClass().getMethods())
                                            .filter(x -> x.getName().equals(requestMethod.getName()))
                                            .findFirst()
                                            .orElse(null);

                                    return extractedRequestMethod.invoke(args[0], requestArgs);
                                }
                        );

                        Object proxyResponse = Proxy.newProxyInstance(
                                requestedParameters[1].getClassLoader(),
                                new Class[]{requestedParameters[1]},
                                (responseProxy, responseMethod, responseArgs) -> {
                                    Method extractedResponseMethod = Arrays.stream(args[1].getClass().getMethods())
                                            .filter(x -> x.getName().equals(responseMethod.getName()))
                                            .findFirst()
                                            .orElse(null);

                                    return extractedResponseMethod.invoke(args[1], responseArgs);
                                }
                        );

                        return extractedMethod.invoke(soletObject, proxyRequest, proxyResponse);
                    } else if (extractedMethod.getName().equals("init")) {
                        SoletConfig soletConfigObj = new SoletConfigImpl();
                        soletConfigObj.setAttribute("application-folder", this.applicationFolderPath);

                        return extractedMethod.invoke(soletObject, soletConfigObj);
                    }

                    return extractedMethod.invoke(soletObject);
                }
        );

        this.loadedApplications.put(soletRoute, httpSoletProxy);
    }

    private void loadClass(File currentFile, URLClassLoader classLoader, String applicationName, String packageName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (currentFile.isDirectory()) {
            for (File childFile : currentFile.listFiles()) {
                this.loadClass(childFile, classLoader, applicationName, packageName + currentFile.getName() + ".");
            }
        } else {
            if (!currentFile.getName().endsWith(".class")) return;

            String className = packageName.replace("classes.", "") + currentFile
                    .getName()
                    .replace(".class", "")
                    .replace("/", ".");

            Class<?> currentClassFile = classLoader.loadClass(className);

            this.loadSolet(currentClassFile, applicationName);
        }
    }

    private void loadApplicationClasses(String classesFolderRootPath, String applicationName) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        File classesRootDirectory = new File(classesFolderRootPath);

        if (!classesRootDirectory.exists() || !classesRootDirectory.isDirectory()) {
            return;
        }

        this.libraryClassUrls.add(new URL("file:/" + classesRootDirectory.getCanonicalPath() + File.separator));
        URLClassLoader classLoader = new URLClassLoader(this.libraryClassUrls.toArray(new URL[this.libraryClassUrls.size()]));

        this.loadClass(classesRootDirectory, classLoader, applicationName, "");
    }

    private void loadLibraryFile(JarFile library, String canonicalPath, String applicationName) throws MalformedURLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Enumeration<JarEntry> jarFileElements = library.entries();

        while (jarFileElements.hasMoreElements()) {
            JarEntry currentEntry = jarFileElements.nextElement();

            if (!currentEntry.isDirectory() && currentEntry.getRealName().endsWith(".class")) {
                URL[] urls = new URL[]{new URL("jar:file:" + canonicalPath + "!/")};
                URLClassLoader ucl = new URLClassLoader(urls);

                String className = currentEntry
                        .getRealName()
                        .replace(".class", "")
                        .replace("/", ".");

                Class<?> currentClassFile = ucl.loadClass(className);

                this.loadSolet(currentClassFile, applicationName);

                this.libraryClassUrls.add(urls[0]);
            }
        }
    }

    private void loadApplicationsLibraries(String librariesFolderRootPath, String applicationName) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.libraryClassUrls = new ArrayList<>();

        File libraryFolder = new File(librariesFolderRootPath);

        if (!libraryFolder.exists() || !libraryFolder.isDirectory()) {
            throw new IllegalArgumentException(LIB_FOLDER_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        File[] files = libraryFolder.listFiles();

        if (files == null) {
            return;
        }

        List<File> jarFiles = Arrays.stream(files).filter(this::isJarFile).collect(Collectors.toList());

        for (File jarFile : jarFiles) {
            if (jarFile != null) {
                JarFile fileAsJar = new JarFile(jarFile.getCanonicalPath());
                this.loadLibraryFile(fileAsJar, jarFile.getCanonicalPath(), applicationName);
            }
        }
    }

    private void loadApplicationFromFolder(String applicationRootFolderPath, String applicationName) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.applicationFolderPath = applicationRootFolderPath;

        String classesFolderRootPath = applicationRootFolderPath + "classes" + File.separator;
        String librariesFolderRootPath = applicationRootFolderPath + "lib" + File.separator;

        this.loadApplicationsLibraries(librariesFolderRootPath, applicationName);
        this.loadApplicationClasses(classesFolderRootPath, applicationName);
    }

    public Map<String, HttpSolet> loadApplications(String applicationsFolderPath) throws IOException {
        this.loadedApplications = new HashMap<>();

        File applicationsFolder = new File(applicationsFolderPath);

        try {
            if (applicationsFolder.exists() && applicationsFolder.isDirectory()) {
                File[] files = applicationsFolder.listFiles();

                List<File> jarFiles = Arrays.stream(files).filter(this::isJarFile).collect(Collectors.toList());

                for (File jarFile : jarFiles) {
                    this.jarFileUnzipService.unzipJar(jarFile);

                    this.loadApplicationFromFolder(jarFile.getCanonicalPath().replace(".jar", "/"), jarFile.getName().replace(".jar", ""));
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return this.loadedApplications;
    }
}
