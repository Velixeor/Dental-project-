package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.TechnicianDTO;
import org.example.projectd.repository.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final TechnicianRepository technicianRepository;

    public List<TechnicianDTO> getTechnician(){
        return technicianRepository.findAll().stream()
                .map(TechnicianDTO::fromEntity)
                .toList();
    }
}
