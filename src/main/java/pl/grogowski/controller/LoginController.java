package pl.grogowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.grogowski.model.User;
import pl.grogowski.service.CategoryService;
import pl.grogowski.service.UserService;
import pl.grogowski.util.BudgetUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginData() {
        return "/login/login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (userService.authenticate(email, password)) {
            LocalDate presentMonth = BudgetUtil.getPresentMonth();
            session.setAttribute("userEmail", email);
            session.setAttribute("presentMonth", presentMonth);
            return "redirect: /user/main/"+ presentMonth;
        }
        model.addAttribute("error", "Incorrect login data");
        return "/login/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterData(Model model) {
        model.addAttribute("user", new User());
        return "/login/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid User user, BindingResult result, @RequestParam String repeated, Model model, HttpSession session) {
        boolean errorsPresent = false;
        if (user.getPassword().length()<6||user.getPassword().length()>24) {
            model.addAttribute("errorLength", "Password must be between 6 and 24 characters");
            errorsPresent = true;
        }
        if (!user.getPassword().equals(repeated)) {
            model.addAttribute("errorMatch", "Password and repeated password do not match");
            errorsPresent = true;
        }
        if (userService.userExists(user.getEmail())) {
            model.addAttribute("errorMail", "Account with this email already exists");
            errorsPresent = true;
        }
        if(errorsPresent||result.hasErrors()) {
            return "/login/register";
        } else {
            userService.registerUser(user);
            categoryService.createCategoriesForNewUser(user);
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("presentMonth", BudgetUtil.getPresentMonth());
            return "redirect: /user/welcome";
        }
    }

}
