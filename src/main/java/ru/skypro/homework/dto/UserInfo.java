package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserInfo {
    int id;
    String email;
    String firstName;
    String lastName;
    String phone;
    Role role;  //user или admin
    String image; //ссылка на аватар пользователя
}
