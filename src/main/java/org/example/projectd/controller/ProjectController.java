package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.annotation.Loggable;
import org.example.projectd.dto.ProjectDTO;
import org.example.projectd.dto.ProjectPageResponseDTO;
import org.example.projectd.dto.ProjectResponseDTO;
import org.example.projectd.entity.Project;
import org.example.projectd.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO projectDTO) {
        return new ResponseEntity<>(projectService.createProject(projectDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Integer projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
    }

    @Loggable
    @GetMapping
    public ResponseEntity<ProjectPageResponseDTO> getProjectsPaginated(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(projectService.getProjects(page, size));
    }

}
