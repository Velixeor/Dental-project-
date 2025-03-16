package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Project;
import org.example.projectd.entity.Service;
import org.example.projectd.entity.Tooth;


@Builder
public record ToothDTO(
        Integer number,
        String colorForScheme,
        ServiceDTO serviceDTO
) {
    public Tooth toEntity(Project project, Service service) {
        return new Tooth(null, project, colorForScheme, number, service);
    }

    public static ToothDTO fromEntity(Tooth tooth) {
        return ToothDTO.builder()
                .number(tooth.getNumber())
                .colorForScheme(tooth.getColorForScheme())
                .serviceDTO(tooth.getService() != null ? ServiceDTO.fromEntity(tooth.getService()) : null)
                .build();
    }
}
