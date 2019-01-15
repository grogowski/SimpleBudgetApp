package pl.grogowski.controller;

import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    public String showMain() {
        return "main";
    }
}
