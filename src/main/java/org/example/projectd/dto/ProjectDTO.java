package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.*;


import java.time.LocalDateTime;
import java.util.List;


@Builder
public record ProjectDTO(
        String patient,
        String comment,
        String typeOfAntagonist,
        String status,
        String teethColor,
        Boolean isDeleted,
        Integer customerId,
        Integer technicianId,
        List<ToothDTO> teeth,
        List<CouplingDTO> couplings

) {
    public Project toEntity(Customer customer, Technician technician) {
        return new Project(
                null,
                LocalDateTime.now(),
                null,
                patient,
                comment,
                customer,
                TypeOfAntagonist.valueOf(typeOfAntagonist),
                ProjectStatus.valueOf(status),
                isDeleted,
                TeethColor.valueOf(teethColor),
                technician
        );
    }
}
