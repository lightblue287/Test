package com.fruitshop.controller;

import com.fruitshop.model.UserDTO;
import com.fruitshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userSvc;

    @GetMapping("/users")
    public String getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            Model model) {

        List<UserDTO> users = userSvc.getUsersFromApi(page, perPage);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("perPage", perPage);
        return "users";
    }
}
