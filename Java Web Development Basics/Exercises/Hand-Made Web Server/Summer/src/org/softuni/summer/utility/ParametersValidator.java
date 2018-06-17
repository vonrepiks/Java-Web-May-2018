package org.softuni.summer.utility;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.broccolina.solet.HttpSoletResponse;
import org.softuni.javache.http.HttpSession;
import org.softuni.summer.api.Model;

import java.lang.reflect.Parameter;

public final class ParametersValidator {
    private ParametersValidator() {}

    public static boolean isPrimitive(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(int.class.getSimpleName())
                || parameterTypeName.equals(long.class.getSimpleName())
                || parameterTypeName.equals(double.class.getSimpleName())
                || parameterTypeName.equals(boolean.class.getSimpleName());
    }

    public static boolean isString(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(String.class.getSimpleName());
    }

    public static boolean isWrapper(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(Integer.class.getSimpleName())
                || parameterTypeName.equals(Long.class.getSimpleName())
                || parameterTypeName.equals(Double.class.getSimpleName())
                || parameterTypeName.equals(Boolean.class.getSimpleName());
    }

    public static boolean isHttpSoletRequest(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(HttpSoletRequest.class.getSimpleName());
    }

    public static boolean isHttpSoletResponse(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(HttpSoletResponse.class.getSimpleName());
    }

    public static boolean isHttpSession(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(HttpSession.class.getSimpleName());
    }

    public static boolean isModel(Parameter parameter) {
        if (parameter == null) {
            return false;
        }

        String parameterTypeName = parameter.getType().getSimpleName();

        return parameterTypeName.equals(Model.class.getSimpleName());
    }


    public static boolean isBindingModel(Parameter parameter) {
        return parameter != null
                && !isPrimitive(parameter)
                && !isWrapper(parameter)
                && !isString(parameter)
                && !isHttpSoletRequest(parameter)
                && !isHttpSoletResponse(parameter)
                && !isHttpSession(parameter)
                && !isModel(parameter);
    }

    public static boolean isPathVariable(Parameter parameter) {
        return isPrimitive(parameter) || isWrapper(parameter) || isString(parameter);
    }
}
