package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.models.AutoServices;
import com.telerikacademy.car_service.models.CarVisit;
import com.telerikacademy.car_service.models.CustomerCar;
import com.telerikacademy.car_service.models.dto.AddCarVisitDto;
import com.telerikacademy.car_service.models.dto.CarVisitDto;
import com.telerikacademy.car_service.models.dto.CarVisitsInfoDto;
import com.telerikacademy.car_service.models.dto.EditCarVisitDto;
import com.telerikacademy.car_service.repositories.CarsVisitsRepository;
import com.telerikacademy.car_service.services.contracts.AutoServicesService;
import com.telerikacademy.car_service.services.contracts.CarsVisitsService;
import com.telerikacademy.car_service.services.contracts.CustomersCarsService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class CarVisitsServiceImpl implements CarsVisitsService {

    private CarsVisitsRepository carsVisitsRepository;

    private CustomersCarsService carsService;

    private AutoServicesService autoServices;

    @Autowired
    public CarVisitsServiceImpl(
            CarsVisitsRepository carsVisitsRepository,
            CustomersCarsService carsService,
            AutoServicesService autoServices) {

        this.carsVisitsRepository = carsVisitsRepository;
        this.carsService = carsService;
        this.autoServices = autoServices;

    }

    @Override
    public List<CarVisitsInfoDto> getAllCarsVisits() {

        List<CarVisit> carVisitList = carsVisitsRepository.findAll();

        if (carVisitList.isEmpty()) {

            throw new HibernateException("No Cars visits registered yet!");

        } else {

            List<CarVisitsInfoDto> carVisitsInfoDtoList = new ArrayList<>();

            for (CarVisit c : carVisitList) {
                CarVisitsInfoDto carVisitsInfoDto = mapCarVisitToInfoDto(c);
                carVisitsInfoDtoList.add(carVisitsInfoDto);
            }
            return carVisitsInfoDtoList;
        }
    }

    @Override
    public List<CarVisitsInfoDto> singleCarVisits(Integer carId) {

        List<CarVisitsInfoDto> infoDtoList = new ArrayList<>();

        List<CarVisit> carVisits = carsVisitsRepository.findAllByCustomerCar(carId);

        if (carVisits.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR
                    , String.format("Customer car with id %d has no visits yet!", carId));
        } else {
            for (CarVisit c : carVisits) {
                CarVisitsInfoDto carVisitsInfoDto = mapCarVisitToInfoDto(c);
                infoDtoList.add(carVisitsInfoDto);
            }
            return infoDtoList;
        }

    }

    @Override
    public CarVisitDto getCarVisitDtoById(int id) {

        CarVisit carsVisit = carsVisitsRepository.findCarVisitById(id);

        if (carsVisit == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No visit with id %d found!", id));
        } else {

            return mapToCarVisitDto(carsVisit);
        }

    }

    @Override
    public void addCarVisit(AddCarVisitDto addCarVisitDto) {


        Integer carId = addCarVisitDto.getCarId();
        CustomerCar car = carsService.findCarById(carId);

        List<Integer> indices = addCarVisitDto.getIndices();

        CarVisit visit = new CarVisit();
        List<AutoServices> visitServices = new ArrayList<>();

        for (Integer i : indices) {
            AutoServices services = autoServices.findAutoServiceById(i);
            visitServices.add(services);
        }

        double visitPrice = carVisitPrice(visitServices);
        visit.setDate(LocalDateTime.now());
        visit.setAutoServices(visitServices);
        visit.setCustomerCar(car);
        visit.setPrice(visitPrice);
        visit.setPdfGenerated(false);

        carsVisitsRepository.save(visit);
    }

    @Override
    public void editCarVisits(EditCarVisitDto editCarVisitDto) {

        Integer id = editCarVisitDto.getCarVisitId();
        CarVisit carVisitToBeEdit = carsVisitsRepository.findCarVisitById(id);

        if (carVisitToBeEdit == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No visit with id %d found!", id));
        } else {

            List<AutoServices> listServices = new ArrayList<>();
            List<Integer> indices = editCarVisitDto.getIndices();
            for (Integer i : indices) {
                AutoServices service = autoServices.findAutoServiceById(i);
                listServices.add(service);
            }
            double editPrice = carVisitPrice(listServices);
            carVisitToBeEdit.setPrice(editPrice);
            carsVisitsRepository.save(carVisitToBeEdit);

        }
    }

    @Override
    public void editPdfGenerated(CarVisitDto carVisitDto) {

        int id = carVisitDto.getId();

        CarVisit carsVisit = carsVisitsRepository.findCarVisitById(id);
        if (carsVisit == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("No visit with id %d found!", id));
        } else {

            carsVisit.setPdfGenerated(true);
            carsVisitsRepository.save(carsVisit);
        }

    }


    private static CarVisitsInfoDto mapCarVisitToInfoDto(CarVisit visit) {

        CarVisitsInfoDto carVisitsInfoDto = new CarVisitsInfoDto();

        carVisitsInfoDto.setId(visit.getId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        carVisitsInfoDto.setDate(visit.getDate().format(dateTimeFormatter));
        carVisitsInfoDto.setCarRegistration(visit.getCustomerCar().getRegistration());
        carVisitsInfoDto.setPrice(visit.getPrice());
        carVisitsInfoDto.setPdfGenerated(visit.getPdfGenerated());

        return carVisitsInfoDto;

    }


    private double carVisitPrice(List<AutoServices> services) {

        double price = 0;
        for (AutoServices a : services) {

            price += a.getPrice();
        }
        return price;
    }


    private CarVisitDto mapToCarVisitDto(CarVisit carsVisit) {

        CarVisitDto carVisitDto = new CarVisitDto();
        carVisitDto.setId(carsVisit.getId());
        carVisitDto.setDate(carsVisit.getDate());
        carVisitDto.setCustomer(carsVisit.getCustomerCar().getCustomer().getName());
        carVisitDto.setCustomerCar(carsVisit.getCustomerCar().getYear().getModel().getMaker().getMake());
        carVisitDto.setCarPlateNumber(carsVisit.getCustomerCar().getRegistration());
        carVisitDto.setServices(carsVisit.getAutoServices());
        carVisitDto.setPrice(carsVisit.getPrice());
        carVisitDto.setPdfGenerated(carsVisit.getPdfGenerated());

        return carVisitDto;
    }
}
