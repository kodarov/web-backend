package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.Id;

/**
 * Entity
 *
 * @Ad Advertisement
 */
//@Entity
@Data
public class Ad {
    @Id
    private int id;
    private static int count;
    private int author;
    private String image;
    private int price;
    private String title;
    private String description;
}
