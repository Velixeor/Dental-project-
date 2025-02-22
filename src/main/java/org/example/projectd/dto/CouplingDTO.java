package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Coupling;
import org.example.projectd.entity.Project;


@Builder
public record CouplingDTO(
        Integer numberTooth1,
        Integer numberTooth2
) {
    public Coupling toEntity(Project project) {
        return new Coupling(null, project, numberTooth1, numberTooth2);
    }

    public static CouplingDTO fromEntity(Coupling coupling) {
        return CouplingDTO.builder()
                .numberTooth1(coupling.getNumberTooth1())
                .numberTooth2(coupling.getNumberTooth2())
                .build();
    }
}
