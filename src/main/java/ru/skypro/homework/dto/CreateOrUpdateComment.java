package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO для создания комментария
 */
@Data
public class CreateOrUpdateComment {
    /**
     * текст комментария
     */
    String text;
}
