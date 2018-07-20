package org.softuni.config;

import org.softuni.interceptors.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final PreAuthenticateInterceptor preAuthenticateInterceptor;

    private final LoginInterceptor loginInterceptor;

    private final RegisterInterceptor registerInterceptor;

    private final LogoutInterceptor logoutInterceptor;

    private final LoggerInterceptor loggerInterceptor;

    public WebMvcConfig(PreAuthenticateInterceptor preAuthenticateInterceptor, LoginInterceptor loginInterceptor, RegisterInterceptor registerInterceptor, LogoutInterceptor logoutInterceptor, LoggerInterceptor loggerInterceptor) {
        this.preAuthenticateInterceptor = preAuthenticateInterceptor;
        this.loginInterceptor = loginInterceptor;
        this.registerInterceptor = registerInterceptor;
        this.logoutInterceptor = logoutInterceptor;
        this.loggerInterceptor = loggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.preAuthenticateInterceptor);
        registry.addInterceptor(this.loginInterceptor).addPathPatterns("/users/login");
        registry.addInterceptor(this.registerInterceptor).addPathPatterns("/users/register");
        registry.addInterceptor(this.logoutInterceptor).addPathPatterns("/users/logout");
        registry.addInterceptor(this.loggerInterceptor)
                .addPathPatterns("/cars/add", "/cars/add-parts", "/sales/add", "/suppliers/add", "/suppliers/edit/**", "/suppliers/delete/**");
    }
}
