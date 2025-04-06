package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.ServiceDTO;
import org.example.projectd.dto.TechnicianDTO;
import org.example.projectd.entity.Material;
import org.example.projectd.entity.Technician;
import org.example.projectd.entity.TypeService;
import org.example.projectd.repository.MaterialRepository;
import org.example.projectd.repository.ServiceRepository;
import org.example.projectd.repository.TypeServiceRepository;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final MaterialRepository materialRepository;
    private final TypeServiceRepository typeServiceRepository;
    private final StageService stageService;

    public org.example.projectd.entity.Service saveServiceAndCreateStage(ServiceDTO serviceDTO, Technician technician) {
        TypeService typeService = typeServiceRepository.findById(serviceDTO.typeServiceId())
                .orElseThrow(() -> new RuntimeException("TypeService not found"));

        Material material = materialRepository.findById(serviceDTO.materialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        org.example.projectd.entity.Service service=serviceRepository.save(serviceDTO.toEntity(typeService, material));
        stageService.saveAllStages(service,technician);
        return service;
    }
}
