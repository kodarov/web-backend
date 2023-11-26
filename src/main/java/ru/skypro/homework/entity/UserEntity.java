package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

/**
 * Сущность юзера
 * @UsersEntity
 */
@Entity(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Avatar avatar;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
}
