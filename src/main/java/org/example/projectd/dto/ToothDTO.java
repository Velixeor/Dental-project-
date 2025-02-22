package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Project;
import org.example.projectd.entity.Service;
import org.example.projectd.entity.Tooth;


@Builder
public record ToothDTO(
        Integer number,
        String colorForScheme,
        Integer serviceId
) {
    public Tooth toEntity(Project project, Service service) {
        return new Tooth(null, project, colorForScheme, number, service);
    }

    public static ToothDTO fromEntity(Tooth tooth) {
        return ToothDTO.builder()
                .number(tooth.getNumber())
                .colorForScheme(tooth.getColorForScheme())
                .serviceId(tooth.getService() != null ? tooth.getService().getId() : null)
                .build();
    }
}
