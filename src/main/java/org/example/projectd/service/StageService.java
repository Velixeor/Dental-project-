package org.example.projectd.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.StageDTO;
import org.example.projectd.entity.PatternStage;
import org.example.projectd.entity.Stage;
import org.example.projectd.entity.Technician;
import org.example.projectd.repository.PatternStageRepository;
import org.example.projectd.repository.StageRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class StageService {
    private final StageRepository stageRepository;
    private final PatternStageRepository patternStageRepository;
    private final TechnicianService technicianService;
    public void saveAllStages(org.example.projectd.entity.Service service, Technician technician) {
        List<PatternStage> patternStages = patternStageRepository.findByTypeService(service.getTypeService());

        if (patternStages.isEmpty()) {
            log.warn("No pattern stages found for service type: {}", service.getTypeService().getName());
            return;
        }

        List<Stage> stages = patternStages.stream()
                .map(pattern -> createStageFromPattern(pattern, service, technician))
                .toList();

        stageRepository.saveAll(stages);
        log.info("{} stages successfully saved for service ID {}", stages.size(), service.getId());
    }

    private Stage createStageFromPattern(PatternStage pattern,
                                         org.example.projectd.entity.Service service,
                                         Technician technician) {
        StageDTO stageDTO = StageDTO.builder()
                .name(pattern.getBaseStage().getName())
                .comment(pattern.getBaseStage().getComment())
                .confirmed(false)
                .sentForQualityControl(false)
                .serviceId(service.getId())
                .technicianId(technician.getId())
                .build();

        return stageDTO.toEntity(service, technician);
    }

    public List<StageDTO> getStagesByUserId(Integer userId) {
        List<Stage> stages = stageRepository.findByTechnicianId(technicianService.getTechnicianByUserID(userId).getId());
        return stages.stream()
                .map(StageDTO::fromEntity)
                .toList();
    }

    public void updateStage(StageDTO dto) {
        Stage stage = stageRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Stage not found"));
        stage.setComment(dto.comment());
        stage.setSentForQualityControl(true);
        stageRepository.save(stage);
    }
}
