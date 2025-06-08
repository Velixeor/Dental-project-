package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.BaseStageDTO;
import org.example.projectd.repository.BaseStageRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BaseStageService {
    private final BaseStageRepository baseStageRepository;

    public List<BaseStageDTO> getAllBaseStages(Long companyId) {
        return baseStageRepository.findAllByCompanyIdOrCommon(companyId.intValue()).stream()
                .map(BaseStageDTO::fromEntity)
                .toList();
    }
}
