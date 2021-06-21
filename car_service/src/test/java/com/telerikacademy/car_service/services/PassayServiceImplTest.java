package com.telerikacademy.car_service.services;

import com.telerikacademy.car_service.services.contracts.PassayService;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassayServiceImplTest {

    private PassayService passayService = new PassayServiceImpl();


    @Test
    public void whenPasswordGeneratedUsingPass_thenSuccessful() {
        String password = passayService.generateRandomPassword();
        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            specialCharCount++;
        }
        assertTrue("Password validation failed in Pass", specialCharCount >= 2);
    }
}