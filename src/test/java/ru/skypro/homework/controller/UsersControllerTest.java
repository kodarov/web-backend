package ru.skypro.homework.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserInfo;
import ru.skypro.homework.dto.UserUpdate;

import java.net.URI;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private UsersController usersController;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @BeforeEach
    public void setUp() throws Exception {
        baseUrl = "http://localhost:" + port + "/users";
        restTemplate = restTemplate.withBasicAuth("kodarov@gmail.com", "password");
    }
    @Test
    void setPasswordTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/set_password");

        NewPassword newPassword = new NewPassword();
        newPassword.setNewPassword("NewPassword");
        newPassword.setCurrentPassword("password");

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("testuser@gmail.com", "password")
                .postForEntity(newUrl, newPassword, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
    }

    @Test
    void setPasswordTestNegativeForbidden() throws Exception {
        URI newUrl = new URI(baseUrl + "/set_password");

        NewPassword newPassword = new NewPassword();
        newPassword.setNewPassword("newpassword");
        newPassword.setCurrentPassword("NagativePassword");

        ResponseEntity<String> response = restTemplate.postForEntity(newUrl, newPassword, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN).isNotNull();
    }

    @Test
    void setPasswordTestNegativeUnauthentication() throws Exception {
        URI newUrl = new URI(baseUrl + "/set_password");

        NewPassword newPassword = new NewPassword();
        newPassword.setNewPassword("newpassword");
        newPassword.setCurrentPassword("password");

        ResponseEntity<String> response = restTemplate.withBasicAuth("Nokodarov@gmail.com", "NoNewPassword")
                .postForEntity(newUrl, newPassword, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED).isNotNull();
    }

    @Test
    void getUserInfoTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/me");

        ResponseEntity<UserInfo> response = restTemplate.getForEntity(newUrl, UserInfo.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        Assertions.assertThat(response.getBody().getPhone()).isEqualTo("79616544133");
    }

    @Test
    void updateUserTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/me");
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setFirstName("FirstName");
        userUpdate.setLastName("LastName");
        userUpdate.setPhone("+79616544111");

        ResponseEntity<UserUpdate> response = restTemplate.exchange(newUrl, HttpMethod.PATCH, new HttpEntity<>(userUpdate), UserUpdate.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        Assertions.assertThat(response.getBody()).isEqualTo(userUpdate).isNotNull();
    }

    @Test
    void UploadAvatarTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/me/image");
        Resource imageResource = new ClassPathResource("/images/test-image.jpg");
        long imageSizeIn = imageResource.getFile().length();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(imageResource.getFile()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(newUrl, HttpMethod.PATCH, request, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();

        //get avatar
        URI getAvatarUrl = new URI(baseUrl + "/avatars/1");
        ResponseEntity<byte[]> responseGetAvatar = restTemplate.getForEntity(getAvatarUrl,byte[].class);
        Assertions.assertThat(responseGetAvatar.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        Assertions.assertThat(responseGetAvatar.getBody().length).isEqualTo(imageSizeIn);
    }

    @Test
    void getAvatarTestPositive() throws Exception{
        URI newUrl = new URI(baseUrl + "/avatars/1");
        ResponseEntity<byte[]> response = restTemplate.getForEntity(newUrl,byte[].class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
    }
}