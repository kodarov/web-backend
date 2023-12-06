package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Entity representing an advertisement.
 */
@Entity(name = "ads")
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private AdImage adImage;
    private int price;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
