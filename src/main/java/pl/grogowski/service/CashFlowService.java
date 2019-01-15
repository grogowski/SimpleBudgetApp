package pl.grogowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grogowski.model.CashFlow;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.repository.CashFlowRepository;
import pl.grogowski.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CashFlowService {
    @Autowired
    CashFlowRepository cashFlowRepository;
    @Autowired
    UserRepository userRepository;

    public BigDecimal getCashOutflowForGivenRecord(String userEmail, Record record) {
        List<CashFlow> outflows = getCashFlowForGivenCategoryAndDate(userEmail, record.getMonth(), record.getYear(), record.getCategory());
        BigDecimal sum = new BigDecimal(0);
        for (CashFlow cashFlow:outflows) {
            sum = sum.add(cashFlow.getAmount());
        }
        return sum;
    }

    private List<CashFlow> getCashFlowForGivenCategoryAndDate(String userEmail, Integer month, Integer year, Category c) {
        return cashFlowRepository.queryFindByUserAndByCategoryAndByDate(
                userRepository.findByEmail(userEmail), c,
                LocalDate.of(year, month, 1),
                LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth()));
    }
}
