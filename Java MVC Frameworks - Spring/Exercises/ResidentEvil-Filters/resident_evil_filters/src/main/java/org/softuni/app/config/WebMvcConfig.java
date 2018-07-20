package org.softuni.app.config;

import org.softuni.app.interceptors.LoginInterceptor;
import org.softuni.app.interceptors.LogoutInterceptor;
import org.softuni.app.interceptors.PreAuthenticateInterceptor;
import org.softuni.app.interceptors.RegisterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final PreAuthenticateInterceptor preAuthenticateInterceptor;

    private final LoginInterceptor loginInterceptor;

    private final RegisterInterceptor registerInterceptor;

    private final LogoutInterceptor logoutInterceptor;

    public WebMvcConfig(PreAuthenticateInterceptor preAuthenticateInterceptor, LoginInterceptor loginInterceptor, RegisterInterceptor registerInterceptor, LogoutInterceptor logoutInterceptor) {
        this.preAuthenticateInterceptor = preAuthenticateInterceptor;
        this.loginInterceptor = loginInterceptor;
        this.registerInterceptor = registerInterceptor;
        this.logoutInterceptor = logoutInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.preAuthenticateInterceptor);
        registry.addInterceptor(this.loginInterceptor).addPathPatterns("/users/login");
        registry.addInterceptor(this.registerInterceptor).addPathPatterns("/users/register");
        registry.addInterceptor(this.logoutInterceptor).addPathPatterns("/users/logout");
    }
}
