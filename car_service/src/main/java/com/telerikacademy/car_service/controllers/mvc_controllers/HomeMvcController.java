package com.telerikacademy.car_service.controllers.mvc_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car-service/")
public class HomeMvcController {

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("view", "index_home/home");

        return "base-layout";
    }
}
