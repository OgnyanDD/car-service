package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.Maker;
import com.telerikacademy.car_service.repositories.CarMakerRepository;
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
public class CarMakerServiceImplTest {

    private static final Maker MAKER = new Maker();

    @Mock
    private CarMakerRepository mockCarMakerRepository;

    @InjectMocks
    private CarMakerServiceImpl carMakerService;

    @Before
    public void setDefaultTestServices() {

        MAKER.setId(1);
        MAKER.setMake("BMW");
        MAKER.setCountry("Germany");
    }

    @Test
    public void makerList_shouldReturnListWithMakers() {

        when(mockCarMakerRepository.findAll()).thenReturn(Collections.singletonList(MAKER));
        List<Maker> makerList = carMakerService.makerList();
        Assert.assertEquals(1, makerList.size());
        verify(mockCarMakerRepository, Mockito.times(1)).findAll();
    }

    @Test(expected = ResponseStatusException.class)
    public void makerList_shouldThrowException_whenNoDataExist() {

        carMakerService.makerList();
    }
}