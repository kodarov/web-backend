package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {
    @GetMapping("/me")
    public UserDto getUser (){
        return new UserDto();
    }
    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody UserDto user){
        return new UserDto();
    }
    @PatchMapping(value ="/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestParam MultipartFile avatar) throws IOException {
        return ResponseEntity.ok().build();
    }
    @PostMapping("/set_password")
    public PasswordDto setPassword(@RequestBody PasswordDto pass){
        return new PasswordDto();
    }
}
