package org.example.projectd.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.*;
import org.example.projectd.entity.*;
import org.example.projectd.repository.CustomerRepository;
import org.example.projectd.repository.ProjectRepository;
import org.example.projectd.repository.TechnicianRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ToothService toothService;
    private final CouplingService couplingService;
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;

    @Transactional
    public Project createProject(ProjectDTO projectDTO, Integer companyId) {
        Customer customer = customerRepository.findById(projectDTO.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + projectDTO.customerId()));
        Technician technician = technicianRepository.findById(projectDTO.technicianId())
                .orElseThrow(() -> new EntityNotFoundException("Technician not found with id: " + projectDTO.technicianId()));
        Project project = projectRepository.save(projectDTO.toEntity(customer, technician,companyId));
        toothService.saveTeeth(projectDTO.teeth(), project);
        couplingService.saveCouplings(projectDTO.couplings(), project);
        return project;
    }

    public ProjectResponseDTO getProjectById(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Проект не найден"));
        return ProjectResponseDTO.fromEntity(project,
                couplingService.getAllCouplingByProjectId(projectId), toothService.getAllTeethByProjectId(projectId));
    }

    public ProjectPageResponseDTO getProjects(Long companyId, int page, int size) {
        Page<Project> projectsPage = projectRepository.findAllByCompanyId(
                companyId.intValue(), PageRequest.of(page - 1, size)
        );
        List<ShortProjectDTO> shortProjectDTOs = projectsPage
                .map(ShortProjectDTO::fromEntity)
                .getContent();

        return new ProjectPageResponseDTO(shortProjectDTOs, projectsPage.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ProjectPageResponseDTO searchProjects(Long companyId, String query, List<String> filters, int page, int size) {
        Specification<Project> spec = buildSearchSpecification(query, filters)
                .and((root, query1, cb) -> cb.equal(root.get("company").get("id"), companyId));

        Page<Project> resultPage = fetchProjectsPage(spec, page, size);
        List<ShortProjectDTO> shortProjects = mapToShortDto(resultPage);
        return buildPageResponse(shortProjects, resultPage.getTotalPages());
    }
    private Specification<Project> buildSearchSpecification(String query, List<String> filters) {
        return ProjectSpecifications.searchByFields(query, filters);
    }

    private Page<Project> fetchProjectsPage(Specification<Project> spec, int page, int size) {
        return projectRepository.findAll(spec, PageRequest.of(page - 1, size));
    }

    private List<ShortProjectDTO> mapToShortDto(Page<Project> projects) {
        return projects.getContent().stream()
                .map(ShortProjectDTO::fromEntity)
                .toList();
    }

    private ProjectPageResponseDTO buildPageResponse(List<ShortProjectDTO> projects, int totalPages) {
        return new ProjectPageResponseDTO(projects, totalPages);
    }

    public Project update(Project project) {
        if (project.getId() == null) {
            throw new IllegalArgumentException("Cannot update project without ID");
        }

        Project existing = projectRepository.findById(project.getId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        existing.setStatus(project.getStatus());
        existing.setDateCompleted(project.getDateCompleted());

        return projectRepository.save(existing);
    }

}
