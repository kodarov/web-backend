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
import ru.skypro.homework.exception.AvatarNotFoundException;
import ru.skypro.homework.exception.EntityNotFoundException;
import ru.skypro.homework.exception.InvalidImageException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repositories.AvatarRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

/**
 * Service for processing user-related operations.
 *
 * @author KodarovSS
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    /**
     * Load user details by username.
     *
     * @param username The username to load user details.
     * @return UserDetails of the specified user.
     * @throws UsernameNotFoundException if the user is not found.
     */
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

    /**
     * Get user information based on authentication details.
     *
     * @param auth The authentication details.
     * @return UserInfo DTO object containing user information.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public UserInfo getUser(Authentication auth) {
        log.debug("--- started getUser");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found " + auth.getName()));
        return userMapper.outDto(userEntity);
    }

    /**
     * Update user information.
     *
     * @param auth       The authentication details.
     * @param userUpdate The DTO with updated user information.
     * @return Updated UserUpdate DTO object.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public UserUpdate updateUser(Authentication auth, UserUpdate userUpdate) {
        log.debug("--- service started updateUser");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found " + auth.getName()));
        UserEntity updatedUser = userMapper.inDto(userUpdate, userEntity);
        userRepository.save(updatedUser);
        return userUpdate;
    }

    /**
     * Update user password.
     *
     * @param auth     The authentication details.
     * @param password The DTO with new and current passwords.
     * @return True if the password is updated, false otherwise.
     */
    @Override
    public boolean updatePassword(Authentication auth, NewPassword password) {
        log.debug("--- service started updatePassword");
        UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName()).orElseThrow();

        if (encoder.matches(password.getCurrentPassword(), userEntity.getPassword())) {
            userEntity.setPassword(encoder.encode(password.getNewPassword()));
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }

    /**
     * Update user avatar.
     *
     * @param auth  The authentication details.
     * @param image The new avatar image data.
     * @return True if the avatar is updated, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public boolean updateAvatar(Authentication auth, MultipartFile image) throws IOException {
        log.debug("--- service started updateAvatar");
        try {
            UserEntity userEntity = userRepository.findUserEntityByLoginIgnoreCase(auth.getName())
                    .orElseThrow(() -> new UserNotFoundException("User not found" + auth.getName()));
            if (!image.getContentType().startsWith("image/")) {
                throw new InvalidImageException();
            }

            Avatar avatar = userEntity.getAvatar();
            avatar.setData(image.getBytes());
            userEntity.setAvatar(avatar);
            userRepository.save(userEntity);
            return true;
        } catch (IOException e) {
            log.error("Error updating avatar: " + e.getMessage());
            throw new AvatarNotFoundException();
        }
    }

    /**
     * Get avatar data by avatar ID.
     *
     * @param avatarId The ID of the avatar.
     * @return Byte array representing avatar data.
     * @throws EntityNotFoundException if the avatar is not found.
     */
    @Override
    public byte[] getAvatar(int avatarId) {
        log.info("--- service started getAvatar");
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new AvatarNotFoundException());
        return avatar.getData();
    }
}
