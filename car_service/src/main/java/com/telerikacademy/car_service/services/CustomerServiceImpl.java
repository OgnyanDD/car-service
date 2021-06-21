package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.car_service.exceptions.DuplicateDatabaseItemFoundException;
import com.telerikacademy.car_service.exceptions.WrongPasswordException;
import com.telerikacademy.car_service.models.Customer;
import com.telerikacademy.car_service.models.dto.customer.*;
import com.telerikacademy.car_service.repositories.CustomerRepository;
import com.telerikacademy.car_service.services.contracts.CustomerService;
import com.telerikacademy.car_service.services.contracts.EmailService;
import com.telerikacademy.car_service.services.contracts.PassayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND_BY_EMAIL_EXCEPTION_MSG = "Customer with email %s not found!";
    private static final String CUSTOMER_NOT_FOUND_BY_ID_EXCEPTION_MSG = "Customer with ID %s not found!";
    private static final String DATABASE_NOT_FOUND_EXCEPTION_MSG = "Failed to access database!";
    private static final String CUSTOMER_EXIST_EXCEPTION_MSG = "Customer already exist!";
    private static final String WRONG_PASSWORD_EXCEPTION_MSG = "Wrong password!";

    private static final String AUTHORITY_ROLE = "ROLE_USER";
    private static final String REGISTRATION_EMAIL_SUBJECT = "Welcome to CAR SERVICE!";
    private static final String FORGOTTEN_PASSWORD_EMAIL_SUBJECT = "Request to reset your password in CAR SERVICE";

    private static final String FORGOTTEN_PASSWORD_MESSAGE =
            "<h1>Forgot your password?</h1>\n" +
                    "<p>No worries! It happens.</p>\n" +
                    "<p>Your new password is:</p>\n" +
                    "<div style=\"background: rgba(0, 0, 0, 0.1); border: 2px solid black; border-radius: 10px; width: 300px; overflow: auto;\">\n" +
                    "    <p style=\"margin-left: 5px\"><u>PASSWORD</u></p>\n" +
                    "    <p style=\"margin-left: 5px; margin-right: 5px; font-weight: bold; background: rgba(0, 0, 0, 0.2);\">%s</p>\n" +
                    "</div>\n" +
                    "<p style=\"font-style: italic\">You can change your password after your first login.</p>\n" +
                    "<p>Kind regards,</p>\n" +
                    "<p style=\"font-weight: bold\">Car Service Team</p>\n" +
                    "<a href=\"https://imgbb.com/\"><img src=\"https://i.ibb.co/9V4pjq3/footer-logo.png\" alt=\"footer-logo\" border=\"0\"></a>";

    private CustomerRepository customerRepository;
    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private PassayService passwordService;
    private SimpleMailMessage simpleMailMessage;
    private EmailService emailService;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            UserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder,
            PassayService passwordService,
            SimpleMailMessage simpleMailMessage,
            EmailService emailService) {

        this.customerRepository = customerRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.passwordService = passwordService;
        this.simpleMailMessage = simpleMailMessage;
        this.emailService = emailService;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {

        return getCustomerDtos(customerRepository.getAllCustomers());
    }

    @Override
    public Customer getCustomerByEmail(String customerEmail) {

        Customer customer = customerRepository.findByEmail(customerEmail);

        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(
                    CUSTOMER_NOT_FOUND_BY_EMAIL_EXCEPTION_MSG, customerEmail));
        }
        return customerRepository.findByEmail(customerEmail);
    }

    @Override
    public Customer getCustomerById(Integer customerId) {

        Customer customer = customerRepository.getById(customerId);

        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(
                    CUSTOMER_NOT_FOUND_BY_ID_EXCEPTION_MSG, customerId));
        }
        return customerRepository.getById(customerId);
    }

    @Override
    @Transactional
    public void addCustomer(CustomerCreateDto customerCreateDto) {

        if (customerRepository.existsCustomerByEmail(customerCreateDto.getUsername())) {
            throw new DuplicateDatabaseItemFoundException(CUSTOMER_EXIST_EXCEPTION_MSG);
        }
        Customer newCustomer = new Customer();
//        newCustomer.setPicture(null);
        newCustomer.setEmail(customerCreateDto.getUsername());
        newCustomer.setName(customerCreateDto.getName());
        newCustomer.setPhoneNumber(customerCreateDto.getPhoneNumber());
        newCustomer.setIsDeleted(false);

        String password = passwordService.generateRandomPassword();
        String passwordEncoded = passwordEncoder.encode(password);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(AUTHORITY_ROLE);
        User newUser = new User(customerCreateDto.getUsername(), passwordEncoded, authorities);

        String message = simpleMailMessage.getText();

        String text = "";
        if (message != null) {
            text = String.format(message, newUser.getUsername(), password);
        }

        // TODO WARNING: This will send email, if code is active!
        emailService.sendMessageWithAttachment(newUser.getUsername(), REGISTRATION_EMAIL_SUBJECT, text);

        userDetailsManager.createUser(newUser);
        customerRepository.save(newCustomer);

        if (!customerCreateDto.getPicture().getResource().getFilename().equals("")) {
            Customer newlyCreatedCustomer = this.setCustomerPicture(newCustomer.getEmail(), customerCreateDto.getPicture());
            customerRepository.save(newlyCreatedCustomer);
        }
    }

    @Override
    @Transactional
    public void editCustomerProfile(CustomerEditProfileDto customerEditProfileDto) {

        Customer customer = getCustomerByEmail(customerEditProfileDto.getUsername());
        if (!Objects.requireNonNull(customerEditProfileDto.getPicture().getResource().getFilename()).isEmpty()) {
            customer = setCustomerPicture(customerEditProfileDto.getUsername(), customerEditProfileDto.getPicture());
        }
        customer.setName(customerEditProfileDto.getName());
        customer.setPhoneNumber(customerEditProfileDto.getPhoneNumber());

        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void changeCustomerPassword(CustomerChangePasswordDto customerChangePasswordDto) {

        Customer customer = customerRepository.findByEmail(customerChangePasswordDto.getUsername());
        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(CUSTOMER_NOT_FOUND_BY_EMAIL_EXCEPTION_MSG,
                    customerChangePasswordDto.getUsername()));
        }
        String password = customerChangePasswordDto.getPassword();
        String newPassword = customerChangePasswordDto.getNewPassword();
        UserDetails user = userDetailsManager.loadUserByUsername(customerChangePasswordDto.getUsername());
        String userPassword = user.getPassword();
        boolean checkPassword = passwordEncoder.matches(password, userPassword);

        if (!newPassword.isEmpty() && checkPassword) {
            String passwordEncoded = passwordEncoder.encode(newPassword);
            UserDetails oldUserDetails = userDetailsManager.loadUserByUsername(customerChangePasswordDto.getUsername());
            UserDetails newUserDetails = new User(customerChangePasswordDto.getUsername(),
                    passwordEncoded, oldUserDetails.getAuthorities());

            userDetailsManager.updateUser(newUserDetails);
        } else {
            throw new WrongPasswordException(WRONG_PASSWORD_EXCEPTION_MSG);
        }
    }

    @Override
    public void changeForgottenPassword(CustomerForgottenPasswordDto customerForgottenPasswordDto) {

        Byte isAccountActive = customerRepository.checkCustomerAccountIsActive(customerForgottenPasswordDto.getUsername());
        Customer customer = customerRepository.findByEmail(customerForgottenPasswordDto.getUsername());

        if (customer == null || isAccountActive == 0) {
            throw new DatabaseItemNotFoundException(String.format(
                    CUSTOMER_NOT_FOUND_BY_EMAIL_EXCEPTION_MSG, customerForgottenPasswordDto.getUsername()));
        }

        String password = passwordService.generateRandomPassword();
        String passwordEncoded = passwordEncoder.encode(password);

        UserDetails user = userDetailsManager.loadUserByUsername(customerForgottenPasswordDto.getUsername());
        UserDetails newUserDetails = new User(customerForgottenPasswordDto.getUsername(),
                passwordEncoded, user.getAuthorities());

        String text = String.format(FORGOTTEN_PASSWORD_MESSAGE, password);

        // TODO WARNING: This will send email, if code is active!
        emailService.sendMessageWithAttachment(user.getUsername(), FORGOTTEN_PASSWORD_EMAIL_SUBJECT, text);

        userDetailsManager.updateUser(newUserDetails);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer customerId) {

        Customer customer = customerRepository.getById(customerId);

        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(
                    CUSTOMER_NOT_FOUND_BY_ID_EXCEPTION_MSG, customerId));
        }
        customerRepository.deleteCustomerById(customerId);
        customerRepository.deleteCustomerCars(customerId);
        customerRepository.disableUserAccount(customer.getEmail());
    }

    private Customer setCustomerPicture(String userEmail, MultipartFile picture) {

        Customer customer = customerRepository.findByEmail(userEmail);

        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(CUSTOMER_NOT_FOUND_BY_EMAIL_EXCEPTION_MSG,
                    userEmail));
        }
        customer.setPicture(this.saveImage(picture, customer.getId()));
        return customer;
    }

    private String saveImage(MultipartFile file, Integer userId) {
        try {
            String basePath = this.getClass().getResource("/static/images").getPath();
            File profilesDirectory = createDirectoryIfNotExist(basePath + File.separator + "profiles");
            File directory = createDirectoryIfNotExist(profilesDirectory.getPath() + File.separator + userId);
            String fileName = UUID.randomUUID() + file.getOriginalFilename();
            String filePath = directory.getPath() + File.separator + fileName;
            File imgFile = new File(filePath);
            imgFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imgFile);
            fos.write(file.getBytes());
            fos.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private File createDirectoryIfNotExist(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    private List<CustomerDto> getCustomerDtos(List<Customer> customers) {
        if (customers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, DATABASE_NOT_FOUND_EXCEPTION_MSG);
        } else {
            List<CustomerDto> customersDto = new ArrayList<>();

            for (Customer c : customers) {
                CustomerDto list = mapCustomerToDto(c);
                customersDto.add(list);
            }
            return customersDto;
        }
    }

    private CustomerDto mapCustomerToDto(Customer customer) {

        CustomerDto customerDto = new CustomerDto();

        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhoneNumber());
        customerDto.setIsDeleted(customer.getIsDeleted());

        return customerDto;
    }
}
