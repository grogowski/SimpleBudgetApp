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
            createBlankRecordsForGivenMonth(userEmail, date, categoryService.getCategoriesForUser(userEmail, false));
            records = recordRepository.queryFindByUserAndByDate(userService.getUserByEmail(userEmail), date);
        }
        for (Record r : records) {
            setTransientAttributes(r, userEmail);
        }
        return records;
    }

    private BigDecimal getBudgetedTillDateForUserAndCategory(String userEmail, Record r) {
        List<Record> records = recordRepository.queryFindByUserByCategoryTillDate(userService.getUserByEmail(userEmail), r.getDate(), r.getCategory());
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

    private void setTransientAttributes(Record r, String userEmail) {
        r.setSpending(cashFlowService.getSpendingForGivenRecord(userEmail, r));
        if (r.getDate().isAfter(LocalDate.now())) {
            r.setAvailable(r.getBudgetedAmount());
        } else {
            r.setAvailable(getBudgetedTillDateForUserAndCategory(userEmail, r).subtract(cashFlowService.getSpendingTillDate(userEmail, r)));
        }
    }

    public List<LocalDate> getExistingRecordsDates(String userEmail) {
        return recordRepository.queryFindDistinctDatesByUser(userService.getUserByEmail(userEmail));
    }

    public void editRecord(String recordId, String newBudgetedAmount) {
        Record toBeEdited = recordRepository.findOne(Long.parseLong(recordId));
        toBeEdited.setBudgetedAmount(new BigDecimal(newBudgetedAmount));
        recordRepository.save(toBeEdited);
    }

    public void addRecord(Record r) {
        recordRepository.save(r);
    }

    public Record getRecord(String recordId, String userEmail) {
        Record r = recordRepository.findOne(Long.parseLong(recordId));
        setTransientAttributes(r, userEmail);
        return r;
    }


    public List<BigDecimal> getBudgetedForGivenMonth(String userEmail, LocalDate month) {
        List<BigDecimal> budgeted = new ArrayList<>();
        for (Record r: getRecordsForGivenMonth(userEmail, month)) {
            budgeted.add(r.getBudgetedAmount());
        }
        return budgeted;
    }

    public List<BigDecimal> getSpendingsForGivenMonth(String userEmail, LocalDate month) {
        List<BigDecimal> spendings = new ArrayList<>();
        for (Record r: getRecordsForGivenMonth(userEmail, month)) {
            spendings.add(r.getSpending());
        }
        return spendings;
    }

    public List<String> getCategoriesNamesForUser(String userEmail, LocalDate month) {
        List<String> categoriesNames = new ArrayList<>();
        for (Record r: getRecordsForGivenMonth(userEmail, month)) {
            categoriesNames.add(r.getCategory().getName());
        }
        return categoriesNames;
    }
}
