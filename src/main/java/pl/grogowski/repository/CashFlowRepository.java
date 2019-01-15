package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.grogowski.model.CashFlow;
import pl.grogowski.model.Category;
import pl.grogowski.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CashFlowRepository extends JpaRepository<CashFlow, Long> {

    @Query("select c from CashFlow c where c.user = ?1 and c.category = ?2 and c.date between ?3 and ?4")
    List<CashFlow> queryFindByUserAndByCategoryAndByDate(User user, Category category, LocalDate date1, LocalDate date2);
}
