package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.Id;

/**
 * Enity
 *
 * @User Web user
 */
//@Entity
@Data
public class User {
    @Id
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;


    public User(String userName, String password, String firstName, String lastName, String phone) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = Role.USER;
    }
    public User(){}
}
