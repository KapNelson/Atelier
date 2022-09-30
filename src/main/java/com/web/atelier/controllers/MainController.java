package com.web.atelier.controllers;

import com.web.atelier.models.News;
import com.web.atelier.models.Pillow;
import com.web.atelier.models.Review;
import com.web.atelier.models.User;
import com.web.atelier.repo.NewsRepository;
import com.web.atelier.repo.PillowRepository;
import com.web.atelier.repo.ReviewRepository;
import com.web.atelier.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private NewsRepository newsRepository;

    private void setUsernameAndLogstatus(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = new String("Вхід");
        String logstatus = new String("#login");

        if(auth.isAuthenticated()) {
            if(!auth.getName().equals("anonymousUser")) {
                username = "Привіт, " + auth.getName();
                if(auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
                    logstatus = "#profile_admin";
                } else {
                    logstatus = "#profile";
                }
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("logstatus", logstatus);
    }

    @GetMapping("/")
    public String goToMainPage(Model model) {
        setUsernameAndLogstatus(model);

        Iterable<Review> reviews = reviewRepository.findTop3ByVerifiedOrderByIdDesc(true);
        model.addAttribute("reviews", reviews);

        Iterable<News> news = newsRepository.findTop2ByOrderByPublicationDateDesc();
        model.addAttribute("news", news);

        return "index";
    }

    @PostMapping("/addreview")
    public String addReview(@RequestParam String name, @RequestParam String phone, String text, Model model) {
        Review review = new Review(name, phone, text);
        reviewRepository.save(review);
        return "redirect:";
    }

    @GetMapping("/pillow")
    public String openPillowPage(Model model) {
        setUsernameAndLogstatus(model);

        Iterable<Pillow> pillows = pillowRepository.findAll();
        model.addAttribute("pillows", pillows);
        return "pillow";
    }

    @GetMapping("/repair")
    public String openRepairPage(Model model) {
        setUsernameAndLogstatus(model);

        return "repair";
    }

    @GetMapping("/linens")
    public String openLinensPage(Model model) {
        setUsernameAndLogstatus(model);

        return "linens";
    }

    @GetMapping("/accessories")
    public String openAccessoriesPage(Model model) {
        setUsernameAndLogstatus(model);

        return "accessories";
    }

    @GetMapping("/news")
    public String openNewsPage(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        setUsernameAndLogstatus(model);

        Page<News> news;

        news = newsRepository.findAllByOrderByPublicationDateDesc(pageable);
        List<Integer> newsNumber = new ArrayList<>();
        for (int i = 0; i < news.getTotalPages(); i++) {
            newsNumber.add(i);
        }

        model.addAttribute("news", news);
        model.addAttribute("newsNumber", newsNumber);

        return "news";
    }

    @GetMapping("/review")
    public String openReviewPage(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        setUsernameAndLogstatus(model);

        Page<Review> page;

        page = reviewRepository.findByVerifiedOrderByIdDesc(pageable, true);
        List<Integer> pageNumber = new ArrayList<>();
        for (int i = 0; i < page.getTotalPages(); i++) {
            pageNumber.add(i);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageNumber", pageNumber);

        return "review";
    }

    @PostMapping("/reg")
    public ResponseEntity<HttpStatus> regNewUser(@RequestParam String login_new, @RequestParam String password_new, @RequestParam String repassword_new, Model model) {
        Optional<User> userOpt = userRepository.findById(login_new);

        if (!userOpt.isPresent()) {
            User user = new User(login_new, PASSWORD_ENCODER.encode(password_new), "ROLE_USER", true);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
