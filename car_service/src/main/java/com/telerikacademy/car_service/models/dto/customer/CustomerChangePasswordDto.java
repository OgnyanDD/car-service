package com.telerikacademy.car_service.models.dto.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerChangePasswordDto {

    @NotNull
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_.\\-!@#$]*$",
            message = "The user password can only contains letters," +
                    "numbers and special characters like ('-', '_', '.', '!', '@', '#', '$')!")
    @Size(
            min = 8,
            max = 128,
            message = "The user password length must be min {min} characters and max {max} characters!"
    )
    private String newPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
