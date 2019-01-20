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

import javax.servlet.http.HttpSession;
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
        model.addAttribute("category", new Category());
        model.addAttribute("month", month);
        return "/user/main";
    }

    @RequestMapping(value = "/main/{month}", method = RequestMethod.POST)
    public String updated(@RequestParam Map<String, String> parameters, @PathVariable String month) {
        for (String param : parameters.keySet()) {
            String parts[] = param.split("-");
            if (parts[0].equals("category")) {
                String categoryId = param.split("-")[1];
                String categoryNewName = parameters.get(param);
                if (!categoryNewName.isEmpty()) {
                    categoryService.editCategory(categoryId, categoryNewName);
                }
            } else if (parts[0].equals("amount")) {
                String recordId = param.split("-")[1];
                String budgetedAmount = parameters.get(param);
                recordService.editRecord(recordId, budgetedAmount);
            }
        }
        return "redirect: /user/main/" + month;
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

    @RequestMapping(path = "/addCashFlow", method = RequestMethod.POST)
    public String addCashFlow(@Valid CashFlow cashFlow, BindingResult result, @SessionAttribute String userEmail) {
        if (!result.hasErrors()) {
            cashFlowService.persist(cashFlow, userEmail);
        } else if (result.getAllErrors().size() == 1 && cashFlow.getCategory().getName().equals("Income")) {
            cashFlowService.persist(cashFlow, userEmail);
        }
        return "redirect: /user/cashflows";
    }

    @RequestMapping(path = "/editCashFlows", method = RequestMethod.POST)
    public String editCashFlows(@RequestParam Map<String, String> parameters, @SessionAttribute String userEmail) {
        for (String param : parameters.keySet()) {
            String parts[] = param.split("-");
            CashFlow cashFlow = cashFlowService.getCashFlowById(parts[1]);
            if (parts[0].equals("category")) {
                cashFlow.setCategory(categoryService.getCategoryById(parameters.get(param)));
            } else if (parts[0].equals("date")) {
                cashFlow.setDate(LocalDate.parse(parameters.get(param)));
            } else if (parts[0].equals("in")) {
                cashFlow.setAmount(BigDecimal.valueOf(Double.parseDouble(parameters.get(param))));
            } else if (parts[0].equals("out")) {
                cashFlow.setAmount(BigDecimal.valueOf(Double.parseDouble(parameters.get(param))));
            }
            cashFlowService.update(cashFlow);
        }
        return "redirect: /user/cashflows";
    }

    @RequestMapping(path = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result,
                              @SessionAttribute String userEmail,
                              @RequestParam String month) {
        if (!result.hasErrors()) {
            categoryService.addCategory(userEmail, category);
        }
        return "redirect: /user/main/" + month;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect: /";
    }

    @RequestMapping(path = "/deleteCashFlow/{cashFlowId}", method = RequestMethod.GET)
    public String deleteCashFlow(@PathVariable String cashFlowId) {
        cashFlowService.delete(cashFlowId);
        return "redirect: /user/cashflows";
    }


}
