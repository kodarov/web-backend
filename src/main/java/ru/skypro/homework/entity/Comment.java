package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Сущность комментарий
 * @Comment
 */
@Entity(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp dateTime;
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

}
