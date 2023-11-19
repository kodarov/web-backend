package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserUpdate {
    @Size(min = 2, max = 16, message = "Длина текущего пароля должна быть от 8 до 16 символов")
    private String firstName;
    @Size(min = 2, max = 16, message = "Длина текущего пароля должна быть от 8 до 16 символов")
    private String lastName;
    @Pattern(regexp = "^(\\+7|8)\\s?\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{2})[-.\\s]?(\\d{2})$",
            message = "Номер телефона начинается с +7 или 8,допускает скобки и дефис")
    private String phone;
}
