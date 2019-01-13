package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grogowski.model.CashFlow;

public interface CashFlowRepository extends JpaRepository<CashFlow, Long> {
}
