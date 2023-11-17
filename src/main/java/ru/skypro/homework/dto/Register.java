package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Register {//Как избегать дублирования кода из Login и UserUpdate?
    // Композиция будет противоречить спецификации фронта, на выходе JSON будет другим.
    @Size(min = 4, max = 32, message = "Длина логина должна быть от 4 до 32 символов")
    private String username;
    @Size(min = 8, max = 16, message = "Длина пароля должна быть от 8 до 16 символов")
    private String password;
    @Size(min = 2, max = 16, message = "Длина имени должна быть от 2 до 16 символов")
    private String firstName;
    @Size(min = 2, max = 16, message = "Длина фамилии должна быть от 2 до 16 символов")
    private String lastName;
    @Pattern(regexp = "^(\\+7|8)\\s?\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{2})[-.\\s]?(\\d{2})$",
            message = "Номер телефона начинается с +7 или 8,допускает скобки и дефис")
    private String phone;
    private Role role;
}
