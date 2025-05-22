package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.entity.UserOutboxEvent;
import org.example.projectd.repository.UserOutboxEventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class UserOutboxScheduler {
    private final UserOutboxEventRepository userOutboxEventRepository;
    private final UserSenderService userSenderService;

    @Scheduled(fixedDelay = 10000)
    public void processUserOutbox() {
        List<UserOutboxEvent> events = userOutboxEventRepository.findAllByProcessedAtIsNull();

        for (UserOutboxEvent event : events) {
            try {
                Integer userId = userSenderService.send(event);
                event.setProcessedAt(LocalDateTime.now());
                userOutboxEventRepository.save(event);
            } catch (Exception e) {
                log.error("Ошибка при отправке user outbox event: {}", event.getId(), e);
            }
        }
    }
}
