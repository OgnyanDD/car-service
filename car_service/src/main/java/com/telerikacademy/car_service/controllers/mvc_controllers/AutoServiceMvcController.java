package com.telerikacademy.car_service.controllers.mvc_controllers;

import com.telerikacademy.car_service.models.dto.autoservice.AddAutoServiceDto;
import com.telerikacademy.car_service.services.contracts.AutoServicesService;
import com.telerikacademy.car_service.services.contracts.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car-service/admin/services")
public class AutoServiceMvcController {

    private ServiceCategoryService categoryService;

    private AutoServicesService autoService;

    @Autowired
    public AutoServiceMvcController(ServiceCategoryService categoryService,
                                    AutoServicesService autoService) {
        this.categoryService = categoryService;
        this.autoService = autoService;
    }

    @GetMapping("/new-service")
    public String addNewService(Model model){
        model.addAttribute("categories",categoryService.showAllCategories());
        model.addAttribute("view","auto-services/add-service");
        return "base-layout";
    }

    @PostMapping("/new-service")
    public String addService(@ModelAttribute AddAutoServiceDto autoServices){
        autoService.addNewAutoService(autoServices);
        return "redirect:/car-service/home";
    }

    @GetMapping("{id}/edit")
    public String editAutoService(Model model,@PathVariable Integer id){

        model.addAttribute("service",autoService.getAutoServiceById(id));
        model.addAttribute("categories",categoryService.showAllCategories());
        model.addAttribute("view","auto-services/edit-service");
        return "base-layout";
    }

    @PostMapping("/edit")
    public String editAutoService(@ModelAttribute AddAutoServiceDto serviceDto){
        autoService.editAutoService(serviceDto);
        return "redirect:/car-service/home";
    }
}
