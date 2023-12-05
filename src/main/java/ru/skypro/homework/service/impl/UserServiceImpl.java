package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repositories.AvatarRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

/**
 * Service for processing user
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login not found"));
        UserDetails userDetails = User.builder()
                .username(userEntity.getLogin())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
        return userDetails;
    }

    @Override
    public UserInfo getUser(Authentication auth) {
        log.debug("--- started getUser");
            UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                    .orElseThrow(() -> new UserNotFoundException("User not found "+ auth.getName()));
        return userMapper.outDto(userEntity);
    }

    @Override
    public UserUpdate updateUser(Authentication auth, UserUpdate userUpdate) {
        log.debug("--- service started updateUser");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found "+ auth.getName()));
        UserEntity updatedUser = userMapper.inDto(userUpdate,userEntity);
        userRepository.save(updatedUser);
        return userUpdate;
    }

    @Override
    public boolean updatePassword(Authentication auth, NewPassword password) {
        log.debug("--- service started updatePassword");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();

        if (encoder.matches(password.getCurrentPassword(), userEntity.getPassword())){
            userEntity.setPassword(encoder.encode(password.getNewPassword()));
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAvatar(Authentication auth, MultipartFile image) throws IOException {
        log.debug("--- service started updateAvatar");
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
        log.info("--- service started getAvatar");
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow();
        return avatar.getData();
    }
}
