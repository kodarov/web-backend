package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.AdImage;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    /**
     * Получение информации об авторизованном пользователе
     *
     * @return
     */
    @GetMapping("/me")
    public UserInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUser(auth);
    }

    /**
     * Обновление информации об авторизованном пользователе
     *
     * @param userUpdate
     * @return
     */
    @PatchMapping("/me")
    public UserUpdate updateUser(@RequestBody UserUpdate userUpdate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updateUser(auth,userUpdate);
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
    public ResponseEntity<String> setPassword(@RequestBody NewPassword pass) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updatePassword(auth,pass);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getAvatar(@PathVariable("id") Integer idUser){
        //пока без бизнес-логики
        Avatar avatar = new Avatar();
        return ResponseEntity.ok().body(avatar.getData());
    }

}
