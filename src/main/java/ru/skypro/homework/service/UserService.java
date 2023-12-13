package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;

import java.io.IOException;

public interface UserService {
    UserInfo getUser(Authentication auth);
    UserUpdate updateUser(Authentication auth, UserUpdate user);
    boolean updatePassword(Authentication auth, NewPassword password);
    boolean updateAvatar(Authentication auth, MultipartFile image) throws IOException;
    byte[] getAvatar(int idUser);
}
