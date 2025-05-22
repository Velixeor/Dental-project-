package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Material;
import org.example.projectd.entity.Service;
import org.example.projectd.entity.TypeService;

import java.math.BigDecimal;


@Builder
public record ServiceDTO(
        Integer id,
        Double servicePrice,
        Integer typeServiceId,
        Integer materialId,
        String materialName,
        String serviceName
) {
    public static ServiceDTO fromEntity(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .servicePrice(service.getServicePrice().doubleValue())
                .typeServiceId(service.getTypeService() != null ? service.getTypeService().getId() : null)
                .materialId(service.getMaterial() != null ? service.getMaterial().getId() : null)
                .serviceName(service.getTypeService().getName())
                .materialName(service.getMaterial().getName())
                .build();
    }

    public Service toEntity(TypeService typeService, Material material) {
        return new Service(
                null,
                typeService,
                material,
                BigDecimal.valueOf(servicePrice)
        );
    }
}
