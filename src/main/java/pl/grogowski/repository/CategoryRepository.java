package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grogowski.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
