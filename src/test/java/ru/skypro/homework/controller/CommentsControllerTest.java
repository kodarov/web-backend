package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.dto.CommentCreateOrUpdate;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentsControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private CommentsController commentsController;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(commentsController).isNotNull();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/ads";
        restTemplate = restTemplate.withBasicAuth("kodarov@gmail.com", "password");
    }

    @Test
    void addCommentTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1/comments");
        CommentCreateOrUpdate comCrOrUpd = new CommentCreateOrUpdate();
        comCrOrUpd.setText("Test comment");
        ResponseEntity<CommentDto> response = restTemplate.postForEntity(newUrl, comCrOrUpd, CommentDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        assertThat(response.getBody()).isInstanceOf(CommentDto.class);

    }

    @Test
    void getCommentsTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1/comments");
        ResponseEntity<Comments> response = restTemplate.getForEntity(newUrl, Comments.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
        assertThat(response.getBody()).isInstanceOf(Comments.class);
    }

    @Test
    void updateCommentTestPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/1/comments/1");
        CommentCreateOrUpdate comCrOrUpd = new CommentCreateOrUpdate();
        comCrOrUpd.setText("Test comment");
        ResponseEntity<CommentDto> response = restTemplate.exchange(
                newUrl,
                HttpMethod.PATCH,
                new HttpEntity<>(comCrOrUpd),
                CommentDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK).isNotNull();
    }

    @Test
    void delCommentPositive() throws Exception {
        URI newUrl = new URI(baseUrl + "/2/comments/2");
        ResponseEntity<String> response = restTemplate
                .exchange(
                        newUrl,
                        HttpMethod.DELETE,
                        HttpEntity.EMPTY,
                        String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}