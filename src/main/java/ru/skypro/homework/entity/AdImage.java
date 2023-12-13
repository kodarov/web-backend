package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Entity representing an image for an advertisement.
 */

@Entity
@Data
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "bytea")
    private byte[] data;
}
