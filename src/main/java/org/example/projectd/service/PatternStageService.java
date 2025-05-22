package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.PatternStageDTO;
import org.example.projectd.repository.PatternStageRepository;
import org.springframework.stereotype.Service;

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
}
