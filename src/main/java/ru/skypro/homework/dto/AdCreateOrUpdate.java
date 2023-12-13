package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class AdCreateOrUpdate {
    @Size(min = 4, max = 32, message = "The title length must be from 4 to 32 characters")
    private String title;
    @Size(min = 0, max = 10000000, message = "Price limit 10000000")
    private int price;
    @Size(min = 8, max = 64, message = "Description length must be from 8 to 64 characters")
    private String description;
}
