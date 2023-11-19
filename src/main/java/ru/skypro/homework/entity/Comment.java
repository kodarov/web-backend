package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность комментарий
 * @Comment
 */
@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate localDate;
    private String text;
    @ManyToOne
    private User user;
    @ManyToOne
    private Ad ad;

}
