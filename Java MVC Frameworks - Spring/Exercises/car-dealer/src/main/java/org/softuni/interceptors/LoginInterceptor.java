package org.softuni.interceptors;

import org.softuni.dtos.users.UserDto;
import org.softuni.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("username");

        if (username != null) {
            response.sendRedirect("/");
            return false;
        }

        if (request.getMethod().equals("POST")) {
            String loginUsername = request.getParameter("username");
            String password = request.getParameter("password");

            UserDto userDto = this.userService.getUserByUsername(loginUsername);

            if (userDto == null) {
                response.sendRedirect("/");
                return false;
            }

            if (!password.equals(userDto.getPassword())) {
                response.sendRedirect("/users/login");
                return false;
            }

            request.getSession().setAttribute("username", userDto.getUsername());
        }

        return true;
    }
}
