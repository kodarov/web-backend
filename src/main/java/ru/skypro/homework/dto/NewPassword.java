package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewPassword {
    @Size(min = 8, max = 16, message = "Длина текущего пароля должна быть от 8 до 16 символов")
    private String currentPassword;
    @Size(min = 8, max = 16, message = "Длина нового пароля должна быть от 8 до 16 символов")
    private String newPassword;
}
