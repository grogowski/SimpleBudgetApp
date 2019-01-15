package pl.grogowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import pl.grogowski.model.Record;
import pl.grogowski.service.CashFlowService;
import pl.grogowski.service.CategoryService;
import pl.grogowski.service.RecordService;
import pl.grogowski.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    RecordService recordService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CashFlowService cashFlowService;
    @Autowired
    UserService userService;

    @RequestMapping("/main")
    public String showMain(Model model, @SessionAttribute String userEmail) {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        List<Record> records = recordService.getRecordsForGivenMonth(userEmail, date);
        BigDecimal totalBudgeted = recordService.getTotalBudgetedForGivenMonth(userEmail, date);
        BigDecimal balance = userService.getUserBalance(userEmail);
        model.addAttribute("records", records);
        model.addAttribute("budgeted", totalBudgeted);
        model.addAttribute("balance", balance);
        return "/user/main";
    }

}
