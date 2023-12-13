package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewPassword {
    @NotBlank
    @Size(min = 8, max = 16, message = "The current password must be between 8 and 16 characters long")
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 16, message = "The length of the new password must be between 8 and 16 characters")
    private String newPassword;
}
