package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.annotation.Loggable;
import org.example.projectd.dto.ProjectDTO;
import org.example.projectd.dto.ProjectPageResponseDTO;
import org.example.projectd.dto.ProjectResponseDTO;
import org.example.projectd.dto.ShortProjectDTO;
import org.example.projectd.entity.Project;
import org.example.projectd.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO projectDTO,
                                                 @RequestHeader("X-Company-Id") String companyId) {

        return new ResponseEntity<>(projectService.createProject(projectDTO,Integer.parseInt(companyId)), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Integer projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProjectPageResponseDTO> getProjectsPaginated(
            @RequestHeader("X-Company-Id") String companyId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(projectService.getProjects(Long.parseLong(companyId), page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<ProjectPageResponseDTO> searchProjectsPaginated(
            @RequestHeader("X-Company-Id") String companyId,
            @RequestParam String query,
            @RequestParam List<String> filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(projectService.searchProjects(Long.parseLong(companyId), query, filter, page, size));
    }
}
