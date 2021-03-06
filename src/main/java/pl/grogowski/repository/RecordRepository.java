package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUser(User user);

    @Query("select r from Record r where r.user = ?1 and r.date = ?2")
    List<Record> queryFindByUserAndByDate(User user, LocalDate date);

    @Query("select r from Record r where r.user = ?1 and r.date between '1900-01-01' and ?2 and r.category=?3")
    List<Record> queryFindByUserByCategoryTillDate(User user, LocalDate date, Category category);

    @Query("select distinct r.date from Record r where r.user = ?1")
    List<LocalDate> queryFindDistinctDatesByUser(User user);

}
