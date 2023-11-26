package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "avatars")
@Data
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "bytea")
    private byte[] data;
    @OneToOne
    User user;
}
