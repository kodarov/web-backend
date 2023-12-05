package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.repositories.UserRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Import(UserRepository.class)
class AuthServiceImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ads")
            .withUsername("admin")
            .withPassword("admin");
    @Autowired
    private UserRepository userRepository;
    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();
    }
    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void setDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.driverClassName",postgreSQLContainer::getDriverClassName);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto=",()->"validate");
        //registry.add("spring.liquibase.change-log",()->"classpath:/db/changelog/db.changelog-master.yaml");
    }
    @Test
    void login() {
    }

    @Test
    void register() {
    }
}