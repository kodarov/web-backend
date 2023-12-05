package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AuthService;

/**
 * Authentication service for handling login and registration.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl detailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    /**
     * Attempt to log in a user with the provided credentials.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return True if login is successful, false otherwise.
     */
    @Override
    public boolean login(String userName,String password) {
        log.debug("--- service started login");
            if (!userRepository.existsByLoginIgnoreCase(userName)) {
                return false;
            }
            UserDetails userDetails = detailsService.loadUserByUsername(userName);
            if (encoder.matches(password, userDetails.getPassword())) {
                log.debug("--- user login: " + userName);
                return true;
            }
            return false;
    }
    /**
     * Register a new user with the provided registration details.
     *
     * @param register The registration details of the new user.
     * @return True if registration is successful, false if the username already exists.
     */
    @Override
    public boolean register(Register register) {
        log.debug("--- service started register");
        if (userRepository.existsByLoginIgnoreCase(register.getUsername())) {
            return false;
        }

        UserEntity newUser = createUser(register);

        try {
            userRepository.save(newUser);
            log.debug("--- user registered: " + register.getUsername());
            return true;
        }catch (Exception e){
            log.error("error during user registration: " + e.getMessage());
            return false;
        }
    }

    /**
     * Creates a user entity based on DTO registration data.
     *
     * @param register The registration details of the new user.
     * @return A UserEntity object representing the new user.
     */
    private UserEntity createUser(Register register) {
        UserEntity newUser = new UserEntity();
        newUser.setLogin(register.getUsername());
        newUser.setPassword(encoder.encode(register.getPassword()));
        newUser.setFirstName(register.getFirstName());
        newUser.setLastName(register.getLastName());
        newUser.setPhone(register.getPhone());
        newUser.setRole(register.getRole());
        newUser.setAvatar(new Avatar());
        return newUser;
    }
}
