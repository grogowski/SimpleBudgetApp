package pl.grogowski.controller;

import org.json.JSONObject;
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
import java.util.HashMap;
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
        for (LocalDate d : dates) {
            availableMonths.put(d, BudgetUtil.convertDate(d));
        }
        model.addAttribute("availableMonths", availableMonths);
        model.addAttribute("records", records);
        model.addAttribute("budgeted", totalBudgeted);
        model.addAttribute("balance", balance);
        model.addAttribute("category", new Category());
        model.addAttribute("displayedMonth", requestedDate);
        return "/user/main";
    }

    @RequestMapping(value = "/main/edit", method = RequestMethod.POST)
    @ResponseBody
    public String updateMainView(@RequestParam String changed, @RequestParam String value, @RequestParam String id, @SessionAttribute String userEmail) {
        if (changed.equals("category")) {
            categoryService.editCategory(id, value);
            return new JSONObject().put("categoryName", value).toString();
        } else if (changed.equals("amount")) {
            recordService.editRecord(id, value);
            Record record = recordService.getRecord(id, userEmail);
            String totalBudgeted = recordService.getTotalBudgetedForGivenMonth(userEmail, record.getDate()).toString();
            String available = record.getAvailable().toString();
            String budgeted = record.getBudgetedAmount().toString();
            Map<String, String> map = new HashMap<>();
            map.put("budgeted", budgeted);
            map.put("available", available);
            map.put("totalBudgeted", totalBudgeted);
            return new JSONObject(map).toString();
        }
        return new JSONObject().toString();
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

    @RequestMapping(path = "/editCashFlow", method = RequestMethod.POST)
    public String editCashFlows(@RequestParam String id,
                                @RequestParam String categoryId,
                                @RequestParam String date,
                                @RequestParam String in,
                                @RequestParam String out,
                                @SessionAttribute String userEmail) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id);
        cashFlow.setDate(LocalDate.parse(date));
        if(in.equals("0")){
            cashFlow.setCategory(categoryService.getCategoryById(categoryId));
            cashFlow.setAmount(BigDecimal.valueOf(Double.parseDouble(out)));
        } else {
            cashFlow.setAmount(BigDecimal.valueOf(Double.parseDouble(in)));
        }
        cashFlowService.update(cashFlow);
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
