package org.softuni.casebook.routes;

import org.softuni.casebook.annotations.Controller;
import org.softuni.casebook.annotations.GetMapping;
import org.softuni.casebook.annotations.PostMapping;
import org.softuni.casebook.controllers.BaseController;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RoutesManagerImpl implements RoutesManager {
    private static final String CONTROLLERS_FOLDER_PATH = "C:\\SoftUni\\Java-Web-May-2018\\Java Web Development Basics\\Exercises\\Casebook\\src\\main\\java\\org\\softuni\\casebook\\controllers\\";
    private static final String FILE_PATH_DELIMITER = "\\\\src\\\\main\\\\java\\\\";
    private static final String JAVA_FILE_EXTENSION = ".java";

    private Map<String, ControllerMethodEntry<Method, BaseController>> getMappingRoutes;
    private Map<String, ControllerMethodEntry<Method, BaseController>> postMappingRoutes;

    public RoutesManagerImpl() {
        this.getMappingRoutes = new HashMap<>();
        this.postMappingRoutes = new HashMap<>();
    }

    private void initializeMappingRoutes(File[] files) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String classPath;
        for (File file : files) {
            if (file.isDirectory()) {
                initializeMappingRoutes(file.listFiles());
            } else {
                if (file.getName().endsWith(".java")) {
                    classPath = file.getPath().split(FILE_PATH_DELIMITER)[1].replace("\\", ".").replace(JAVA_FILE_EXTENSION, "");
                    Class<?> clazz = Class.forName(classPath);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        Constructor<?> controllerConstructor = clazz.getDeclaredConstructor();
                        BaseController controller = (BaseController) controllerConstructor.newInstance();
                        Method[] methods = clazz.getMethods();
                        for (Method method : methods) {
                            ControllerMethodEntry<Method, BaseController> entry = new ControllerMethodEntry<>(method, controller);
                            method.setAccessible(true);
                            if (method.isAnnotationPresent(GetMapping.class)) {
                                String route = method.getAnnotation(GetMapping.class).route();
                                this.getMappingRoutes.putIfAbsent(route, entry);
                            } else if (method.isAnnotationPresent(PostMapping.class)) {
                                String route = method.getAnnotation(GetMapping.class).route();
                                this.postMappingRoutes.putIfAbsent(route, entry);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initializeRoots() {
        try {
            initializeMappingRoutes(new File(CONTROLLERS_FOLDER_PATH).listFiles());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, ControllerMethodEntry<Method, BaseController>> getGetMappingRoutes() {
        return Collections.unmodifiableMap(this.getMappingRoutes);
    }

    @Override
    public Map<String, ControllerMethodEntry<Method, BaseController>> getPostMappingRoutes() {
        return Collections.unmodifiableMap(this.getPostMappingRoutes());
    }
}
