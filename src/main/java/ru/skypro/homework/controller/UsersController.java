package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
     * Обновление пароля
     *
     * @param pass
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody NewPassword pass) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updatePassword(auth,pass);
        return ResponseEntity.ok().build();
    }

    /**
     * Получение информации об авторизованном пользователе
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updateAvatar(auth,image);
        return ResponseEntity.ok().build();
    }

    /**
     * Вспомогательный эндпоинт получения аватара
     * @param avatarId
     * @return
     */
    @GetMapping("/avatars/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable("id") Integer avatarId){
        return ResponseEntity.ok().body(userService.getAvatar(avatarId));
    }

    //для примера
    @GetMapping("/test")
    public UserDetails getTest(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
