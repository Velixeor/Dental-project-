package org.example.projectd.dto;


import lombok.Builder;


@Builder
public record ServiceDTO(
        Integer id,
        String name,
        Double servicePrice,
        String typeService
) {}
