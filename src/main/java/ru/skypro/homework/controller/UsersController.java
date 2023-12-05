package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@Valid @RequestBody NewPassword pass) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updatePassword(auth,pass);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUser(auth);
    }

    @PatchMapping("/me")
    public UserUpdate updateUser(@Valid @RequestBody UserUpdate userUpdate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.updateUser(auth,userUpdate);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestPart("image") MultipartFile image) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.updateAvatar(auth,image);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/avatars/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable("id") Integer avatarId){
        return ResponseEntity.ok().body(userService.getAvatar(avatarId));
    }
}
