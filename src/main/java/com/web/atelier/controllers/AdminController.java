package com.web.atelier.controllers;

import com.web.atelier.models.Pillow;
import com.web.atelier.models.Review;
import com.web.atelier.repo.PillowRepository;
import com.web.atelier.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private PillowRepository pillowRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/admin")
    public String goToAdminPage(Model model) {
        return "admin";
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
    public String goToManageNewsPage(Model model) {
        return "manage_news";
    }

    @GetMapping("/admin/manage_user")
    public String goToManageUserPage(Model model) {
        return "manage_user";
    }

    @PostMapping("/add_pillow")
    public String addNewPillow(@RequestParam Integer width, @RequestParam Integer height, @RequestParam Float price, Model model) {
        Pillow pillow = new Pillow(width, height, price);
        pillowRepository.save(pillow);
        return "redirect:admin";
    }

    @PostMapping("/delete_pillow")
    public String deletePillow(@RequestParam Integer id, Model model) {
        pillowRepository.deleteById(id);
        return "redirect:admin";
    }
}
