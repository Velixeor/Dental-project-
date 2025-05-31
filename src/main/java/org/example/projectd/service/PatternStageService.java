package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.PatternStageDTO;
import org.example.projectd.repository.PatternStageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PatternStageService {
    private final PatternStageRepository patternStageRepository;

    public List<PatternStageDTO> getPatternStagesByTypeService(Integer typeServiceId) {
        return patternStageRepository.findByTypeServiceIdOrderByExecutionStepNumber(typeServiceId).stream()
                .map(PatternStageDTO::fromEntity)
                .toList();
    }

    @Transactional
    public void updateExecutionOrder(List<PatternStageDTO> stages) {
        for (PatternStageDTO dto : stages) {
            if (dto.id() == null) {
                patternStageRepository.insertPatternStage(
                        dto.baseStageId(), dto.typeServiceId(), dto.executionStepNumber()
                );
            } else {
                patternStageRepository.updateExecutionStepNumber(dto.id(), dto.executionStepNumber());
            }
        }
    }

}
