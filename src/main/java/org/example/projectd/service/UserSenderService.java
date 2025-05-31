package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.entity.UserOutboxEvent;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSenderService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String TARGET_URL = "http://other-microservice/api/users";//////////////////////////////////////////////

    public Integer send(UserOutboxEvent event) {
        Map<String, Object> payload = event.getPayload();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                TARGET_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || !response.getBody().containsKey("id")) {
            throw new RuntimeException("Не удалось создать пользователя или получить его ID");
        }

        Integer userId = (Integer) response.getBody().get("id");

        log.info("Пользователь создан. ID: {}, email: {}", userId, payload.get("email"));
        return userId;
    }
}
