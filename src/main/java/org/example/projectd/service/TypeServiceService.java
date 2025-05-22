package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.TypeServiceDTO;
import org.example.projectd.repository.TypeServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TypeServiceService {
    private final TypeServiceRepository typeServiceRepository;

    public Page<TypeServiceDTO> getAllTypeServices(Pageable pageable) {
        return typeServiceRepository.findAll(pageable)
                .map(TypeServiceDTO::fromEntity);
    }
}
