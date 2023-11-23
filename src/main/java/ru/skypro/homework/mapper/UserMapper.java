package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.User;

public class UserMapper {
    public static UserInfo outDto(User user){
        UserInfo dto = new UserInfo();
        dto.setId(user.getId());
        dto.setEmail(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setImage(String.format("me/%d/image",user.getAvatar().getId()));
        return dto;
    }

    public static User inDto(UserUpdate userUpdate){
        User user = new User();
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setPhone(userUpdate.getPhone());
        return user;
    }
}
