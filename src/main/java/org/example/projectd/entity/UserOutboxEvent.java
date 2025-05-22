package org.example.projectd.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.projectd.service.JsonConverter;

import java.time.LocalDateTime;
import java.util.Map;


@Entity
@Table(name = "user_outbox_event", schema = "project_designer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "external_key", nullable = false)
    private String externalKey;

    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> payload;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
