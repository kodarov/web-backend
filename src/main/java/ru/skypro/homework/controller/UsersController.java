package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {
    /**
     * Получение информации об авторизованном пользователе
     *
     * @return
     */
    @GetMapping("/me")
    public UserInfo getUserInfo() {
        return new UserInfo();
    }

    /**
     * Обновление информации об авторизованном пользователе
     *
     * @param userUpdate
     * @return
     */
    @PatchMapping("/me")
    public UserUpdate updateUser(@RequestBody UserUpdate userUpdate) {
        return new UserUpdate();
    }

    /**
     * Обновление аватара авторизованного пользователя
     * @param image
     * @return
     * @throws IOException
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestPart("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok().build();
    }

    /**
     * Обновление пароля
     *
     * @param pass
     * @return
     */
    @PostMapping("/set_password")
    public NewPassword setPassword(@RequestBody NewPassword pass) {
        log.info("Изменение пароля");
        return new NewPassword();
    }
}
