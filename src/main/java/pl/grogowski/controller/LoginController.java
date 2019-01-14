package pl.grogowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.grogowski.model.User;
import pl.grogowski.repository.UserRepository;
import pl.grogowski.service.UserService;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@SessionAttributes("count")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String getLoginData(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if (userService.authenticate(email, password)) {
            session.setAttribute("userEmail", email);
            return "success";
        }
        return "login";
    }

    @RequestMapping(path = "register", method = RequestMethod.GET)
    public String getRegisterData(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String register(@Valid User user, BindingResult result, @RequestParam String repeated) {
        if (result.hasErrors()||!user.getPassword().equals(repeated)) {
            return "register";
        }
        if (!userService.userExists(user.getEmail())) {
            userService.registerUser(user);
            return "redirect: /";
        }
        return "register";
    }

}
