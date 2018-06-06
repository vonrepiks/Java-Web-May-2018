package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
