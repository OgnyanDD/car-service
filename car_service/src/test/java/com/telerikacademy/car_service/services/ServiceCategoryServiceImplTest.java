package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.ServiceCategory;
import com.telerikacademy.car_service.repositories.ServiceCategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ServiceCategoryServiceImplTest {

    private static final ServiceCategory SERVICE_CATEGORY = new ServiceCategory();

    @Mock
    private ServiceCategoryRepository mockServiceCategoryRepository;

    @InjectMocks
    private ServiceCategoryServiceImpl serviceCategoryService;

    @Before
    public void setDefaultTestServices() {
        SERVICE_CATEGORY.setId(1);
        SERVICE_CATEGORY.setCategory("Test category");
    }

    @Test
    public void showAllCategories_shouldReturnListWithAllServiceCategory() {

        when(mockServiceCategoryRepository.findAll()).thenReturn(Collections.singletonList(SERVICE_CATEGORY));
        List<ServiceCategory> serviceCategories = serviceCategoryService.showAllCategories();
        Assert.assertEquals(1, serviceCategories.size());
        verify(mockServiceCategoryRepository, Mockito.times(1)).findAll();
    }

    @Test (expected = ResponseStatusException.class)
    public void showAllCategories_shouldThrowException_whenNoDataExist(){

        serviceCategoryService.showAllCategories();
    }
}