package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.entity.Project;
import org.example.projectd.entity.ProjectStatus;
import org.example.projectd.entity.Stage;
import org.example.projectd.entity.Tooth;
import org.example.projectd.repository.ProjectRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectCompletionScheduler {

    private final ProjectRepository projectRepository;
    private final ToothService toothService;
    private final StageService stageService;
    private final ProjectService projectService;

    @Scheduled(fixedDelay = 60000)
    public void checkAndCompleteProjects() {
        List<Project> activeProjects = projectRepository.findByStatusIn(List.of(ProjectStatus.NEW, ProjectStatus.IN_PROGRESS));

        for (Project project : activeProjects) {
            try {
                if (areAllStagesOfProjectConfirmed(project)) {
                    project.setStatus(ProjectStatus.COMPLETED);
                    project.setDateCompleted(LocalDateTime.now());
                    projectService.update(project);
                    log.info("Project {} marked as COMPLETED by scheduler", project.getId());
                }
            } catch (Exception e) {
                log.error("Error processing project {}: {}", project.getId(), e.getMessage(), e);
            }
        }
    }

    private boolean areAllStagesOfProjectConfirmed(Project project) {
        List<Tooth> teeth = toothService.getByProjectId(project.getId());

        return teeth.stream()
                .map(Tooth::getService)
                .filter(Objects::nonNull)
                .allMatch(service -> {
                    List<Stage> stages = stageService.getByServiceId(service.getId());
                    return stages.stream().allMatch(Stage::getConfirmed);
                });
    }
}
