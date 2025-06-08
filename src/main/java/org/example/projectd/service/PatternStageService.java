package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.PatternStageDTO;
import org.example.projectd.entity.PatternStage;
import org.example.projectd.repository.PatternStageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatternStageService {
    private final PatternStageRepository patternStageRepository;

    public List<PatternStageDTO> getPatternStagesByTypeService(Integer typeServiceId, Long companyId) {
        return patternStageRepository
                .findByTypeServiceIdAndCompanyIdOrderByExecutionStepNumber(typeServiceId, companyId.intValue())
                .stream()
                .map(PatternStageDTO::fromEntity)
                .toList();
    }


    @Transactional
    public void updateExecutionOrder(List<PatternStageDTO> stages,Long companyId) {
        if (stages.isEmpty()) return;

        Integer typeServiceId = stages.get(0).typeServiceId();


        List<PatternStage> existingStages = patternStageRepository.findByTypeServiceIdAndCompanyId(typeServiceId,companyId.intValue());
        Set<Integer> incomingIds = stages.stream()
                .map(PatternStageDTO::id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        for (PatternStage stage : existingStages) {
            if (!incomingIds.contains(stage.getId())) {
                patternStageRepository.deleteById(stage.getId());
            }
        }


        for (PatternStageDTO dto : stages) {
            if (dto.id() == null) {
                patternStageRepository.insertPatternStage(
                        dto.baseStageId(),
                        dto.typeServiceId(),
                        dto.executionStepNumber(),
                        companyId
                );
            } else {
                patternStageRepository.updateExecutionStepNumber(dto.id(), dto.executionStepNumber());
            }
        }
    }

}
