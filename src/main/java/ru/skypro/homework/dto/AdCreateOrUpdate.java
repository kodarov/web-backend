package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * DTO создания и обновления объявления
 */
@Data
public class AdCreateOrUpdate {
    @Size(min = 4, max = 32, message = "Длина заголовка должна быть от 4 до 32 символов")
    String title;
    @Size(min = 0, max = 10000000, message = "Лимит цены 10000000")
    int price;
    @Size(min = 8, max = 64, message = "Длина описания должна быть от 8 до 64 символов")
    String description;
}
