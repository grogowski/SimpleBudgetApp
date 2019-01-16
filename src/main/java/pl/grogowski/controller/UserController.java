package pl.grogowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.grogowski.model.CashFlow;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.service.CashFlowService;
import pl.grogowski.service.CategoryService;
import pl.grogowski.service.RecordService;
import pl.grogowski.service.UserService;
import pl.grogowski.util.BudgetUtil;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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


    @RequestMapping(path = "/main/{month}", method = RequestMethod.GET)
    public String showMain(Model model, @SessionAttribute String userEmail, @PathVariable String month) {
        LocalDate presentDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        LocalDate futureMonth = presentDate.plusMonths(1);
        String[] parts = month.split("-");
        LocalDate requestedDate;
        if (month.equals("default")) {
            requestedDate = presentDate;
        } else {
            requestedDate = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), 1);
        }
        List<Record> records = recordService.getRecordsForGivenMonth(userEmail, requestedDate);
        BigDecimal totalBudgeted = recordService.getTotalBudgetedForGivenMonth(userEmail, requestedDate);
        BigDecimal balance = userService.getUserBalance(userEmail);
        List<LocalDate> dates = recordService.getExistingRecordsDates(userEmail);
        if (!dates.contains(futureMonth)) {
            dates.add(futureMonth);
        }
        Map<LocalDate, String> availableMonths = new TreeMap<>();
        availableMonths.put(LocalDate.of(1900, 01, 01), "select month");
        for (LocalDate d : dates) {
            availableMonths.put(d, BudgetUtil.convertDate(d));
        }
        model.addAttribute("availableMonths", availableMonths);
        model.addAttribute("records", records);
        model.addAttribute("budgeted", totalBudgeted);
        model.addAttribute("balance", balance);
        return "/user/main";
    }

    @RequestMapping(value = "/main/{month}", method = RequestMethod.POST)
    public String updated(@RequestParam Map<String, String> parameters, @PathVariable String month) {
        for (String param : parameters.keySet()) {
            if (param.endsWith("changed")) {
                if (param.startsWith("category")) {
                    String categoryId = param.split("-")[1];
                    String categoryNewName = parameters.get(param);
                    categoryService.editCategory(categoryId, categoryNewName);
                } else {
                    String recordId = param.split("-")[1];
                    String budgetedAmount = parameters.get(param);
                    recordService.editRecord(recordId, budgetedAmount);
                }
            }
        }
        return "redirect: /user/main/"+month;
    }

    @RequestMapping(path = "/cashflows", method = RequestMethod.GET)
    public String showCashFlows(Model model, @SessionAttribute String userEmail) {
        List<CashFlow> cashFlows = cashFlowService.getAllCashFlowsForUser(userEmail);
        List<Category> categories = categoryService.getCategoriesForUser(userEmail);
        Category income = new Category();
        income.setName("Income");
        categories.add(income);
        model.addAttribute("categories", categories);
        model.addAttribute("cashFlows", cashFlows);
        model.addAttribute("cashFlow", new CashFlow());
        return "user/cashflows";
    }

    @RequestMapping(path = "/cashflows", method = RequestMethod.POST)
    public String addCashFlow(@Valid CashFlow cashFlow, BindingResult result, @SessionAttribute String userEmail) {
        if (!result.hasErrors()) {
            cashFlowService.persist(cashFlow, userEmail);
        }
        return "redirect: /user/cashflows";
    }

}
