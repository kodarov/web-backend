package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Register {
    @NotBlank
    @Size(min = 4, max = 32, message = "Login length must be from 4 to 32 characters")
    private String username;
    @NotBlank
    @Size(min = 8, max = 16, message = "Password length must be between 8 and 16 characters")
    private String password;
    @NotBlank
    @Size(min = 2, max = 16, message = "Firstname length must be between 2 and 16 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 16, message = "Lastname length must be from 2 to 16 characters")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^(\\+7|8)\\s?\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{2})[-.\\s]?(\\d{2})$",
            message = "Phone number starts with +7 or 8, parentheses and hyphens are allowed")
    private String phone;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
