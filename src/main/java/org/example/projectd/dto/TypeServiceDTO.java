package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.TypeService;


@Builder
public record TypeServiceDTO(
        Integer id,
        String name
) {
    public static TypeServiceDTO fromEntity(TypeService typeService) {
        return TypeServiceDTO.builder()
                .id(typeService.getId())
                .name(typeService.getName())
                .build();
    }
}
