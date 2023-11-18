package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Comments {
    int count;
    CommentDto[] results;
}
