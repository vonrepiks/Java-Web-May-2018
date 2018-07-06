package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import spring.entities.User;
import spring.entities.LoginUser;

import java.util.List;

@Repository
@RepositoryRestResource(excerptProjection = LoginUser.class)
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT *" +
            "  FROM users AS u\n" +
            "ORDER BY u.first_name DESC", nativeQuery = true)
    List<User> getUsersByFirstNameAsc();

    @Query(value = "SELECT u.username, u.password" +
            "  FROM users AS u\n" +
            "WHERE u.first_name = 'Ico'", nativeQuery = true)
    User getLoginUser();

    // With query string pass parameters
    @Query(value = "SELECT *" +
            "  FROM users AS u\n" +
            "WHERE u.first_name = :name", nativeQuery = true)
    User getUserByFirstName(@Param("name") String name);

    List<User> findUsersByOrderById();
}
