package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        log.info("Вход юзера " + userName);

        //для теста дублируем в БД
        UserEntity newUser = new UserEntity();
        newUser.setId(1);
        newUser.setLogin("user@gmail.com");
        newUser.setPassword(encoder.encode("password"));
        newUser.setFirstName("firstName");
        newUser.setLastName("lastName");
        newUser.setPhone("+79616544133");
        newUser.setRole(Role.USER);
        newUser.setAvatar(new Avatar());
        userRepository.save(newUser);

        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());

        //для теста дублируем в БД
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