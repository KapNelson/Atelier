package com.web.atelier.controllers;

import com.web.atelier.models.Pillow;
import com.web.atelier.models.Review;
import com.web.atelier.models.User;
import com.web.atelier.repo.PillowRepository;
import com.web.atelier.repo.ReviewRepository;
import com.web.atelier.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(4);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PillowRepository pillowRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String goToMainPage(Model model) {
        Iterable<Review> reviews = reviewRepository.findTop3ByVerifiedOrderByIdDesc(true);
        model.addAttribute("reviews", reviews);
        return "index";
    }

    @PostMapping("/addreview")
    public String authorizationUser(@RequestParam String name, @RequestParam String phone, String text, Model model) {
        Review review = new Review(name, phone, text);
        reviewRepository.save(review);
        return "redirect:";
    }

    @GetMapping("/pillow")
    public String openPillowPrice(Model model) {
        Iterable<Pillow> pillows = pillowRepository.findAll();
        model.addAttribute("pillows", pillows);
        return "pillow";
    }

    @PostMapping("/reg")
    public ResponseEntity<HttpStatus> regNewUser(@RequestParam String login_new, @RequestParam String password_new, @RequestParam String repassword_new, Model model) {
        Optional<User> userOpt = userRepository.findById(login_new);

        if (!userOpt.isPresent()) {
            if (password_new.equals(repassword_new)) {
                User user = new User(login_new, PASSWORD_ENCODER.encode(password_new), "ROLE_USER", true);
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
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
