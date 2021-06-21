package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.Maker;
import com.telerikacademy.car_service.models.Model;
import com.telerikacademy.car_service.models.ServiceCategory;
import com.telerikacademy.car_service.models.Year;
import com.telerikacademy.car_service.models.dto.ManufactureYearDto;
import com.telerikacademy.car_service.repositories.ManufactureYearRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ManufactureYearServiceImplTest {

    private static final Year YEAR = new Year();
    private static final Model MODEL = new Model();
    private static final Maker MAKER = new Maker();

    @Mock
    private ManufactureYearRepository mockManufactureYearRepository;

    @InjectMocks
    private ManufactureYearServiceImpl manufactureYearService;

    @Before
    public void setDefaultTestServices() {

        MAKER.setId(1);
        MAKER.setMake("BMW");
        MAKER.setCountry("Germany");

        MODEL.setId(1);
        MODEL.setMaker(MAKER);
        MODEL.setModel("X5");

        YEAR.setId(1);
        YEAR.setYear(1111);
        YEAR.setModel(MODEL);
    }

    @Test
    public void getModelYearsByModelId_shouldReturnListWithWithModelYearsById() {

        when(mockManufactureYearRepository.getYearsByModelId(1)).thenReturn(Collections.singletonList(YEAR));
        List<ManufactureYearDto> manufactureYearDtoList = manufactureYearService.getModelYearsByModelId(1);
        Assert.assertEquals(1, manufactureYearDtoList.size());
        verify(mockManufactureYearRepository, Mockito.times(1)).getYearsByModelId(1);
    }

    @Test
    public void getYearById_shouldReturnYearById() {

        when(mockManufactureYearRepository.getYearById(1)).thenReturn(YEAR);
        Year expected = manufactureYearService.getYearById(1);
        Assert.assertEquals(expected, YEAR);
    }
}