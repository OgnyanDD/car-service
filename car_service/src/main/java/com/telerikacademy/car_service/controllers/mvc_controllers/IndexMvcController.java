package com.telerikacademy.car_service.controllers.mvc_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car-service/")
public class IndexMvcController {

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("view", "index_home/index");

        return "base-layout";
    }

    @GetMapping("team-7")
    public String about(Model model) {
        model.addAttribute("view", "index_home/team-7");

        return "base-layout";
    }
}
