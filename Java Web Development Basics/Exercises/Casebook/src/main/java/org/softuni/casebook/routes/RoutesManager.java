package org.softuni.casebook.routes;

import org.softuni.casebook.controllers.BaseController;

import java.lang.reflect.Method;
import java.util.Map;

public interface RoutesManager {
    @SuppressWarnings("unchecked")
    void initializeRoots();

    Map<String, ControllerMethodEntry<Method, BaseController>> getGetMappingRoutes();

    Map<String, ControllerMethodEntry<Method, BaseController>> getPostMappingRoutes();
}
