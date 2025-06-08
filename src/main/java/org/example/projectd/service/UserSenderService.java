package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.entity.UserOutboxEvent;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserSenderService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String TARGET_URL = "http://localhost:8083/api/users"; // URL изменить при необходимости

    public Integer send(UserOutboxEvent event) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", event.getName());
        payload.put("mail", event.getEmail());
        payload.put("password", event.getPassword());
        payload.put("companyId", event.getCompanyId());
        payload.put("roleId", event.getRoleId());

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

        log.info("Пользователь создан. ID: {}, email: {}", userId, event.getEmail());
        return userId;
    }
}
