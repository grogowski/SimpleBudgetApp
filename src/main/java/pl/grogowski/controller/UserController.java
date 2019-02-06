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
import java.util.*;

/**
 * Handles all views for signed in user.
 */
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

    /**
     * Returns a budget view for a month specified in path variable.
     */
    @RequestMapping(path = "/main/{month}", method = RequestMethod.GET)
    public String showMain(Model model, @SessionAttribute String userEmail, @PathVariable String month) {
        LocalDate requestedMonth = BudgetUtil.StringToDate(month);
        List<Record> records = recordService.getRecordsForGivenMonth(userEmail, requestedMonth);
        List<LocalDate> dates = recordService.getExistingRecordsDates(userEmail);
        if (!dates.contains(BudgetUtil.getNextMonth())) {
            dates.add(BudgetUtil.getNextMonth());
        }
        Map<LocalDate, String> availableMonths = new TreeMap<>();
        for (LocalDate d : dates) {
            availableMonths.put(d, BudgetUtil.DateToString(d));
        }
        model.addAttribute("availableMonths", availableMonths);
        model.addAttribute("records", records);
        model.addAttribute("budgeted", recordService.getTotalBudgetedForGivenMonth(userEmail, requestedMonth));
        model.addAttribute("balance", userService.getUserBalance(userEmail));
        model.addAttribute("category", new Category());
        model.addAttribute("displayedMonth", requestedMonth);
        return "/user/main";
    }

    /**
     * Updates category name or budgeted amount in db, returns JSON with data to update the view.
     */
    @RequestMapping(value = "/main/edit", method = RequestMethod.POST)
    @ResponseBody
    public String updateMainView(@RequestParam String changed, @RequestParam String value, @RequestParam String id, @SessionAttribute String userEmail) {
        if (changed.equals("category")) {
            if (!categoryService.userCategoriesContainCategoryWithGivenName(value, userEmail)) {
                categoryService.editCategory(id, value);
            }
            return new JSONObject().put("categoryName", categoryService.getCategoryById(id).getName()).toString();
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

    /**
     * Returns transactions view.
     */
    @RequestMapping(path = "/cashflows", method = RequestMethod.GET)
    public String showCashFlows(Model model, @SessionAttribute String userEmail) {
        List<Category> inCategories = categoryService.getCategoriesForUser(userEmail, true);
        List<Category> outCategories = categoryService.getCategoriesForUser(userEmail, false);
        model.addAttribute("inCategories", inCategories);
        model.addAttribute("outCategories", outCategories);
        model.addAttribute("cashFlows", cashFlowService.getAllCashFlowsForUser(userEmail));
        model.addAttribute("cashFlow", new CashFlow());
        return "user/cashflows";
    }

    /**
     * Saves a new cashflow in db, returns updated transactions view.
     */
    @RequestMapping(path = "/addCashFlow", method = RequestMethod.POST)
    public String addInflow(@Valid CashFlow cashFlow, BindingResult result, @SessionAttribute String userEmail) {
        if (!result.hasErrors()) {
            cashFlowService.persist(cashFlow, userEmail);
        }
        return "redirect: /user/cashflows";
    }

    /**
     * Updates cashflow in db, returns updated transactions view.
     */
    @RequestMapping(path = "/editCashFlow", method = RequestMethod.POST)
    public String editCashFlows(@RequestParam String id,
                                @RequestParam String categoryId,
                                @RequestParam String date,
                                @RequestParam String amount,
                                @SessionAttribute String userEmail) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id);
        cashFlow.setDate(LocalDate.parse(date));
        cashFlow.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
        cashFlow.setCategory(categoryService.getCategoryById(categoryId));
        cashFlowService.update(cashFlow);
        return "redirect: /user/cashflows";
    }

    /**
     * Validates and saves new category, redirects to main view.
     */
    @RequestMapping(path = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result,
                              @SessionAttribute String userEmail,
                              @RequestParam String displayedMonth) {
        List<Category> categories = categoryService.getCategoriesForUser(userEmail);
        if (!result.hasErrors()
                && !categoryService.userCategoriesContainCategoryWithGivenName(category.getName(), userEmail)) {
            categoryService.addCategory(userEmail, category);
        }
        return "redirect: /user/main/" + displayedMonth;
    }

    /**
     * Logout method. After logout redirects to login view.
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect: /";
    }

    /**
     * Deletes a cashflow and redirects to updated transactions view.
     */
    @RequestMapping(path = "/deleteCashFlow/{cashFlowId}", method = RequestMethod.GET)
    public String deleteCashFlow(@PathVariable String cashFlowId) {
        cashFlowService.delete(cashFlowId);
        return "redirect: /user/cashflows";
    }

    /**
     * Returns charts view.
     */
    @RequestMapping(path = "/charts", method = RequestMethod.GET)
    public String showCharts(Model model, @SessionAttribute String userEmail) {
        List<LocalDate> dates = recordService.getExistingRecordsDates(userEmail);
        Map<LocalDate, String> availableMonths = new TreeMap<>();
        for (LocalDate d : dates) {
            availableMonths.put(d, BudgetUtil.DateToString(d));
        }
        model.addAttribute("availableMonths", availableMonths);
        model.addAttribute("displayedMonth", BudgetUtil.getPresentMonth());
        return "user/charts";
    }

    /**
     * Returns JSON with data to draw a chart.
     */
    @RequestMapping(value = "/charts/draw/{month}", method = RequestMethod.POST)
    @ResponseBody
    public String drawChart(@PathVariable String month, @SessionAttribute String userEmail) {
        List<String> categoriesNames = recordService.getCategoriesNamesForUser(userEmail, BudgetUtil.StringToDate(month));
        List<BigDecimal> budgeted = recordService.getBudgetedForGivenMonth(userEmail, BudgetUtil.StringToDate(month));
        List<BigDecimal> spending = recordService.getSpendingsForGivenMonth(userEmail, BudgetUtil.StringToDate(month));
        Map<String, List> map = new HashMap<>();
        map.put("categories", categoriesNames);
        map.put("budgeted", budgeted);
        map.put("spending", spending);
        return new JSONObject(map).toString();
    }

    /**
     * Returns categories view.
     */
    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public String showCategories(Model model, @SessionAttribute String userEmail) {
        model.addAttribute("inCategories", categoryService.getCategoriesForUser(userEmail, true));
        model.addAttribute("outCategories", categoryService.getCategoriesForUser(userEmail, false));
        return "user/categories";
    }

}
