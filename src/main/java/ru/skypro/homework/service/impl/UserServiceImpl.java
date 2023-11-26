package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserInfo getUser(Authentication auth) {
        log.info("сервис getUser");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();
        return UserMapper.outDto(userEntity);
    }

    @Override
    public UserUpdate updateUser(Authentication auth, UserUpdate user) {
        log.info("сервис updateUser");
        return null;
    }

    @Override
    public boolean updatePassword(Authentication auth, NewPassword password) {
        log.info("сервис updatePassword");
        return false;
    }

    @Override
    public boolean updateAvatar(Authentication auth, MultipartFile image) {
        log.info("сервис updateAvatar");
        return false;
    }

    @Override
    public byte[] getAvatar(int idUser) {
        log.info("сервис getAvatar");
        return new byte[0];
    }
}
