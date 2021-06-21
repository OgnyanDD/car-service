package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.CustomerCar;
import com.telerikacademy.car_service.models.Year;
import com.telerikacademy.car_service.models.dto.*;
import com.telerikacademy.car_service.repositories.CustomerCarsRepository;
import com.telerikacademy.car_service.services.contracts.CustomerService;
import com.telerikacademy.car_service.services.contracts.CustomersCarsService;
import com.telerikacademy.car_service.services.contracts.ManufactureYearService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerCarsServiceImpl implements CustomersCarsService {

    private CustomerCarsRepository carsRepository;

    private CustomerService customerService;

    private ManufactureYearService manufactureYearService;

    @Autowired
    public CustomerCarsServiceImpl(CustomerCarsRepository carsRepository, CustomerService customerService, ManufactureYearService manufactureYearService) {
        this.carsRepository = carsRepository;
        this.customerService = customerService;
        this.manufactureYearService = manufactureYearService;
    }

    @Override
    public List<CustomerCarInfoDto> showAllCars() {
        try {
            return customerCarInfoDtoList(carsRepository.findAll());

        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        }
    }

    @Override
    public List<CustomerCarDto> showAllCustomerCars(Integer customerId) {

        List<CustomerCar> customerCars = carsRepository.getAllCustomerCars().stream()
                .filter(car -> car.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());

        if (customerCars.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.
                    format("Customer whit id %d have no cars registered!", customerId));
        } else {
            List<CustomerCarDto> carDtoList = new ArrayList<>();

            for (CustomerCar c : customerCars) {
                CustomerCarDto carDto = mapCustomerCarToCustomerCarDto(c);
                carDtoList.add(carDto);
            }
            return carDtoList;
        }
    }

    @Override
    public CustomerCarDto findByRegistration(String registration) {

        try {
            CustomerCar car = carsRepository.findByRegistration(registration);
            return mapCustomerCarToCustomerCarDto(car);
        } catch (HibernateException he) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }


    @Override
    public CustomerCar findCarById(Integer carId) {
        return carsRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, String.format("Ooops car with id %d not found!", carId)));
    }

    @Override
    public CustomerCarDto customerCarDto(Integer carId) {

        CustomerCar car = findCarById(carId);

        if(car== null){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, String.format("Ooops car with id %d not found!", carId));
        }else {
            return mapCustomerCarToCustomerCarDto(car);

        }
    }

    @Override
    public void editCustomerCar(AddOrEditCustomerCarDto editCustomerCarDto) {

        CustomerCar carToEdit = carsRepository.findById(editCustomerCarDto.getCarId())
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, ("Ooops this cant be edit! Car doesn't exist in our records!")));

        carToEdit.setRegistration(editCustomerCarDto.getRegistration().toUpperCase());
        carToEdit.setCar_vin(editCustomerCarDto.getCarVinNumber().toUpperCase());
        Year year = manufactureYearService.getYearById(editCustomerCarDto.getYear());
        carToEdit.setYear(year);
        carsRepository.save(carToEdit);
    }

    @Override
    public void deleteCustomerCar(Integer carId) {


        CustomerCar carToDelete = findCarById(carId);
        if (carToDelete == null) {
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, String.format("Car whit id %d not found!", carId));
        } else {
            carToDelete.setDeleted(true);
            carsRepository.save(carToDelete);
        }


    }

    @Override
    public void addNewCustomerCar(AddOrEditCustomerCarDto addCustomerCarDto) {

        CustomerCar car = new CustomerCar();
        Integer yearId = addCustomerCarDto.getYear();

        Year year = manufactureYearService.getYearById(yearId);

        if (year == null) {
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, String.format("Year whit id: %d not found", yearId));
        } else {
            car.setYear(year);
            Integer customerId = addCustomerCarDto.getCustomerId();
            Customer customer = customerService.getCustomerById(customerId);
            car.setCustomer(customer);
            car.setCar_vin(addCustomerCarDto.getCarVinNumber().toUpperCase());
            car.setRegistration(addCustomerCarDto.getRegistration().toUpperCase());
            car.setDeleted(false);
            customer.getCustomerCars().add(car);

            carsRepository.save(car);
        }
    }

    private List<CustomerCarInfoDto> customerCarInfoDtoList(List<CustomerCar> cars) {
        List<CustomerCarInfoDto> allCustomersCars = new ArrayList<>();
        for (CustomerCar car : cars) {
            CustomerCarInfoDto carInfoDto = mapCustomerCarToCarInfoDto(car);
            allCustomersCars.add(carInfoDto);
        }
        return allCustomersCars;
    }

    private CustomerCarInfoDto mapCustomerCarToCarInfoDto(CustomerCar car) {
        CustomerCarInfoDto carInfoDto = new CustomerCarInfoDto();
        carInfoDto.setMaker(car.getYear().getModel().getMaker().getMake());
        carInfoDto.setModel(car.getYear().getModel().getModel());
        carInfoDto.setRegistration(car.getRegistration());
        carInfoDto.setCustomerName(car.getCustomer().getName());
        carInfoDto.setEmail(car.getCustomer().getEmail());
        return carInfoDto;
    }

    private CustomerCarDto mapCustomerCarToCustomerCarDto(CustomerCar car) {

        CustomerCarDto customerCarDto = new CustomerCarDto();

        customerCarDto.setCarId(car.getId());
        customerCarDto.setCustomerId(car.getCustomer().getId());
        customerCarDto.setMaker(car.getYear().getModel().getMaker().getMake());
        customerCarDto.setModel(car.getYear().getModel().getModel());
        customerCarDto.setYear(car.getYear().getYear());
        customerCarDto.setRegistration(car.getRegistration());
        customerCarDto.setCarVin(car.getCar_vin());
        customerCarDto.setDeleted(car.getDeleted());

        return customerCarDto;
    }
}
