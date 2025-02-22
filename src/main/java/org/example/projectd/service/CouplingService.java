package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.CouplingDTO;
import org.example.projectd.dto.ToothDTO;
import org.example.projectd.entity.Coupling;
import org.example.projectd.entity.Project;
import org.example.projectd.entity.Tooth;
import org.example.projectd.repository.CouplingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CouplingService {
    private final CouplingRepository couplingRepository;

    public List<Coupling> saveCouplings(List<CouplingDTO> couplingDTOs, Project project) {
        if (couplingDTOs == null || couplingDTOs.isEmpty()) {
            return List.of();
        }

        List<Coupling> couplings = couplingDTOs.stream()
                .map(dto -> dto.toEntity(project))
                .toList();

        return couplingRepository.saveAll(couplings);
    }
    public List<CouplingDTO> getAllCouplingByProjectId(Integer projectId){
        List<Coupling> couplings = couplingRepository.findByProjectId(projectId);
        return couplings.stream().map(CouplingDTO::fromEntity).collect(Collectors.toList());
    }
}
