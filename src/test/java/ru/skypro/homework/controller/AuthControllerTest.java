package ru.skypro.homework.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

import java.net.URI;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private AuthController authController;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username",postgres::getUsername);
        registry.add("spring.datasource.password",postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "validate");
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(authController).isNotNull();
    }
    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    //User SQL changeset
    @Test
    void loginTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/login");
        Login login = new Login();
        login.setUsername("kodarov@gmail.com");
        login.setPassword("password");
        ResponseEntity<?> response = restTemplate.postForEntity(newUrl,login,Login.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
    }
    @Test
    void loginTestNegative() throws Exception {
        URI newUrl = new URI(baseUrl + "/login");
        Login login = new Login();
        login.setUsername("kodarov@gmail.com");
        login.setPassword("passwordNegative");
        ResponseEntity<?> response = restTemplate.postForEntity(newUrl,login,Login.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED).isNotNull();
    }

    @Test
    void registerTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/register");
        Register register = new Register();
        register.setUsername("testuser1@gmail.com");
        register.setPassword("testpassword1");
        register.setFirstName("Test");
        register.setLastName("Testsky");
        register.setPhone("+79616544111");
        register.setRole(Role.USER);

        ResponseEntity<?> response = restTemplate.postForEntity(newUrl,register, Register.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED).isNotNull();
        //login new user
        URI newUrlLogin = new URI(baseUrl + "/login");
        Login login = new Login();
        login.setUsername("testuser1@gmail.com");
        login.setPassword("testpassword1");
        ResponseEntity<?> responseLogin = restTemplate.postForEntity(newUrlLogin,login,Login.class);
        Assertions.assertThat(responseLogin.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
    }

    @Test
    void registerTestNegative() throws Exception {
        URI newUrl = new URI(baseUrl + "/register");
        Register register = new Register();
        register.setUsername("testuser1@gmail.com");
        register.setPassword("testpassword1");
        register.setFirstName("Test");
        register.setLastName("Testsky");
        register.setPhone("+79616544111");
        register.setRole(Role.USER);

        ResponseEntity<?> response = restTemplate.postForEntity(newUrl,register, Register.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).isNotNull();
    }
}