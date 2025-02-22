package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Project;

import java.util.List;


@Builder
public record ProjectResponseDTO(
        Integer id,
        String patient,
        String comment,
        String typeOfAntagonist,
        String status,
        String teethColor,
        Boolean isDeleted,
        CustomerDTO customer,
        TechnicianDTO technician,
        List<CouplingDTO> couplings,
        List<ToothDTO> teeth
) {
    public static ProjectResponseDTO fromEntity(Project project,
                                                List<CouplingDTO> couplings,List<ToothDTO>teeth) {
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .patient(project.getPatient())
                .comment(project.getComment())
                .typeOfAntagonist(project.getTypeOfAntagonist().name())
                .status(project.getStatus().name())
                .teethColor(project.getTeethColor().name())
                .isDeleted(project.getIsDeleted())
                .customer(CustomerDTO.fromEntity(project.getCustomer()))
                .technician(TechnicianDTO.fromEntity(project.getTechnician()))
                .couplings(couplings)
                .teeth(teeth)
                .build();
    }
}
