package com.example.ticketservice.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceIntegration {
    @Value("${user-service.url}")
    private String USER_SERVICE_URL;

    @Autowired
    private RestTemplate restTemplate;

    public boolean existingId(Long userId) {
        try {
            ResponseEntity<Void> response = restTemplate.getForEntity(String.format("%susers/%d", USER_SERVICE_URL, userId), Void.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}

