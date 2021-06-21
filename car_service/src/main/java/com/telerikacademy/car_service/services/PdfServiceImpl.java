package com.telerikacademy.car_service.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.dto.CarVisitDto;
import com.telerikacademy.car_service.services.contracts.PdfService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public boolean createPdf(CarVisitDto carVisitDto, List<AutoServices> services, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        try {
            Document document = new Document(PageSize.A4, 15, 15, 45, 30);

            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean existPath = new File(filePath).exists();

            if (!existPath) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "invoice" + ".pdf"));
            document.open();

            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(BaseColor.ORANGE);

            Font mainFont = FontFactory.getFont("Arial", 15, BaseColor.BLACK);
            Font titleFont = FontFactory.getFont("Arial", 25, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Car Visit Details", titleFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);

            document.add(paragraph);
            document.add(lineSeparator);

            Paragraph visitNumberParagraph = new Paragraph("Visit No. # " + (carVisitDto.getId()), mainFont);
            visitNumberParagraph.setAlignment(Element.ALIGN_LEFT);
            visitNumberParagraph.setIndentationLeft(50);
            visitNumberParagraph.setIndentationRight(50);
            visitNumberParagraph.setSpacingAfter(10);

            Paragraph dateParagraph = new Paragraph("Date: " + (carVisitDto.getDate()), mainFont);
            dateParagraph.setAlignment(Element.ALIGN_LEFT);
            dateParagraph.setIndentationLeft(50);
            dateParagraph.setIndentationRight(50);
            dateParagraph.setSpacingAfter(10);

            document.add(visitNumberParagraph);
            document.add(dateParagraph);
            document.add(new Chunk(lineSeparator));

            Paragraph customerParagraph = new Paragraph("Customer: " + (carVisitDto.getCustomer()), mainFont);
            customerParagraph.setAlignment(Element.ALIGN_LEFT);
            customerParagraph.setIndentationLeft(50);
            customerParagraph.setIndentationRight(50);
            customerParagraph.setSpacingAfter(10);

            Paragraph carParagraph = new Paragraph("Vehicle: " + (carVisitDto.getCustomerCar()), mainFont);
            carParagraph.setAlignment(Element.ALIGN_LEFT);
            carParagraph.setIndentationLeft(50);
            carParagraph.setIndentationRight(50);
            carParagraph.setSpacingAfter(10);

            Paragraph registrationNumberParagraph = new Paragraph("Registration number: " + (carVisitDto.getCarPlateNumber()), mainFont);
            registrationNumberParagraph.setAlignment(Element.ALIGN_LEFT);
            registrationNumberParagraph.setIndentationLeft(50);
            registrationNumberParagraph.setIndentationRight(50);
            registrationNumberParagraph.setSpacingAfter(10);

            document.add(customerParagraph);
            document.add(carParagraph);
            document.add(registrationNumberParagraph);
            document.add(new Chunk(lineSeparator));

            Paragraph carServicesParagraph = new Paragraph("AUTO SERVICES", mainFont);
            carServicesParagraph.setAlignment(Element.ALIGN_CENTER);
            carServicesParagraph.setIndentationLeft(50);
            carServicesParagraph.setIndentationRight(50);
            carServicesParagraph.setSpacingAfter(10);

            document.add(carServicesParagraph);

            Font tableBody = FontFactory.getFont("Arial", 15, BaseColor.WHITE);

            PdfPTable serviceTable = new PdfPTable(3);
            serviceTable.setWidthPercentage(100);
            serviceTable.setSpacingBefore(10);
            serviceTable.setSpacingAfter(10);

            for (AutoServices s : services) {

                PdfPCell category = new PdfPCell(new Paragraph(s.getServiceCategory()
                        .getCategory(), tableBody));

                category.setBorderColor(BaseColor.BLACK);
                category.setPaddingLeft(10);
                category.setHorizontalAlignment(Element.ALIGN_LEFT);
                category.setVerticalAlignment(Element.ALIGN_LEFT);
                category.setBackgroundColor(BaseColor.GRAY);
                category.setExtraParagraphSpace(5);
                serviceTable.addCell(category);


                PdfPCell service = new PdfPCell(new Paragraph(s.getService(), tableBody));

                service.setBorderColor(BaseColor.BLACK);
                service.setPaddingLeft(10);
                service.setHorizontalAlignment(Element.ALIGN_LEFT);
                service.setVerticalAlignment(Element.ALIGN_LEFT);
                service.setBackgroundColor(BaseColor.GRAY);
                service.setExtraParagraphSpace(5);
                serviceTable.addCell(service);

                PdfPCell price = new PdfPCell(new Paragraph(String.valueOf(s.getPrice()), tableBody));
                price.setBorderColor(BaseColor.BLACK);
                price.setPaddingLeft(10);
                price.setHorizontalAlignment(Element.ALIGN_RIGHT);
                price.setVerticalAlignment(Element.ALIGN_RIGHT);
                price.setBackgroundColor(BaseColor.GRAY);
                price.setExtraParagraphSpace(5);
                serviceTable.addCell(price);

            }

            Paragraph paragraphPrice = new Paragraph("Total price : " + (carVisitDto.getPrice()), mainFont);
            paragraphPrice.setAlignment(Element.ALIGN_RIGHT);
            paragraphPrice.setIndentationLeft(50);
            paragraphPrice.setIndentationRight(50);
            paragraphPrice.setSpacingAfter(10);


            document.add(serviceTable);
            document.add(paragraphPrice);
            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void fileDownload(String fullPath, HttpServletResponse response) {
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                response.setContentType("application/pdf");
                response.setHeader("content-disposition", "attachment; filename=" + "invoice.pdf");
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {

                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
