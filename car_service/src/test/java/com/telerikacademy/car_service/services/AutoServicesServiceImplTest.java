package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.CarVisit;
import com.telerikacademy.car_service.models.ServiceCategory;
import com.telerikacademy.car_service.models.dto.autoservice.AddAutoServiceDto;
import com.telerikacademy.car_service.models.dto.autoservice.AutoServiceDto;
import com.telerikacademy.car_service.repositories.AutoServicesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AutoServicesServiceImplTest {

    private static final Integer ID = 1;
    private static final ServiceCategory CATEGORY = new ServiceCategory();
    private static final String SERVICE_CATEGORY = "Computer_Diagnostics";
    private static final String SERVICE = "Diagnostics";
    private static final String NEW_SERVICE = "ESP_Diagnostics";
    private static final double PRICE = 20;
    private static final Boolean IS_DELETED = false;
    private static final Set<CarVisit> CAR_VISITS = new HashSet<>();

    private static final AutoServices AUTO_SERVICES = new AutoServices();
    private static final AutoServiceDto AUTO_SERVICE_DTO = new AutoServiceDto();
    private static final AddAutoServiceDto ADD_AUTO_SERVICE_DTO = new AddAutoServiceDto();


    @Mock
    private AutoServicesRepository mockRepository;

    @InjectMocks
    AutoServicesServiceImpl autoService;


    @Before
    public void setUp() {

        List<AutoServices> AUTO_SERVICES_LIST = new ArrayList<>();

        AUTO_SERVICES.setId(ID);
        AUTO_SERVICES.setServiceCategory(CATEGORY);
        AUTO_SERVICES.setService(SERVICE);
        AUTO_SERVICES.setPrice(PRICE);
        AUTO_SERVICES.setDeleted(IS_DELETED);
        AUTO_SERVICES.setCarVisits(CAR_VISITS);
        AUTO_SERVICES_LIST.add(AUTO_SERVICES);

        AUTO_SERVICE_DTO.setId(AUTO_SERVICES.getId());
        AUTO_SERVICE_DTO.setCategory(SERVICE_CATEGORY);
        AUTO_SERVICE_DTO.setService(AUTO_SERVICES.getService());
        AUTO_SERVICE_DTO.setPrice(String.format("%.2f", AUTO_SERVICES.getPrice()));
        AUTO_SERVICE_DTO.setIsDeleted(IS_DELETED);

        ADD_AUTO_SERVICE_DTO.setId(ID);
        ADD_AUTO_SERVICE_DTO.setCategory(CATEGORY);
        ADD_AUTO_SERVICE_DTO.setPrice(PRICE);
        ADD_AUTO_SERVICE_DTO.setService(SERVICE);

        CATEGORY.setId(1);
        CATEGORY.setCategory(SERVICE_CATEGORY);

        when(mockRepository.getAllServices()).thenReturn(AUTO_SERVICES_LIST);
        when(mockRepository.getAllServices().stream()
                .filter(service -> service.getServiceCategory().getId().equals(1))
                .filter(service -> !service.getDeleted())
                .collect(Collectors.toList())).thenReturn(AUTO_SERVICES_LIST);

        when(mockRepository.findAll().stream().filter(autoservice -> autoservice.getService().equals(SERVICE))
                .collect(Collectors.toList()))
                .thenReturn(AUTO_SERVICES_LIST);
        when(mockRepository.getById(ID)).thenReturn(AUTO_SERVICES);

    }

    @Test
    public void showAllAutoServices_ShouldReturn_One_AutoService() {
        List<AutoServiceDto> services = autoService.showAllAutoServices();
        Assert.assertEquals(1, services.size());
        verify(mockRepository, Mockito.times(1)).getAllServices();
    }

    @Test
    public void showAutoServiceByCategory_ShouldReturn_One_AutoService() {

        List<AutoServiceDto> services = autoService.showAutoServiceByCategory(1);
        Assert.assertEquals(1, services.size());
        verify(mockRepository, Mockito.times(1)).getAllServices();
    }

    @Test(expected = ResponseStatusException.class)
    public void showAutoServiceByCategory_Throw_New_Response_Status_Exception() {
        List<AutoServiceDto> services = autoService.showAutoServiceByCategory(2);
        Assert.assertEquals(2, services.size());
        verify(mockRepository, Mockito.times(1)).getAllServices();
    }

    @Test
    public void getAutoServiceById_Should_Return_One_AutoServiceDto() {
        AutoServiceDto serviceDto = autoService.getAutoServiceById(ID);
        Assert.assertEquals(ID, serviceDto.getId());
        verify(mockRepository, Mockito.times(1)).getById(ID);
    }

    @Test(expected = ResponseStatusException.class)
    public void getAutoServiceById_Should_Throw_Response_Status_Ex() {
        AutoServiceDto serviceDto = autoService.getAutoServiceById(2);
        Assert.assertNotSame(2, serviceDto.getId());
        verify(mockRepository, Mockito.times(1)).getById(ID);
    }

    @Test
    public void addNewAutoService_Return_NewService() {
        AddAutoServiceDto addService = new AddAutoServiceDto();
        addService.setCategory(CATEGORY);
        addService.setService("SomeNewService");
        addService.setPrice(PRICE);

        AutoServices newService = new AutoServices();
        newService.setServiceCategory(addService.getCategory());
        newService.setService(addService.getService());
        newService.setPrice(addService.getPrice());

        autoService.addNewAutoService(addService);
    }


    @Test(expected = ResponseStatusException.class)
    public void addNewAutoService_Should_Throw_Response_Status_Ex() {

        autoService.addNewAutoService(ADD_AUTO_SERVICE_DTO);
    }

    @Test
    public void editAutoService_Should_Set_Service_To_NEW_SERVICE() {
        AddAutoServiceDto addService = new AddAutoServiceDto();
        addService.setId(ID);
        addService.setCategory(CATEGORY);
        addService.setService(NEW_SERVICE);
        addService.setPrice(PRICE);
        autoService.editAutoService(addService);
        Assert.assertEquals(NEW_SERVICE,AUTO_SERVICES.getService());
    }

    @Test(expected = ResponseStatusException.class)
    public void editAutoService_Should_ResponseStatus_Ex() {
        AddAutoServiceDto addService = new AddAutoServiceDto();
        addService.setId(2);
        addService.setCategory(CATEGORY);
        addService.setService(NEW_SERVICE);
        addService.setPrice(PRICE);
        autoService.editAutoService(addService);
    }


    @Test
    public void findAutoServiceById() {
        AutoServices services = autoService.findAutoServiceById(ID);
        Assert.assertEquals(services,AUTO_SERVICES);
    }

    @Test
    public void deleteAutoService_Should_Set_isDeleted_True() {
        autoService.deleteAutoService(ID);
        Assert.assertEquals(true,AUTO_SERVICES.getDeleted());

    }

    @Test(expected = ResponseStatusException.class)
    public void deleteAutoService_Should_Throw_Response_Status_Ex() {
        autoService.deleteAutoService(2);
    }
}