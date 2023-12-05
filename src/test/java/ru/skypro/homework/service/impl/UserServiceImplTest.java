package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repositories.AvatarRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.security.Principal;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private AvatarRepository avatarRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Alex1001";
            }
        };
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void getUser() {


    }

    @Test
    void updateUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateAvatar() {
    }

    @Test
    void getAvatar() {
    }
}