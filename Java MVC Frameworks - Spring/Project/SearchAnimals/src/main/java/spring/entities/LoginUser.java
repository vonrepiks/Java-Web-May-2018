package spring.entities;

import org.springframework.data.rest.core.config.Projection;
import spring.entities.User;

@Projection(name = "login", types = {User.class})
public interface LoginUser {

    String getUsername();

    String getPassword();
}
