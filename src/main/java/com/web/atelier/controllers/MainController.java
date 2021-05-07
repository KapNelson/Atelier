package com.web.atelier.controllers;

import com.web.atelier.models.User;
import com.web.atelier.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Главная страница");
        return "index";
    }

    @PostMapping("/")
    public String addUser(@RequestParam String login, @RequestParam String password, Model model) {
        User user = new User(login, password);
        userRepository.save(user);
        return "index";
    }

    /*@GetMapping("/")
    public String test(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }*/


}
