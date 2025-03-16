package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.TechnicianDTO;
import org.example.projectd.service.TechnicianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/technician")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TechnicianController {
    private final TechnicianService technicianService;

    @GetMapping("")
    public ResponseEntity<List<TechnicianDTO>> getTechnician() {
        return new ResponseEntity<>(technicianService.getTechnician(), HttpStatus.OK);
    }
}
