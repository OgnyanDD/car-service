package com.telerikacademy.car_service.controllers.mvc_controllers;

import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.CarVisit;
import com.telerikacademy.car_service.models.dto.CarVisitDto;
import com.telerikacademy.car_service.services.contracts.CarsVisitsService;
import com.telerikacademy.car_service.services.contracts.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/car-service/")
public class PdfMvcController {


    private CarsVisitsService carsVisitsService;

    private ServletContext context;

    private PdfService pdfService;

    @Autowired
    public PdfMvcController(CarsVisitsService carsVisitsService,
                            ServletContext context,
                            PdfService pdfService) {

        this.carsVisitsService = carsVisitsService;
        this.context = context;
        this.pdfService = pdfService;
    }
    @RequestMapping(value = {"/car-visit/pdf/{id}", "/admin/car-visit/pdf/{id}"}, method = RequestMethod.GET)
    public void createPdf(@PathVariable Integer id,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        CarVisitDto carVisitDto = carsVisitsService.getCarVisitDtoById(id);
        List<AutoServices> servicesList = carVisitDto.getServices();
        boolean isPdfGen = pdfService.createPdf(carVisitDto, servicesList, context, request, response);

        if (isPdfGen) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "invoice" + ".pdf");
            pdfService.fileDownload(fullPath, response);
            carsVisitsService.editPdfGenerated(carVisitDto);
        }
    }
}
