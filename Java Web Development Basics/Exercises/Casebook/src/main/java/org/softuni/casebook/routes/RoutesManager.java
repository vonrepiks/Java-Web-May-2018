package org.softuni.casebook.routes;

import org.softuni.casebook.controllers.dynamic.DynamicBaseController;
import org.softuni.casebook.template_engine.LimeLeafImpl;
import org.softuni.casebook.utility.Notification;
import org.softuni.javache.http.HttpSessionStorage;

import java.lang.reflect.Method;
import java.util.Map;

public interface RoutesManager {
    @SuppressWarnings("unchecked")
    void initializeRoutes(HttpSessionStorage sessionStorage, LimeLeafImpl limeLeaf, Notification notification);

    Map<String, ControllerMethodEntry<Method, DynamicBaseController>> getGetMappingRoutes();

    Map<String, ControllerMethodEntry<Method, DynamicBaseController>> getPostMappingRoutes();
}
