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
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.time.LocalDate;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PillowRepository pillowRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/admin")
    public String goToAdminPage(Model model) {
        return "admin";
    }

    @PostMapping("/confirm_review")
    public String confirmReview(@RequestParam Long id, Model model) {
        Optional<Review> review = reviewRepository.findById(id);
        review.get().setVerified(!review.get().getVerified());
        reviewRepository.save(review.get());
        return "redirect:admin/manage_review";
    }

    @GetMapping("/admin/manage_pillow")
    public String goToManagePillowPage(Model model) {
        Iterable<Pillow> pillows = pillowRepository.findAll();
        model.addAttribute("pillows", pillows);

        return "manage_pillow";
    }

    @GetMapping("/admin/manage_review")
    public String goToManageReviewPage(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Review> page;

        page = reviewRepository.findAllByOrderByIdDesc(pageable);
        List<Integer> pageNumber = new ArrayList<>();
        for (int i = 0; i < page.getTotalPages(); i++) {
            pageNumber.add(i);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageNumber", pageNumber);

        return "manage_review";
    }

    @GetMapping("/admin/manage_news")
    public String goToManageNewsPage(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<News> news;

        news = newsRepository.findAllByOrderByPublicationDateDesc(pageable);
        List<Integer> newsNumber = new ArrayList<>();
        for (int i = 0; i < news.getTotalPages(); i++) {
            newsNumber.add(i);
        }

        model.addAttribute("news", news);
        model.addAttribute("newsNumber", newsNumber);

        return "manage_news";
    }

    @GetMapping("/admin/manage_user")
    public String goToManageUserPage(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "manage_user";
    }

    @PostMapping("/add_pillow")
    public String addNewPillow(@RequestParam Integer width, @RequestParam Integer height, @RequestParam Float price, Model model) {
        Pillow pillow = new Pillow(width, height, price);
        pillowRepository.save(pillow);
        return "redirect:admin/manage_pillow";
    }

    @PostMapping("/delete_pillow")
    public String deletePillow(@RequestParam Integer id, Model model) {
        pillowRepository.deleteById(id);
        return "redirect:admin/manage_pillow";
    }

    @PostMapping("/ban_user")
    public String banUser(@RequestParam String login, Model model) {
        Optional<User> user = userRepository.findById(login);
        user.get().setEnabled(!user.get().getEnabled());
        userRepository.save(user.get());
        return "redirect:admin/manage_user";
    }

    @PostMapping("/add_news")
    public String addNewNews(@RequestParam String title, @RequestParam String text, @RequestParam MultipartFile file, Model model) {
        String fileType = Objects.requireNonNull(StringUtils.split(file.getContentType(), "/"))[1];

        News news = new News(title, text, LocalDate.now(), fileType);
        Long newsId = newsRepository.save(news).getId();

        File imageFile = new File(String.format("src/main/resources/static/img/news/%s.%s", newsId, fileType));
        try {
            file.transferTo(imageFile);
            imageFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:admin/manage_news";
    }

    @PostMapping("/delete_news")
    public String deleteNews(@RequestParam Long id, Model model) {
        newsRepository.deleteById(id);
        return "redirect:admin/manage_news";
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20848820);
        return multipartResolver;
    }
}
