package org.softuni.interceptors;

import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.users.UserDto;
import org.softuni.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Component
public class PreAuthenticateInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    public PreAuthenticateInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm;
        try {
            hm = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            return true;
        }
        Method method = hm.getMethod();
        if (method.getDeclaringClass().isAnnotationPresent(Controller.class)) {
            Class<?> declaringClass = method.getDeclaringClass();

            boolean loggedIn = false;
            String roleName = null;
            if (declaringClass.isAnnotationPresent(PreAuthenticate.class)) {
                PreAuthenticate annotation = declaringClass.getAnnotation(PreAuthenticate.class);

                loggedIn = annotation.loggedIn();
                roleName = annotation.inRole();
            }
            if (method.isAnnotationPresent(PreAuthenticate.class)) {
                PreAuthenticate annotation = method.getAnnotation(PreAuthenticate.class);
                loggedIn = annotation.loggedIn();
                roleName = annotation.inRole();
            }

            HttpSession session = request.getSession();

            String username;
            if (loggedIn) {
                username = (String) session.getAttribute("username");

                if (username == null) {
                    response.sendRedirect("/users/login");
                    return false;
                }
            } else {
                return true;
            }

            UserDto user = this.userService.getUserByUsername(username);

            String finalRoleName = roleName;
            if (user.getRoles().stream().noneMatch(role -> role.getName().equals(finalRoleName) || role.getName().equals("ADMIN"))) {
                response.sendRedirect("/users/login");
                return false;
            }
        }

        return true;
    }
}
