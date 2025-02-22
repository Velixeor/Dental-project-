package org.example.projectd.dto;


import java.util.List;


public record ProjectPageResponseDTO(List<ShortProjectDTO> projects, int totalPages) {}
