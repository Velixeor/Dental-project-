package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.BaseStage;


@Builder
public record BaseStageDTO(
        Integer id,
        String name,
        String comment
) {
    public static BaseStageDTO fromEntity(BaseStage baseStage) {
        return BaseStageDTO.builder()
                .id(baseStage.getId())
                .name(baseStage.getName())
                .comment(baseStage.getComment())
                .build();
    }
}
