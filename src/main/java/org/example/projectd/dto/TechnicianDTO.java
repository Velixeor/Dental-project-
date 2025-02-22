package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Technician;


@Builder
public record TechnicianDTO(
        Integer id,
        String name,
        Boolean isOlder
) {
    public static TechnicianDTO fromEntity(Technician technician) {
        return TechnicianDTO.builder()
                .id(technician.getId())
                .name(technician.getName())
                .isOlder(technician.getIsOlder())
                .build();
    }
}
