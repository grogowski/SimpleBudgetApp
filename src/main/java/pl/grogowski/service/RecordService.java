package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.repository.RecordRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CashFlowService cashFlowService;

    public BigDecimal getTotalBudgetedForGivenMonth(String userEmail, LocalDate date) {
        BigDecimal total = new BigDecimal(0);
        for (Record r: getRecordsForGivenMonth(userEmail, date)) {
            total = total.add(r.getBudgetedAmount());
        }
        return total;
    }

    public List<Record> getRecordsForGivenMonth(String userEmail, LocalDate date) {
        List<Record> records = recordRepository.queryFindByUserAndByDate(userService.getUserByEmail(userEmail), date);
        if (records.isEmpty()) {
            List<Category> categories = categoryService.getCategoriesForUser(userEmail);
            createBlankRecordsForGivenMonth(userEmail, date, categories);
            records = recordRepository.queryFindByUserAndByDate(userService.getUserByEmail(userEmail), date);
        }
        for (Record r : records) {
            r.setSpending(cashFlowService.getSpendingForGivenRecord(userEmail, r));
            if (date.isAfter(LocalDate.now())) {
                r.setAvailable(r.getBudgetedAmount());
            } else {
                r.setAvailable(getBudgetedTillDate(userEmail, r).subtract(cashFlowService.getSpendingTillDate(userEmail, r)));
            }
        }
        return records;
    }

    private BigDecimal getBudgetedTillDate(String userEmail, Record r) {
        List<Record> records = recordRepository.queryFindByUserTillDate(userService.getUserByEmail(userEmail), r.getDate());
        BigDecimal total = new BigDecimal(0);
        for (Record record : records) {
            total = total.add(record.getBudgetedAmount());
        }
        return total;
    }

    private void createBlankRecordsForGivenMonth(String userEmail, LocalDate date, List<Category> categories) {
        List<Record> toBePersisted = new ArrayList<>();
        for (Category c : categories) {
            Record newRecord = new Record();
            newRecord.setCategory(c);
            newRecord.setUser(userService.getUserByEmail(userEmail));
            newRecord.setBudgetedAmount(new BigDecimal(0));
            newRecord.setDate(date);
            toBePersisted.add(newRecord);
        }
        recordRepository.save(toBePersisted);
    }


}
