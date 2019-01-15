package pl.grogowski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.grogowski.model.Record;
import pl.grogowski.model.User;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUser(User user);

    @Query("select r from Record r where r.user = ?1 and r.month = ?2 and r.year = ?3")
    List<Record> queryFindByUserAndByMonthAndByYear(User user, Integer month, Integer year);

}
