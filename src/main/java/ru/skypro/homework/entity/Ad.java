package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность объявления
 * @Ad
 */
@Entity
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private static int count;
    private String image;
    private int price;
    private String title;
    private String description;
    @ManyToOne
    private User user;
}
