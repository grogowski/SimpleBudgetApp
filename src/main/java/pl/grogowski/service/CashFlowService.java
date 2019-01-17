package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.CashFlow;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.repository.CashFlowRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CashFlowService {
    @Autowired
    CashFlowRepository cashFlowRepository;
    @Autowired
    UserService userService;

    public BigDecimal getSpendingForGivenRecord(String userEmail, Record record) {
        List<CashFlow> outflows = getCashFlowsForGivenCategoryAndMonth(userEmail, record.getDate(), record.getCategory());
        BigDecimal sum = new BigDecimal(0);
        for (CashFlow cashFlow : outflows) {
            sum = sum.add(cashFlow.getAmount());
        }
        return sum;
    }

    public BigDecimal getSpendingTillDate(String userEmail, Record record) {
        List<CashFlow> outflows = getCashFlowsTillGivenDate(userEmail, record.getDate(), record.getCategory());
        BigDecimal sum = new BigDecimal(0);
        for (CashFlow cashFlow : outflows) {
            sum = sum.add(cashFlow.getAmount());
        }
        return sum;
    }

    public BigDecimal getUserOutcome(String userEmail) {
        List<CashFlow> outflows = cashFlowRepository.queryAllCashFlowsByUser(userService.getUserByEmail(userEmail), false);
        BigDecimal outcome = new BigDecimal(0);
        for (CashFlow c : outflows) {
            outcome = outcome.add(c.getAmount());
        }
        return outcome;
    }

    public BigDecimal getUserIncome(String userEmail) {
        List<CashFlow> inflows = cashFlowRepository.queryAllCashFlowsByUser(userService.getUserByEmail(userEmail), true);
        BigDecimal income = new BigDecimal(0);
        for (CashFlow c : inflows) {
            income = income.add(c.getAmount());
        }
        return income;
    }

    private List<CashFlow> getCashFlowsForGivenCategoryAndMonth(String userEmail, LocalDate date, Category c) {
        return cashFlowRepository.queryFindByUserAndByCategoryAndByDate(
                userService.getUserByEmail(userEmail), c,
                date,
                LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth()));
    }

    private List<CashFlow> getCashFlowsTillGivenDate(String userEmail, LocalDate date, Category c) {
        return cashFlowRepository.queryFindByUserAndByCategoryAndByDate(
                userService.getUserByEmail(userEmail), c,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth()));
    }

    public List<CashFlow> getAllCashFlowsForUser(String userEmail) {
        return cashFlowRepository.findByUser(userService.getUserByEmail(userEmail));
    }

    public void persist(CashFlow cashFlow, String userEmail) {
        cashFlow.setUser(userService.getUserByEmail(userEmail));
        if (cashFlow.getCategory().getName().equals("Income")) {
            cashFlow.setCategory(null);
            cashFlow.setInflow(true);
        }
        cashFlowRepository.save(cashFlow);
    }
}
