package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.ToothDTO;
import org.example.projectd.entity.Project;
import org.example.projectd.entity.Tooth;
import org.example.projectd.repository.ToothRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ToothService {
    private final ToothRepository toothRepository;

    public List<Tooth> saveTeeth(List<ToothDTO> toothDTOs, Project project) {
        if (toothDTOs == null || toothDTOs.isEmpty()) {
            return List.of();
        }

        List<Tooth> teeth = toothDTOs.stream()
                .map(dto -> dto.toEntity(project, null))
                .toList();

        return toothRepository.saveAll(teeth);
    }

    public List<ToothDTO> getAllTeethByProjectId(Integer projectId){
        List<Tooth> teeth = toothRepository.findByProjectId(projectId);
        return teeth.stream().map(ToothDTO::fromEntity).collect(Collectors.toList());
    }

}
