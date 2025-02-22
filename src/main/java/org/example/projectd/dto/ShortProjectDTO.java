package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Project;

import java.time.LocalDateTime;


@Builder
public record ShortProjectDTO(
        Integer id,
        String customer,
        String patient,
        LocalDateTime dateCreate,
        String status,
        LocalDateTime dateCompleted
) {
    public static ShortProjectDTO fromEntity(Project project) {
        return ShortProjectDTO.builder()
                .id(project.getId())
                .customer(project.getCustomer().getName())
                .patient(project.getPatient())
                .dateCreate(project.getDateCreate())
                .status(project.getStatus().name())
                .dateCompleted(project.getDateCompleted())
                .build();
    }
}
