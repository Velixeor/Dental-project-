package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.TechnicianCreateDTO;
import org.example.projectd.dto.TechnicianDTO;
import org.example.projectd.service.TechnicianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project/technician")
@RequiredArgsConstructor
public class TechnicianController {
    private final TechnicianService technicianService;

    @GetMapping("")
    public ResponseEntity<List<TechnicianDTO>> getTechnician() {
        return new ResponseEntity<>(technicianService.getTechnician(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TechnicianDTO> createTechnician(@RequestBody TechnicianCreateDTO technicianCreateDTO) {
        return new ResponseEntity<>(technicianService.createTechnician(technicianCreateDTO), HttpStatus.CREATED);
    }
}
