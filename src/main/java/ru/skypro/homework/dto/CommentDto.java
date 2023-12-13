package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;             //date and time of comment creation in milliseconds since 00:00:00 01.01
    private int pk;                     //comment id
    private String text;
}
