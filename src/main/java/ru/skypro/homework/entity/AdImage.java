package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long fileSize;
    private String mediaType;
    @Column(columnDefinition = "bytea")
    private byte[] data;
    @ManyToOne
    private Ad ad;
}
