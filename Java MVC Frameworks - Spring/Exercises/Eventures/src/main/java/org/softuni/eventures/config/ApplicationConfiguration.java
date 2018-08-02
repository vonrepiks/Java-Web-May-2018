package org.softuni.eventures.config;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.factories.UserRoleFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRoleFactory userRoleFactory() {
        return new UserRoleFactory();
    }
}
