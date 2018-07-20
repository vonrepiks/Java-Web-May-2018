package org.softuni.interceptors;

import org.softuni.dtos.logs.LogDto;
import org.softuni.services.LoggerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private final LoggerService loggerService;

    public LoggerInterceptor(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogDto logDto = new LogDto();

        HandlerMethod hm;
        try {
            hm = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            return;
        }
        Method method = hm.getMethod();
        if (method.getDeclaringClass().isAnnotationPresent(Controller.class)) {
            Class<?> declaringClass = method.getDeclaringClass();
            logDto.setTableName(declaringClass.getSimpleName().replace("Controller", ""));
        }

        logDto.setUser((String) request.getSession().getAttribute("username"));

        if (request.getRequestURI().contains("add")) {
            logDto.setOperation("add");
        } else if (request.getRequestURI().contains("edit")) {
            logDto.setOperation("edit");
        } else if (request.getRequestURI().contains("delete")) {
            logDto.setOperation("delete");
        }

        logDto.setModifyingDate(LocalDateTime.now());

        this.loggerService.create(logDto);
    }
}
