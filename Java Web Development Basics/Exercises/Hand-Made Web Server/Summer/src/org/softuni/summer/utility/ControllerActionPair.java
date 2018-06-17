package org.softuni.summer.utility;

import java.lang.reflect.Method;
import java.util.*;

public class ControllerActionPair {
    private Object controller;
    private Method action;
    private Set<Object> parameters;

    public ControllerActionPair(Object controller, Method method) {
        this.setController(controller);
        this.setAction(method);
        this.parameters = new LinkedHashSet<>();
    }

    private void setController(Object controller) {
        this.controller = controller;
    }

    private void setAction(Method action) {
        this.action = action;
    }

    public Object getController() {
        return this.controller;
    }

    public Method getAction() {
        return this.action;
    }

    public Set<Object> getParameters() {
        return Collections.unmodifiableSet(this.parameters);
    }

    public void addParameter(Object value) {
        this.parameters.add(value);
    }

    public void clearParameters() {
        this.parameters.clear();
    }
}
