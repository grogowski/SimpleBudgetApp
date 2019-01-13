package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grogowski.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
