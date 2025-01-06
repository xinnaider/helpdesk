package com.example.userservice;

import com.example.userservice.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = UserServiceApplication.class)
public class UserServiceApplicationTests {

    static DockerImageName timescaleImage = DockerImageName
            .parse("timescale/timescaledb:2.12.1-pg15")
            .asCompatibleSubstituteFor("postgres");

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(timescaleImage)
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @BeforeAll
    static void setUp() {
        postgresContainer.start();
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @AfterAll
    static void tearDown() {
        postgresContainer.stop();
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createUserTest() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.USER);

        ResponseEntity<User> response = restTemplate.postForEntity("/api/users", user, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Test User");
        assertThat(response.getBody().getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    void getUsersTest() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("/api/users", User[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(0); // Confirma que o array não é nulo
    }
}
