package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserServiceImpl detailsService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testLoginSuccess() {
        String userName = "TestUser";
        String password = "TestPassword";
        when(userRepository.existsByLoginIgnoreCase(userName)).thenReturn(true);

        UserDetails userDetails = mock(UserDetails.class);
        when(detailsService.loadUserByUsername(userName)).thenReturn(userDetails);
        when(encoder.matches(password, userDetails.getPassword())).thenReturn(true);

        assertTrue(authService.login(userName, password));

        verify(userRepository, times(1)).existsByLoginIgnoreCase(userName);
        verify(detailsService, times(1)).loadUserByUsername(userName);
        verify(encoder, times(1)).matches(password, userDetails.getPassword());
    }

    @Test
    void testLoginFailure() {
        String userName = "TestUser";
        String password = "TestPassword";
        when(userRepository.existsByLoginIgnoreCase(userName)).thenReturn(false);

        assertFalse(authService.login(userName, password));

        verify(userRepository, times(1)).existsByLoginIgnoreCase(userName);
        verify(detailsService, never()).loadUserByUsername(anyString());
        verify(encoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testRegisterSuccess() {
        Register register = new Register();
        register.setUsername("TestNewUser");

        when(userRepository.existsByLoginIgnoreCase(register.getUsername())).thenReturn(false);

        assertTrue(authService.register(register));

        verify(userRepository, times(1)).existsByLoginIgnoreCase(register.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterFailure() {
        Register register = new Register();
        register.setUsername("TestUser");

        when(userRepository.existsByLoginIgnoreCase(register.getUsername())).thenReturn(true);

        assertFalse(authService.register(register));

        verify(userRepository, times(1)).existsByLoginIgnoreCase(register.getUsername());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
