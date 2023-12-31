package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

/**
 * Entity representing a user.
 */
@Entity(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "avatar_id",referencedColumnName = "id")
    private Avatar avatar;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
}
