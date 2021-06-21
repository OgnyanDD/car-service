package com.telerikacademy.car_service.services.contracts;

import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.dto.CarVisitDto;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PdfService {

    boolean createPdf(CarVisitDto carVisitDto,
                      List<AutoServices> services,
                      ServletContext context,
                      HttpServletRequest request,
                      HttpServletResponse response);

    void fileDownload(String fullPath, HttpServletResponse response);
}