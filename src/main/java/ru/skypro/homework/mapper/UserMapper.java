package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.UserEntity;

public class UserMapper {
    public static UserInfo outDto(UserEntity userEntity){
        UserInfo dto = new UserInfo();
        dto.setId(userEntity.getId());
        dto.setEmail(userEntity.getLogin());
        dto.setFirstName(userEntity.getFirstName());
        dto.setLastName(userEntity.getLastName());
        dto.setPhone(userEntity.getPhone());
        dto.setRole(userEntity.getRole());
        dto.setImage(String.format("me/%d/image", userEntity.getAvatar().getId()));
        return dto;
    }

    public static UserEntity inDto(UserUpdate userUpdate, UserEntity user){
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setPhone(userUpdate.getPhone());
        return user;
    }
}
