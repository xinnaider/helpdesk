package com.example.ticketservice;

import com.example.ticketservice.model.Ticket;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
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

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.cloud.discovery.enabled=false",
                "spring.cloud.config.discovery.enabled=false"
        }
)
@Testcontainers
@ContextConfiguration(classes = TicketServiceApplication.class)
public class TicketServiceApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    static DockerImageName timescaleImage = DockerImageName
            .parse("timescale/timescaledb:2.12.1-pg15")
            .asCompatibleSubstituteFor("postgres");

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(timescaleImage)
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    static WireMockServer wireMockServer;

    @BeforeAll
    static void setUp() {
        postgresContainer.start();
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
        System.setProperty("user-service.url", "http://localhost:8081/");

        wireMockServer = new WireMockServer(options().port(8081));
        wireMockServer.start();

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)));
    }

    @AfterAll
    static void tearDown() {
        postgresContainer.stop();
        wireMockServer.stop();
    }

    @Test
    void createTicketTest() {
        Ticket ticket = new Ticket();
        ticket.setTitle("Test Ticket");
        ticket.setDescription("Integration Test");
        ticket.setPriority(Ticket.Priority.MEDIUM);
        ticket.setStatus(Ticket.Status.OPEN);
        ticket.setUserId(1L);

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/api/tickets", ticket, Ticket.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getTitle()).isEqualTo("Test Ticket");
    }

    @Test
    void getTicketsTest() {
        ResponseEntity<Ticket[]> response = restTemplate.getForEntity("/api/tickets", Ticket[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(0);
    }
}
