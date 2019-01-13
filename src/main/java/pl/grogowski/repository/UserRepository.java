package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grogowski.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
