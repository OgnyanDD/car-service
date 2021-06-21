package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.*;
import com.telerikacademy.car_service.models.dto.AddOrEditCustomerCarDto;
import com.telerikacademy.car_service.models.dto.CustomerCarDto;
import com.telerikacademy.car_service.models.dto.CustomerCarInfoDto;
import com.telerikacademy.car_service.repositories.CustomerCarsRepository;
import com.telerikacademy.car_service.services.contracts.CustomerService;
import com.telerikacademy.car_service.services.contracts.ManufactureYearService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerCarsServiceImplTest {

    private static final Customer CUSTOMER = new Customer();
    private static final Maker MAKER = new Maker();
    private static final Model MODEL = new Model();
    private static final Year YEAR = new Year();
    private static final CustomerCar CUSTOMER_CAR = new CustomerCar();
    private static final CustomerCarDto CUSTOMER_CAR_DTO = new CustomerCarDto();
    private static final AddOrEditCustomerCarDto ADD_OR_EDIT_CUSTOMER_CAR_DTO = new AddOrEditCustomerCarDto();


    @Mock
    private CustomerCarsRepository mockCustomerCarsRepository;

    @Mock
    private ManufactureYearService mockManufactureYearService;

    @Mock
    private CustomerService mockCustomerService;

    @InjectMocks
    private CustomerCarsServiceImpl customerCarsService;

    @Before
    public void setDefaultTestServices() {

        CUSTOMER.setId(1);
        CUSTOMER.setName("Ivan Ivanov");
        CUSTOMER.setPhoneNumber("+359878999555");
        CUSTOMER.setEmail("test@test.ff");
        CUSTOMER.setCustomerCars(new ArrayList<>());

        MAKER.setId(1);
        MAKER.setMake("BMW");
        MAKER.setCountry("Germany");

        MODEL.setId(1);
        MODEL.setModel("X5");
        MODEL.setMaker(MAKER);

        YEAR.setId(1);
        YEAR.setYear(1111);
        YEAR.setModel(MODEL);

        CUSTOMER_CAR.setId(1);
        CUSTOMER_CAR.setRegistration("C0000BT");
        CUSTOMER_CAR.setCar_vin("BG12345678910NHNF");
        CUSTOMER_CAR.setCustomer(CUSTOMER);
        CUSTOMER_CAR.setYear(YEAR);
        CUSTOMER_CAR.setDeleted(false);

        CUSTOMER_CAR_DTO.setCustomerId(CUSTOMER.getId());
        CUSTOMER_CAR_DTO.setCarId(CUSTOMER_CAR.getId());
        CUSTOMER_CAR_DTO.setRegistration(CUSTOMER_CAR.getRegistration());
        CUSTOMER_CAR_DTO.setCarVin(CUSTOMER_CAR.getCar_vin());
        CUSTOMER_CAR_DTO.setYear(YEAR.getId());
        CUSTOMER_CAR_DTO.setModel("X5");
        CUSTOMER_CAR_DTO.setMaker("BMW");

        ADD_OR_EDIT_CUSTOMER_CAR_DTO.setCustomerId(1);
        ADD_OR_EDIT_CUSTOMER_CAR_DTO.setCarId(1);
        ADD_OR_EDIT_CUSTOMER_CAR_DTO.setCarVinNumber("BG09876543210BGBG");
        ADD_OR_EDIT_CUSTOMER_CAR_DTO.setRegistration("PK5555PT");
        ADD_OR_EDIT_CUSTOMER_CAR_DTO.setYear(YEAR.getId());
    }

    @Test
    public void showAllCars_shouldReturnListWithAllCars() {

        when(mockCustomerCarsRepository.findAll()).thenReturn(Collections.singletonList(CUSTOMER_CAR));
        List<CustomerCarInfoDto> customerCarInfoDtoList = customerCarsService.showAllCars();
        Assert.assertEquals(1, customerCarInfoDtoList.size());
        verify(mockCustomerCarsRepository, Mockito.times(1)).findAll();
    }

    @Test (expected = ResponseStatusException.class)
    public void showAllCars_shouldThrowException_whenNoDataExist()  {

        when(mockCustomerCarsRepository.findAll()).thenThrow(ResponseStatusException.class);
        customerCarsService.showAllCars();
    }

    @Test
    public void showAllCustomerCars_shouldReturnListWithCarsByCustomerId() {

        when(mockCustomerCarsRepository.getAllCustomerCars()).thenReturn(Collections.singletonList(CUSTOMER_CAR));
        List<CustomerCarDto> customerCarInfoDtoList = customerCarsService.showAllCustomerCars(1);
        Assert.assertEquals(1, customerCarInfoDtoList.size());
        verify(mockCustomerCarsRepository, Mockito.times(1)).getAllCustomerCars();
    }

    @Test(expected = ResponseStatusException.class)
    public void showAllCustomerCars_shouldThrowException_whenNoDataExist() {

        customerCarsService.showAllCustomerCars(1);
    }

    @Test
    public void findByRegistration_shouldReturnCustomerCarByRegistration() {

        when(mockCustomerCarsRepository.findByRegistration("C0000BT")).thenReturn(CUSTOMER_CAR);
        CustomerCarDto expected = customerCarsService.findByRegistration("C0000BT");
        Assert.assertEquals(expected.getRegistration(), CUSTOMER_CAR.getRegistration());
    }

    @Test
    public void findCarById_shouldReturnCustomerCarById() {

        when(mockCustomerCarsRepository.findById(1)).thenReturn(java.util.Optional.of(CUSTOMER_CAR));
        CustomerCar expected = customerCarsService.findCarById(1);
        Assert.assertEquals(expected, CUSTOMER_CAR);
    }

    @Test(expected = ResponseStatusException.class)
    public void findCarById_shouldThrowException_whenCarNotExist() {

        customerCarsService.findCarById(2);
    }

    @Test
    public void customerCarDto_shouldReturnCustomerCar() {

        when(mockCustomerCarsRepository.findById(1)).thenReturn(java.util.Optional.of(CUSTOMER_CAR));
        CustomerCarDto expected = customerCarsService.customerCarDto(1);
        Assert.assertEquals(expected.getRegistration(), CUSTOMER_CAR.getRegistration());
    }

    @Test(expected = ResponseStatusException.class)
    public void customerCarDto_shouldThrowException_whenCarNotExist() {

        customerCarsService.customerCarDto(2);
    }

    @Test
    public void editCustomerCar_shouldEditCustomerCar() {

        when(mockCustomerCarsRepository.findById(1)).thenReturn(java.util.Optional.of(CUSTOMER_CAR));
        when(mockManufactureYearService.getYearById(1)).thenReturn(YEAR);
        customerCarsService.editCustomerCar(ADD_OR_EDIT_CUSTOMER_CAR_DTO);
        verify(mockCustomerCarsRepository, Mockito.times(1)).save(any(CustomerCar.class));
    }

    @Test(expected = ResponseStatusException.class)
    public void editCustomerCar_shouldThrowException_whenCarNotExist() {

        customerCarsService.editCustomerCar(ADD_OR_EDIT_CUSTOMER_CAR_DTO);
    }

    @Test
    public void deleteCustomerCar_shouldDeleteCustomerCarById() {

        when(mockCustomerCarsRepository.findById(1)).thenReturn(java.util.Optional.of(CUSTOMER_CAR));
        customerCarsService.deleteCustomerCar(1);
        Assert.assertEquals(true, CUSTOMER_CAR.getDeleted());
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteCustomerCar_shouldThrowException_whenCarNotExist() {

        customerCarsService.deleteCustomerCar(5);
    }

    @Test
    public void addNewCustomerCar_shouldAddNewCustomerCar() {

        when(mockManufactureYearService.getYearById(1)).thenReturn(YEAR);
        when(mockCustomerService.getCustomerById(1)).thenReturn(CUSTOMER);
        customerCarsService.addNewCustomerCar(ADD_OR_EDIT_CUSTOMER_CAR_DTO);
        verify(mockCustomerCarsRepository, Mockito.times(1)).save(any(CustomerCar.class));
    }

    @Test(expected = ResponseStatusException.class)
    public void addNewCustomerCar_shouldThrowException_whenManufactureYearNotExist() {

        customerCarsService.addNewCustomerCar(ADD_OR_EDIT_CUSTOMER_CAR_DTO);
    }
}