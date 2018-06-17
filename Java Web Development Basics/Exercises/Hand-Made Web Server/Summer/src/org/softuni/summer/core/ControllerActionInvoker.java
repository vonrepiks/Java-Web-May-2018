package org.softuni.summer.core;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.broccolina.solet.HttpSoletResponse;
import org.softuni.javache.http.HttpSession;
import org.softuni.summer.api.Model;
import org.softuni.summer.utility.ControllerActionPair;
import org.softuni.summer.utility.ParametersValidator;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ControllerActionInvoker {
    private DependencyContainer dependencyContainer;

    public ControllerActionInvoker(DependencyContainer dependencyContainer) {
        this.dependencyContainer = dependencyContainer;
    }

    private Object instantiateBindingModel(Parameter currentParameter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return currentParameter
                .getType()
                .getDeclaredConstructor()
                .newInstance();
    }

    private Object parseValue(Class<?> type, String value) {
        if (type.getSimpleName().equals(int.class.getSimpleName()) || type.getSimpleName().equals(Integer.class.getSimpleName())) {
            return Integer.valueOf(value);
        }
        if (type.getSimpleName().equals(long.class.getSimpleName()) || type.getSimpleName().equals(Long.class.getSimpleName())) {
            return Long.valueOf(value);
        }
        if (type.getSimpleName().equals(double.class.getSimpleName()) || type.getSimpleName().equals(Double.class.getSimpleName())) {
            return Double.valueOf(value);
        }
        if (type.getSimpleName().equals(boolean.class.getSimpleName()) || type.getSimpleName().equals(Boolean.class.getSimpleName())) {
            return Boolean.valueOf(value);
        }
        return value;
    }

    private void populateBindingModel(Object bindingModel, HttpSoletRequest httpSoletRequest) {
        Arrays.stream(bindingModel.getClass()
                .getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);

                    if (httpSoletRequest.getBodyParameters().containsKey(field.getName())) {
                        try {
                            String parameterValue = URLDecoder.decode(httpSoletRequest.getBodyParameters().get(field.getName()), "UTF-8");

                            Object parsedParameterValue = this.parseValue(field.getType(), parameterValue);

                            field.set(bindingModel, parsedParameterValue);
                        } catch (IllegalAccessException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private Object[] getActionArguments(Method action, Set<Object> parameters) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Parameter[] actionParameters = action.getParameters();
        Object[] actionArguments = new Object[actionParameters.length];
        Iterator<Object> parametersIterator = parameters.iterator();

        for (int i = 0; i < actionParameters.length; i++) {
            Parameter currentParameter = actionParameters[i];

            if (ParametersValidator.isPathVariable(currentParameter) && parametersIterator.hasNext()) {
                Object pathVariable = parametersIterator.next();
                actionArguments[i] = this.parseValue(currentParameter.getType(), pathVariable.toString());
            } else if (ParametersValidator.isModel(currentParameter)) {
                actionArguments[i] = this.dependencyContainer.getObject(Model.class.getSimpleName());
            } else if (ParametersValidator.isHttpSoletRequest(currentParameter)) {
                actionArguments[i] = this.dependencyContainer.getObject(HttpSoletRequest.class.getSimpleName());
            } else if (ParametersValidator.isHttpSoletResponse(currentParameter)) {
                actionArguments[i] = this.dependencyContainer.getObject(HttpSoletResponse.class.getSimpleName());
            } else if (ParametersValidator.isHttpSession(currentParameter)) {
                actionArguments[i] = this.dependencyContainer.getObject(HttpSession.class.getSimpleName());
            } else if (ParametersValidator.isBindingModel(currentParameter)) {
                Object bindingModel = this.instantiateBindingModel(currentParameter);

                this.populateBindingModel(bindingModel, (HttpSoletRequest) this.dependencyContainer.getObject(HttpSoletRequest.class.getSimpleName()));

                actionArguments[i] = bindingModel;
            }
        }

        return actionArguments;
    }

    public Object invokeAction(ControllerActionPair cap) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        System.out.println("invokeAction row 101");
        System.out.println(cap.getAction());
        System.out.println(cap.getParameters());
        Object[] actionArguments = this.getActionArguments(cap.getAction(), cap.getParameters());
        Arrays.stream(actionArguments).forEach(x -> {
            System.out.println(x);
        });
        if (actionArguments.length > 0) {
            System.out.println("row 108 " + actionArguments.length);
            return cap.getAction().invoke(cap.getController(), actionArguments);
        }

        return cap.getAction().invoke(cap.getController());
    }
}
