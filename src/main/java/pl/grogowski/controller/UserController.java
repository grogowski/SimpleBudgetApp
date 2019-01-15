package pl.grogowski.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import pl.grogowski.model.Category;
import pl.grogowski.model.Record;
import pl.grogowski.model.User;
import pl.grogowski.repository.UserRepository;
import pl.grogowski.service.CategoryService;
import pl.grogowski.service.RecordService;

import java.time.LocalDate;
import java.util.List;
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    RecordService recordService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/main")
    public String showMain(Model model, @SessionAttribute String userEmail) {
        List<Record> records = recordService.getRecordsForUserGivenDate(userEmail, LocalDate.now());
        return "/user/main";
    }

}
