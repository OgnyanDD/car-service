package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.car_service.exceptions.DuplicateDatabaseItemFoundException;
import com.telerikacademy.car_service.exceptions.WrongPasswordException;
import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.CustomerCar;
import com.telerikacademy.car_service.models.dto.customer.*;
import com.telerikacademy.car_service.repositories.CustomerRepository;
import com.telerikacademy.car_service.services.contracts.EmailService;
import com.telerikacademy.car_service.services.contracts.PassayService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {


    private static final String PHOTO = "photo";
    private static final String CUSTOMER_NAME = "Ognyan Dimitrov";
    private static final String EMAIL = "ogy2811@gmail.com";
    private static final String PASSWORD = "password01";
    private static final String NEW_PASSWORD = "password02";
    private static final String PHONE = "+359000111222";
    private static final Boolean IS_DELETED = false;

    private static final Customer CUSTOMER = new Customer();
    private static final List<CustomerCar> CUSTOMER_CARS = new ArrayList<>();
    private static final List<Customer> CUSTOMERS = new ArrayList<>();
    private static final CustomerCreateDto CUSTOMER_CREATE_DTO = new CustomerCreateDto();
    private static final CustomerEditProfileDto CUSTOMER_EDIT_PROFILE_DTO = new CustomerEditProfileDto();
    private static final CustomerChangePasswordDto CUSTOMER_CHANGE_PASSWORD_DTO = new CustomerChangePasswordDto();
    private static final CustomerForgottenPasswordDto CUSTOMER_FORGOTTEN_PASSWORD_DTO = new CustomerForgottenPasswordDto();

    @Mock
    private CustomerRepository mockCustomerRepository;
    @Mock
    private UserDetailsManager userDetailsManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PassayService passayService;
    @Mock
    private SimpleMailMessage simpleMailMessage;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Before
    public void setDefaultTestServices() {
        CUSTOMER.setId(1);
        CUSTOMER.setPicture(PHOTO);
        CUSTOMER.setName(CUSTOMER_NAME);
        CUSTOMER.setEmail(EMAIL);
        CUSTOMER.setPhoneNumber(PHONE);
        CUSTOMER.setIsDeleted(IS_DELETED);
        CUSTOMER.setCustomerCars(CUSTOMER_CARS);
        CUSTOMERS.add(CUSTOMER);

        InputStream inputFile = this.getClass().getResourceAsStream("/images/profiles/user_default.png");
        try {
            MockMultipartFile file = new MockMultipartFile("file",
                    "NameOfTheFile", "multipart/form-data", inputFile);
            CUSTOMER_CREATE_DTO.setPicture(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CUSTOMER_CREATE_DTO.setName(CUSTOMER_NAME);
        CUSTOMER_CREATE_DTO.setUsername(EMAIL);
        CUSTOMER_CREATE_DTO.setPhoneNumber(PHONE);

        CUSTOMER_EDIT_PROFILE_DTO.setId(1);
        try {
            MockMultipartFile file = new MockMultipartFile("file",
                    "NameOfTheFile", "multipart/form-data", inputFile);
            CUSTOMER_EDIT_PROFILE_DTO.setPicture(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CUSTOMER_EDIT_PROFILE_DTO.setName(CUSTOMER_NAME);
        CUSTOMER_EDIT_PROFILE_DTO.setUsername(EMAIL);
        CUSTOMER_EDIT_PROFILE_DTO.setPhoneNumber(PHONE);

        CUSTOMER_CHANGE_PASSWORD_DTO.setId(1);
        CUSTOMER_CHANGE_PASSWORD_DTO.setUsername(CUSTOMER_NAME);
        CUSTOMER_CHANGE_PASSWORD_DTO.setPassword(PASSWORD);
        CUSTOMER_CHANGE_PASSWORD_DTO.setNewPassword(NEW_PASSWORD);

        CUSTOMER_FORGOTTEN_PASSWORD_DTO.setUsername(EMAIL);
    }

    @Test
    public void getAllCustomers_shouldReturnOneCustomer_whenOneCustomerAvailable() {

        when(mockCustomerRepository.getAllCustomers()).thenReturn(CUSTOMERS);
        List<CustomerDto> customers = customerService.getAllCustomers();
        Assert.assertEquals(1, customers.size());
        verify(mockCustomerRepository, Mockito.times(1)).getAllCustomers();
    }

    @Test(expected = ResponseStatusException.class)
    public void getAllCustomers_shouldThrowException_whenNoDataExist() {

        when(mockCustomerRepository.getAllCustomers()).thenThrow(ResponseStatusException.class);
        customerService.getAllCustomers();
    }

    @Test
    public void getCustomerByEmail_shouldReturnCustomer_whenCustomerExist() {

        when(mockCustomerRepository.findByEmail("ivan_petrov@test.bg")).thenReturn(CUSTOMER);
        Customer expected = customerService.getCustomerByEmail("ivan_petrov@test.bg");
        Assert.assertEquals(expected, CUSTOMER);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getCustomerByEmail_shouldThrowException_whenCustomerNotExist() {

        Customer customer = customerService.getCustomerByEmail("ivan_petrov@test.bg");
    }

    @Test
    public void getCustomerById_shouldReturnCustomer_whenCustomerExist() {

        when(mockCustomerRepository.getById(1)).thenReturn(CUSTOMER);
        Customer expected = customerService.getCustomerById(1);
        Assert.assertEquals(expected, CUSTOMER);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getCustomerById_shouldThrowException_whenCustomerNotExist() {

        Customer customer = customerService.getCustomerById(1);
    }

    @Test
    public void addCustomer_shouldRegisterNewCustomer() {

        String email = CUSTOMER.getEmail();
        when(passayService.generateRandomPassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        User user = new User(email, PASSWORD, authorities);
        simpleMailMessage.setText("");
        when(simpleMailMessage.getText()).thenReturn("This is test register msg.");
        when(mockCustomerRepository.findByEmail(CUSTOMER_CREATE_DTO.getUsername())).thenReturn(CUSTOMER);
        customerService.addCustomer(CUSTOMER_CREATE_DTO);
    }

    @Test(expected = DuplicateDatabaseItemFoundException.class)
    public void addCustomer_shouldThrowException_whenCustomerAlreadyExist() {

        when(mockCustomerRepository.existsCustomerByEmail(CUSTOMER_CREATE_DTO.getUsername())).thenReturn(true);
        customerService.addCustomer(CUSTOMER_CREATE_DTO);
    }

    @Test
    public void editCustomerProfile_shouldEditCustomerProfile_whenCustomerExist() {

        when(mockCustomerRepository.findByEmail(CUSTOMER_EDIT_PROFILE_DTO.getUsername())).thenReturn(CUSTOMER);
        when(mockCustomerRepository.findByEmail(CUSTOMER_EDIT_PROFILE_DTO.getUsername())).thenReturn(CUSTOMER);
        customerService.editCustomerProfile(CUSTOMER_EDIT_PROFILE_DTO);
        verify(mockCustomerRepository, Mockito.times(1)).save(any(Customer.class));
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void editCustomerProfile_shouldThrowException_whenCustomerNotExist() {

        when(mockCustomerRepository.findByEmail(CUSTOMER_EDIT_PROFILE_DTO.getUsername())).thenReturn(null);
        customerService.editCustomerProfile(CUSTOMER_EDIT_PROFILE_DTO);
    }

    @Test
    public void changeCustomerPassword_shouldChangeCustomerCurrentPassword() {
        String email = CUSTOMER.getEmail();
        when(mockCustomerRepository.findByEmail(CUSTOMER_CHANGE_PASSWORD_DTO.getUsername())).thenReturn(CUSTOMER);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        User user = new User(email, PASSWORD, authorities);
        when(userDetailsManager.loadUserByUsername(CUSTOMER_CHANGE_PASSWORD_DTO.getUsername())).thenReturn(user);
        when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(NEW_PASSWORD);
        when(passwordEncoder.matches(PASSWORD, user.getPassword())).thenReturn(true);

        customerService.changeCustomerPassword(CUSTOMER_CHANGE_PASSWORD_DTO);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void changeCustomerPassword_shouldThrowException_whenCustomerNotExist() {
        when(mockCustomerRepository.findByEmail(CUSTOMER_CHANGE_PASSWORD_DTO.getUsername())).thenReturn(null);
        customerService.changeCustomerPassword(CUSTOMER_CHANGE_PASSWORD_DTO);
    }

    @Test(expected = WrongPasswordException.class)
    public void changeCustomerPassword_shouldThrowException_whenPasswordsAreIncorrect() {
        String email = CUSTOMER.getEmail();
        when(mockCustomerRepository.findByEmail(CUSTOMER_CHANGE_PASSWORD_DTO.getUsername())).thenReturn(CUSTOMER);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        User user = new User(email, PASSWORD, authorities);
        when(userDetailsManager.loadUserByUsername(CUSTOMER_CHANGE_PASSWORD_DTO.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(PASSWORD, user.getPassword())).thenReturn(false);

        customerService.changeCustomerPassword(CUSTOMER_CHANGE_PASSWORD_DTO);
    }

    @Test
    public void deleteCustomer_shouldDeleteCustomer_whenExist() {

        when(mockCustomerRepository.getById(1)).thenReturn(CUSTOMER);
        customerService.deleteCustomer(1);
        verify(mockCustomerRepository, Mockito.times(1)).deleteCustomerById(1);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void deleteCustomer_shouldThrowException_whenCustomerNotExist() {

        when(mockCustomerRepository.getById(1)).thenReturn(null);
        customerService.deleteCustomer(1);
    }

    @Test
    public void changeForgottenPassword_shouldGenerateNewPasswordThrowEmail() {

        String email = CUSTOMER.getEmail();
        when(mockCustomerRepository.checkCustomerAccountIsActive(email)).thenReturn((byte) 1);
        when(mockCustomerRepository.findByEmail(CUSTOMER_FORGOTTEN_PASSWORD_DTO.getUsername())).thenReturn(CUSTOMER);
        when(passayService.generateRandomPassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        User user = new User(email, PASSWORD, authorities);
        when(userDetailsManager.loadUserByUsername(email)).thenReturn(user);
        customerService.changeForgottenPassword(CUSTOMER_FORGOTTEN_PASSWORD_DTO);
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void changeForgottenPassword_shouldThrowException_whenCustomerNotExist() {

        when(mockCustomerRepository.findByEmail(CUSTOMER_FORGOTTEN_PASSWORD_DTO.getUsername())).thenReturn(null);
        customerService.changeForgottenPassword(CUSTOMER_FORGOTTEN_PASSWORD_DTO);
    }
}