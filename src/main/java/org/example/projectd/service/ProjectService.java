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
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ToothService toothService;
    private final CouplingService couplingService;
    private final CustomerRepository customerRepository;
    private final TechnicianRepository technicianRepository;

    public Project createProject(ProjectDTO projectDTO) {
        Customer customer = customerRepository.findById(projectDTO.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + projectDTO.customerId()));
        Technician technician = technicianRepository.findById(projectDTO.technicianId())
                .orElseThrow(() -> new EntityNotFoundException("Technician not found with id: " + projectDTO.technicianId()));
        Project project = projectRepository.save(projectDTO.toEntity(customer, technician));
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

    public ProjectPageResponseDTO getProjects(int page, int size){
        Page<Project> projectsPage = projectRepository.findAll(PageRequest.of(page - 1, size));
        List<ShortProjectDTO> shortProjectDTOs = projectsPage.map(ShortProjectDTO::fromEntity).getContent();
        return new ProjectPageResponseDTO(shortProjectDTOs, projectsPage.getTotalPages());
    }


}
