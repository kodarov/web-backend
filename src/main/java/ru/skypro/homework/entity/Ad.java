package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Сущность объявления
 * @Ad
 */
@Entity(name = "ads")
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String image;
    private int price;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
