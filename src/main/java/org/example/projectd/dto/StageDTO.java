package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Service;
import org.example.projectd.entity.Stage;
import org.example.projectd.entity.Technician;


@Builder
public record StageDTO(
            Integer id,
            String name,
            String comment,
            Boolean confirmed,
            Boolean sentForQualityControl,
            Integer serviceId,
            Integer technicianId,
            Integer executionStepNumber
) {
    public static StageDTO fromEntity(Stage stage) {
            return StageDTO.builder()
                    .id(stage.getId())
                    .name(stage.getName())
                    .comment(stage.getComment())
                    .confirmed(stage.getConfirmed())
                    .sentForQualityControl(stage.getSentForQualityControl())
                    .serviceId(stage.getService() != null ? stage.getService().getId() : null)
                    .technicianId(stage.getTechnician() != null ? stage.getTechnician().getId() : null)
                    .executionStepNumber(stage.getExecutionStepNumber())
                    .build();
    }
    public Stage toEntity(Service service, Technician technician) {
            Stage stage = new Stage();
            stage.setId(null);
            stage.setName(name);
            stage.setComment(comment);
            stage.setConfirmed(confirmed != null ? confirmed : false);
            stage.setSentForQualityControl(sentForQualityControl != null ? sentForQualityControl : false);
            stage.setService(service);
            stage.setTechnician(technician);
            stage.setExecutionStepNumber(executionStepNumber);
            return stage;
    }
}
