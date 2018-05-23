package org.softuni.casebook.routes;

import org.softuni.casebook.controllers.BaseController;

import java.lang.reflect.Method;
import java.util.Map;

public class ControllerMethodEntry<K extends Method, V extends BaseController> implements Map.Entry<Method, BaseController> {
    private final Method method;
    private BaseController controller;

    public ControllerMethodEntry(Method method, BaseController controller) {
        this.method = method;
        this.controller = controller;
    }

    @Override
    public Method getKey() {
        return this.method;
    }

    @Override
    public BaseController getValue() {
        return this.controller;
    }

    @Override
    public BaseController setValue(BaseController controller) {
        return this.controller = controller;
    }
}
