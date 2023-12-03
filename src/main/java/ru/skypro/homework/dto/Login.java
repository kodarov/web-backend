package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Login {
    @NotBlank
    @Size(min = 4, max = 32, message = "Login length must be from 4 to 32 characters")
    private String username;
    @NotBlank
    @Size(min = 8, max = 16, message = "Password length must be between 8 and 16 characters")
    private String password;
}
