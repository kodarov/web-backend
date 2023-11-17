package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdInfo {
    int pk; // id объявления
    String authorFirstName;
    String authorLastName;
    String description;
    String email;
    String image;
    String phone;
    int price;
    String title;
}
