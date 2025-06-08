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

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class StageService {
    private final StageRepository stageRepository;
    private final PatternStageRepository patternStageRepository;
    private final TechnicianService technicianService;


    record ProjectServiceKey(Integer serviceId, Integer projectId) {
    }

    public List<StageDTO> getPendingStages(Long companyId) {
        return stageRepository.findPendingStagesByCompanyId(companyId)
                .stream()
                .map(StageDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void saveAllStages(org.example.projectd.entity.Service service, Technician technician) {

        List<PatternStage> patternStages = patternStageRepository.findByTypeServiceIdAndCompanyId(
                service.getTypeService().getId(),
                technician.getCompanyId().intValue()
        );

        if (patternStages.isEmpty()) {
            log.warn("No pattern stages found for type '{}' ",
                    service.getTypeService().getName()
                    );
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
                .executionStepNumber(pattern.getExecutionStepNumber())
                .build();

        return stageDTO.toEntity(service, technician);
    }

    public List<StageDTO> getStagesByUserId(Integer userId) {
        Integer technicianId = technicianService.getTechnicianByUserID(userId).getId();
        List<Stage> technicianStages = stageRepository.findByTechnicianId(technicianId);

        Map<Integer, Integer> stageToProjectIdMap = stageRepository.findStageIdToProjectIdByTechnician(technicianId).stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Integer) row[1],
                        (existing, replacement) -> existing
                ));

        Map<ProjectServiceKey, List<Stage>> stagesByProjectService = groupStagesByProjectAndService(technicianStages, stageToProjectIdMap);

        List<Stage> nextStages = stagesByProjectService.values().stream()
                .map(this::findNextStageForService)
                .filter(Objects::nonNull)
                .toList();

        return nextStages.stream()
                .map(StageDTO::fromEntity)
                .toList();
    }

    private Map<ProjectServiceKey, List<Stage>> groupStagesByProjectAndService(
            List<Stage> stages,
            Map<Integer, Integer> stageToProjectIdMap) {

        return stages.stream()
                .map(stage -> {
                    Integer stageId = stage.getId();
                    Integer projectId = stageToProjectIdMap.get(stageId);
                    return new AbstractMap.SimpleEntry<>(
                            new ProjectServiceKey(stage.getService().getId(), projectId),
                            stage
                    );
                })
                .filter(entry -> entry.getKey().projectId() != null)
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    private Stage findNextStageForService(List<Stage> stages) {
        stages.sort(Comparator.comparing(Stage::getExecutionStepNumber));
        for (int i = 0; i < stages.size(); i++) {
            Stage current = stages.get(i);
            if (!current.getConfirmed() && allPreviousStagesConfirmed(stages, i)) {
                return current;
            }
        }
        return null;
    }

    private boolean allPreviousStagesConfirmed(List<Stage> stages, int index) {
        for (int j = 0; j < index; j++) {
            if (!stages.get(j).getConfirmed()) {
                return false;
            }
        }
        return true;
    }

    public StageDTO updateStage(StageDTO dto) {
        Stage stage = stageRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Stage not found"));
        stage.setComment(dto.comment());
        stage.setSentForQualityControl(true);
        stage.setConfirmed(dto.confirmed());
        Stage updatestage = stageRepository.save(stage);
        return StageDTO.fromEntity(updatestage);
    }

    public List<Stage> getByServiceId(Integer serviceId) {
        return stageRepository.findByService_Id(serviceId);
    }


}
