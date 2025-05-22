package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.PatternStage;


@Builder
public record PatternStageDTO(
        Integer id,
        Integer baseStageId,
        String baseStageName,
        Integer typeServiceId,
        Integer executionStepNumber
) {
    public static PatternStageDTO fromEntity(PatternStage patternStage) {
        return PatternStageDTO.builder()
                .id(patternStage.getId())
                .baseStageId(patternStage.getBaseStage().getId())
                .baseStageName(patternStage.getBaseStage().getName())
                .typeServiceId(patternStage.getTypeService() != null ? patternStage.getTypeService().getId() : null)
                .executionStepNumber(patternStage.getExecutionStepNumber())
                .build();
    }
}
