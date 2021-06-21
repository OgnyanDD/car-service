package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.Maker;
import com.telerikacademy.car_service.models.Model;
import com.telerikacademy.car_service.repositories.CarModelRepository;
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
public class CarModelServiceImplTest {

    private static final Maker MAKER = new Maker();
    private static final Model MODEL = new Model();

    @Mock
    private CarModelRepository mockCarModelRepository;

    @InjectMocks
    private CarModelServiceImpl carModelService;

    @Before
    public void setDefaultTestServices() {

        MAKER.setId(1);
        MAKER.setMake("BMW");
        MAKER.setCountry("Germany");

        MODEL.setId(1);
        MODEL.setModel("X5");
        MODEL.setMaker(MAKER);
    }

    @Test
    public void listModels_shouldReturnListWithModels() {

        when(mockCarModelRepository.findAll()).thenReturn(Collections.singletonList(MODEL));
        List<Model> makerList = carModelService.listModels();
        Assert.assertEquals(1, makerList.size());
        verify(mockCarModelRepository, Mockito.times(1)).findAll();
    }

    @Test (expected = ResponseStatusException.class)
    public void listModels_shouldThrowException_whenNoDataExist() {

        carModelService.listModels();
    }

    @Test
    public void listModelsByMakerId_shouldReturnListWithModelsByMakerId() {

        when(mockCarModelRepository.getModelsByMakerId(1)).thenReturn(Collections.singletonList(MODEL));
        List<Model> modelYearsByModelIdList = carModelService.listModelsByMakerId(1);
        Assert.assertEquals(1, modelYearsByModelIdList.size());
        verify(mockCarModelRepository, Mockito.times(1)).getModelsByMakerId(1);
    }
}