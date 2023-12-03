package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsServiceImpl manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        if (!userRepository.existsByLoginIgnoreCase(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        log.info("Вход юзера " + userDetails);

        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (userRepository.existsByLoginIgnoreCase(register.getUsername())) {
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.setLogin(register.getUsername());
        newUser.setPassword(encoder.encode(register.getPassword()));
        newUser.setFirstName(register.getFirstName());
        newUser.setLastName(register.getLastName());
        newUser.setPhone(register.getPhone());
        newUser.setRole(register.getRole());
        newUser.setAvatar(new Avatar());
        userRepository.save(newUser);

        return true;
    }
}