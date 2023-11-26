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
/*    @OneToOne(mappedBy = "avatar")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;*/
}
