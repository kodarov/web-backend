package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repositories.AvatarRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final PasswordEncoder encoder;
    @Override
    public UserInfo getUser(Authentication auth) {
        log.info("сервис getUser");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        return UserMapper.outDto(userEntity);
    }

    @Override
    public UserUpdate updateUser(Authentication auth, UserUpdate userUpdate) {
        log.info("сервис updateUser");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        UserEntity updatedUser = UserMapper.inDto(userUpdate,userEntity);
        userRepository.save(updatedUser);
        return userUpdate;
    }

    @Override
    public boolean updatePassword(Authentication auth, NewPassword password) {
        log.info("сервис updatePassword");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        if (encoder.matches(password.getCurrentPassword(), userEntity.getPassword())){ //совпадает ли текущий с хэшем?
            userEntity.setPassword(encoder.encode(password.getNewPassword())); // тогда сохраняем хэш нового пароля
            userRepository.save(userEntity);
            log.info("пароль изменен");
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAvatar(Authentication auth, MultipartFile image) throws IOException {
        log.info("сервис updateAvatar");
        try {
            UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
            Avatar avatar = userEntity.getAvatar();
            avatar.setData(image.getBytes());
            userEntity.setAvatar(avatar);
            userRepository.save(userEntity);
            return true;
        }
        catch (IOException e){
            return false;
        }
    }

    @Override
    public byte[] getAvatar(int avatarId) {
        log.info("сервис getAvatar");
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow();
        return avatar.getData();
    }
}
