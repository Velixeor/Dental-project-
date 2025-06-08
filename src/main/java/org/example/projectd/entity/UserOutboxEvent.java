package org.example.projectd.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.projectd.service.JsonConverter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


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
    private Integer externalKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
