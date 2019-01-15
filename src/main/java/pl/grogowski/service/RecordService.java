package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.repository.RecordRepository;
import pl.grogowski.repository.UserRepository;

import java.math.BigDecimal;
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
    @Autowired
    CashFlowService cashFlowService;

    public List<Record> getRecordsForUserGivenDate(String userEmail, Integer month, Integer year) {
        List<Record> records = recordRepository.queryFindByUserAndByMonthAndByYear(userRepository.findByEmail(userEmail), month, year);
        if (records.isEmpty()) {
            List<Category> categories = categoryService.getCategoriesForUser(userEmail);
            createBlankRecordsForUser(userEmail, month, year, categories);
            records = recordRepository.queryFindByUserAndByMonthAndByYear(userRepository.findByEmail(userEmail), month, year);
        }
        for (Record r : records) {
            r.setSpending(cashFlowService.getCashOutflowForGivenRecord(userEmail, r));
        }
        return records;
    }

    private void createBlankRecordsForUser(String userEmail, Integer month, Integer year, List<Category> categories) {
        List<Record> toBePersisted = new ArrayList<>();
        for (Category c : categories) {
            Record newRecord = new Record();
            newRecord.setCategory(c);
            newRecord.setUser(userRepository.findByEmail(userEmail));
            newRecord.setBudgetedAmount(new BigDecimal(0));
            newRecord.setMonth(month);
            newRecord.setYear(year);
            toBePersisted.add(newRecord);
        }
        recordRepository.save(toBePersisted);
    }


}
