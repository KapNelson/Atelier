package com.web.atelier.controllers;

import com.web.atelier.models.User;
import com.web.atelier.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Главная страница");
        return "index";
    }

    @PostMapping("/reg")
    public ResponseEntity<HttpStatus> regNewUser(@RequestParam String login_new, @RequestParam String password_new, @RequestParam String repassword_new, Model model) {
        Optional<User> userOpt = userRepository.findById(login_new);

        if(!userOpt.isPresent()) {
            if (password_new.equals(repassword_new)) {
                User user = new User(login_new, password_new);
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> authorizationUser(@RequestParam String login, @RequestParam String password, Model model) {
        Optional<User> userOpt = userRepository.findById(login);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(user.getPasword().equals(password)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
