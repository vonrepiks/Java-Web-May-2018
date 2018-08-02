package org.softuni.eventures.config;

import org.softuni.eventures.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration
        extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public ApplicationSecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();

        repository.setSessionAttributeName("_csrf");

        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/users/login", "/users/register").anonymous()
                    .antMatchers("/css/**", "/javascript/**").permitAll()
                    .antMatchers("/events/create", "/orders/all").hasAuthority("ADMIN")
                    .antMatchers("/home", "/logout", "/events/all", "/events/my").hasAnyAuthority("USER", "ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/users/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home")
                .and()
                .rememberMe()
                    .rememberMeParameter("rememberMe")
                    .key("PLYOK")
                    .userDetailsService(this.userService)
                    .rememberMeCookieName("KLYOK")
                    .tokenValiditySeconds(1200);
//                .and()
//                .exceptionHandling()
//                    .accessDeniedPage("/unauthorized")
        ;
    }
}
