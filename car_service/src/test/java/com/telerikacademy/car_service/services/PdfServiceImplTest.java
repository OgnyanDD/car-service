package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.*;
import com.telerikacademy.car_service.models.dto.CarVisitDto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PdfServiceImplTest {

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
    private static final CarVisitDto CAR_VISIT_DTO = new CarVisitDto();
    private static final String FULL_PATH = "/resources/reports/" + "invoice" + ".pdf";

    List<AutoServices> SERVICES_LIST = new ArrayList<>();

    @Mock
    private ServletContext context;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private PdfServiceImpl pdfService;


    @Before
    public void setUp() {


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

        CAR_VISIT_DTO.setId(CAR_VISIT_ID);
        CAR_VISIT_DTO.setCustomer(CUSTOMER.getName());
        CAR_VISIT_DTO.setDate(LocalDateTime.now());
        CAR_VISIT_DTO.setCustomerCar(CAR_MAKER.getMake());
        CAR_VISIT_DTO.setCarPlateNumber(CUSTOMER_CAR.getRegistration());
        CAR_VISIT_DTO.setPdfGenerated(false);


        when(context.getRealPath("/resources/reports")).thenReturn(FULL_PATH);

    }

    @Test
    public void createPdf_Should_Return_True() {

        boolean PdfGenerated = pdfService.createPdf(CAR_VISIT_DTO, SERVICES_LIST, context, request, response);
        Assert.assertTrue(PdfGenerated);
    }

}