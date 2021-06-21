package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.*;
import com.telerikacademy.car_service.models.dto.AddCarVisitDto;
import com.telerikacademy.car_service.models.dto.CarVisitDto;
import com.telerikacademy.car_service.models.dto.CarVisitsInfoDto;
import com.telerikacademy.car_service.models.dto.EditCarVisitDto;
import com.telerikacademy.car_service.repositories.CarsVisitsRepository;
import com.telerikacademy.car_service.repositories.CustomerCarsRepository;
import com.telerikacademy.car_service.services.contracts.CustomersCarsService;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarVisitsServiceImplTest {

    private static Integer CUSTOMER_ID = 1;
    private static final Integer CAR_ID = 1;
    private static final Integer SERVICE1_ID = 0;
    private static final Integer SERVICE2_ID = 1;
    private static final Integer MAKER_ID = 1;
    private static final Integer YEAR_ID = 1;
    private static final Integer MODEL_ID = 1;
    private static final Integer CATEGORY_ID = 1;
    private static final Integer CAR_VISIT_ID = 1;
    private static final double PRICE = 0.01;
    private static final Customer CUSTOMER = new Customer();
    private static final CustomerCar CUSTOMER_CAR = new CustomerCar();
    private static final Year CAR_YEAR = new Year();
    private static final Model CAR_MODEL = new Model();
    private static final Maker CAR_MAKER = new Maker();
    private static final ServiceCategory CATEGORY = new ServiceCategory();
    private static final AutoServices SERVICE1 = new AutoServices();
    private static final AutoServices SERVICE2 = new AutoServices();
    private static final CarVisit CAR_VISIT = new CarVisit();
    private static final AddCarVisitDto ADD_CAR_VISIT_DTO = new AddCarVisitDto();
    private static final CarVisitsInfoDto CAR_VISIT_INFO_DTO = new CarVisitsInfoDto();
    private static final CarVisitDto CAR_VISIT_DTO = new CarVisitDto();
    private static final EditCarVisitDto EDIT_CAR_VISIT_DTO = new EditCarVisitDto();

    @Mock
    private CarsVisitsRepository mockCarVisitRepository;

    @Mock
    private AutoServicesServiceImpl autoServicesService;

    @Mock
    private CustomersCarsService carsService;

    @InjectMocks
    private CarVisitsServiceImpl carVisitsService;


    @Before
    public void setUp() {

        List<AutoServices> SERVICES_LIST = new ArrayList<>();
        List<CarVisit> VISIT_LIST = new ArrayList<>();

        CAR_MAKER.setId(MAKER_ID);
        CAR_MAKER.setMake("LADA");

        CAR_MODEL.setId(MODEL_ID);
        CAR_MODEL.setMaker(CAR_MAKER);
        CAR_MODEL.setModel("NIVA");

        CAR_YEAR.setId(YEAR_ID);
        CAR_YEAR.setModel(CAR_MODEL);
        CAR_YEAR.setYear(2005);

        CUSTOMER.setId(CUSTOMER_ID);
        CUSTOMER.setName("Paps");
        CUSTOMER.setEmail("'papscs@abv.bg");

        CUSTOMER_CAR.setId(CAR_ID);
        CUSTOMER_CAR.setCustomer(CUSTOMER);
        CUSTOMER_CAR.setRegistration("СВ0001МТ");
        CUSTOMER_CAR.setCar_vin("ABCDEFGHJKLMNOPQR");
        CUSTOMER_CAR.setYear(CAR_YEAR);


        SERVICE1.setId(SERVICE1_ID);
        SERVICE1.setServiceCategory(CATEGORY);
        SERVICE1.setPrice(PRICE);
        SERVICE1.setService("SomeTestService");

        SERVICE2.setId(SERVICE2_ID);
        SERVICE2.setServiceCategory(CATEGORY);
        SERVICE2.setPrice(PRICE);
        SERVICE2.setService("SecondTestService");

        SERVICES_LIST.add(SERVICE1);
        SERVICES_LIST.add(SERVICE2);

        CATEGORY.setId(CATEGORY_ID);
        CATEGORY.setCategory("TestCategory");
        CATEGORY.setAutoServices(SERVICES_LIST);

        CAR_VISIT.setAutoServices(SERVICES_LIST);
        CAR_VISIT.setId(CAR_VISIT_ID);
        CAR_VISIT.setCustomerCar(CUSTOMER_CAR);
        CAR_VISIT.setPrice(PRICE + PRICE);
        CAR_VISIT.setDate(LocalDateTime.now());
        CAR_VISIT.setPdfGenerated(false);

        VISIT_LIST.add(CAR_VISIT);

        CAR_VISIT_INFO_DTO.setId(CAR_VISIT.getId());
        CAR_VISIT_INFO_DTO.setCarRegistration(CAR_VISIT.getCustomerCar().getRegistration());
        CAR_VISIT_INFO_DTO.setPrice(CAR_VISIT.getPrice());
        CAR_VISIT_INFO_DTO.setPdfGenerated(CAR_VISIT.getPdfGenerated());
        CAR_VISIT_DTO.setId(CAR_VISIT.getId());


        when(mockCarVisitRepository.findAll()).thenReturn(VISIT_LIST);

        when(mockCarVisitRepository.findAllByCustomerCar(CAR_ID)).thenReturn(VISIT_LIST);

        when(mockCarVisitRepository.findCarVisitById(CAR_VISIT_ID)).thenReturn(CAR_VISIT);

        when(carsService.findCarById(CAR_ID)).thenReturn((CUSTOMER_CAR));

        when(carsService.findCarById(2)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        when(autoServicesService.findAutoServiceById(SERVICE1_ID)).thenReturn(SERVICE1);

        when(autoServicesService.findAutoServiceById(SERVICE2_ID)).thenReturn(SERVICE2);

    }


    @Test
    public void getAllCarsVisits_Should_Return_One_CAR_VISIT() {
        List<CarVisitsInfoDto> list = carVisitsService.getAllCarsVisits();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void singleCarVisits_Should_Return_One_Visit() {
        List<CarVisitsInfoDto> list = carVisitsService.singleCarVisits(CAR_ID);
        Assert.assertEquals(1, list.size());

    }

    @Test(expected = ResponseStatusException.class)
    public void singleCarVisits_Should_Throw_Response_Status_Ex() {
        List<CarVisitsInfoDto> list = carVisitsService.singleCarVisits(2);
        Assert.assertEquals(1, list.size());

    }

    @Test
    public void getCarVisitDtoById() {

        CarVisitDto visitDto = carVisitsService.getCarVisitDtoById(CAR_VISIT_ID);
        Assert.assertEquals(CAR_VISIT_ID, visitDto.getId());
    }

    @Test(expected = ResponseStatusException.class)
    public void getCarVisitDtoById_Should_Throw_Response_Status_Ex() {

        CarVisitDto visitDto = carVisitsService.getCarVisitDtoById(2);
        Assert.assertEquals(CAR_VISIT_ID, visitDto.getId());
    }

    @Test
    public void addCarVisit() {
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        integers.add(1);
        ADD_CAR_VISIT_DTO.setIndices(integers);
        ADD_CAR_VISIT_DTO.setCarId(CAR_ID);
        carVisitsService.addCarVisit(ADD_CAR_VISIT_DTO);
        verify(mockCarVisitRepository,Mockito.times(1)).save(any());

    }

    @Test(expected = ResponseStatusException.class)
    public void addCarVisit_Should_Throw_Response_Status_Ex() {
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        integers.add(1);
        ADD_CAR_VISIT_DTO.setIndices(integers);
        ADD_CAR_VISIT_DTO.setCarId(2);

        carVisitsService.addCarVisit(ADD_CAR_VISIT_DTO);
    }

    @Test
    public void editCarVisits_Should_invoke_Save_Method() {
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        EDIT_CAR_VISIT_DTO.setCarVisitId(CAR_VISIT_ID);
        EDIT_CAR_VISIT_DTO.setIndices(integers);
        carVisitsService.editCarVisits(EDIT_CAR_VISIT_DTO);
        verify(mockCarVisitRepository,Mockito.times(1)).save(any());
    }

    @Test(expected = ResponseStatusException.class)
    public void editCarVisits_Should_Throw_Response_Status_Ex() {
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        EDIT_CAR_VISIT_DTO.setCarVisitId(2);
        EDIT_CAR_VISIT_DTO.setIndices(integers);
        carVisitsService.editCarVisits(EDIT_CAR_VISIT_DTO);
        verify(mockCarVisitRepository,Mockito.times(1)).save(any());
    }

    @Test
    public void editPdfGenerated() {
        carVisitsService.editPdfGenerated(CAR_VISIT_DTO);
        CarVisitDto visitDto = carVisitsService.getCarVisitDtoById(CAR_VISIT_ID);
        Assert.assertEquals(true,visitDto.getPdfGenerated());
    }

    @Test(expected = ResponseStatusException.class)
    public void editPdfGenerated_Should_Throw_Response_Status_Ex() {
        CarVisitDto visitDto = new CarVisitDto();
        visitDto.setId(2);
        carVisitsService.editPdfGenerated(visitDto);
    }
}