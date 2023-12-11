package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserUpdate {
    @NotBlank(message = "Must not be blank!")
    @Size(min = 2, max = 16, message = "FirstName length must be from 2 to 16 characters")
    private String firstName;
    @Size(min = 2, max = 16, message = "LastName length must be from 2 to 16 characters")
    @NotBlank(message = "Must not be blank!")
    private String lastName;
    @Pattern(regexp = "^(\\+7|8)\\s?\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{2})[-.\\s]?(\\d{2})$",
            message = "Phone number starts with +7 or 8, allows parentheses and hyphens")
    private String phone;
}
