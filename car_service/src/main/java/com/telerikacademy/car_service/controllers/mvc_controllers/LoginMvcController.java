package com.telerikacademy.car_service.controllers.mvc_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginMvcController {

    @GetMapping("/car-service/login")
    public String login(Model model) {

        model.addAttribute("view", "customer/login");

        return "base-layout";
    }

    @GetMapping("/car-service/access-denied")
    public String denied(Model model) {

        model.addAttribute("view", "index_home/denied");

        return "base-layout";
    }
}
