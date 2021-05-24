package com.web.atelier.controllers;

import com.web.atelier.models.Pillow;
import com.web.atelier.repo.PillowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private PillowRepository pillowRepository;

    @GetMapping("/admin")
    public String goToAdminPage(Model model) {
        Iterable<Pillow> pillows = pillowRepository.findAll();
        model.addAttribute("pillows", pillows);
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
