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
    public String goToAdminPage(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Iterable<Pillow> pillows = pillowRepository.findAll();
        model.addAttribute("pillows", pillows);

        Page<Review> page;

        page = reviewRepository.findAllByOrderByIdDesc(pageable);
        List<Integer> pageNumber = new ArrayList<>();
        for(int i = 0; i<page.getTotalPages(); i++){
            pageNumber.add(i);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageNumber", pageNumber);
        return "admin";
    }

    @PostMapping("/admin")
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
