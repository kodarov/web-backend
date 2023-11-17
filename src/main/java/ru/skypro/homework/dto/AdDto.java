package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdDto {
    int author;
    String image;
    int pk; //id объявления
    int price;
    String title;
}
