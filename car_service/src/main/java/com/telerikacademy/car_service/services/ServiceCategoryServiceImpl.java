package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.ServiceCategory;
import com.telerikacademy.car_service.repositories.ServiceCategoryRepository;
import com.telerikacademy.car_service.services.contracts.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private ServiceCategoryRepository categoryRepository;

    @Autowired
    public ServiceCategoryServiceImpl(ServiceCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ServiceCategory> showAllCategories() {

        List<ServiceCategory> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No Service categories found!"
            );

        } else {

            return categoryList;
        }
    }
}
