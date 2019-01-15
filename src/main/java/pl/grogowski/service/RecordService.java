package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.repository.RecordRepository;
import pl.grogowski.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    CategoryService categoryService;

    public List<Record> getRecordsForUserGivenDate(String userEmail, LocalDate date) {
        List<Record> records = recordRepository.queryFindByUserAndByMonthAndByYear(userRepository.findByEmail(userEmail), date.getMonthValue(), date.getYear());
        if (records.isEmpty()) {
            List<Category> categories = categoryService.getCategoriesForUser(userEmail);
            createBlankRecordsForUser(userEmail, date, categories);
            records = recordRepository.queryFindByUserAndByMonthAndByYear(userRepository.findByEmail(userEmail), date.getMonthValue(), date.getYear());
        }
        return records;
    }

    private void createBlankRecordsForUser(String userEmail, LocalDate date, List<Category> categories) {
        List<Record> toBePersisted = new ArrayList<>();
        for (Category c: categories) {
            Record newRecord = new Record();
            newRecord.setCategory(c);
            newRecord.setUser(userRepository.findByEmail(userEmail));
            newRecord.setBudgetedAmount(new BigDecimal(0));
            newRecord.setMonth(date.getMonthValue());
            newRecord.setYear(date.getYear());
            toBePersisted.add(newRecord);
        }
        recordRepository.save(toBePersisted);
    }


}
