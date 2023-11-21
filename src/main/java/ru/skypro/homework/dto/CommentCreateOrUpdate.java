package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO для создания комментария
 */
@Data
public class CommentCreateOrUpdate {
    /**
     * текст комментария
     */
    private String text;
}
