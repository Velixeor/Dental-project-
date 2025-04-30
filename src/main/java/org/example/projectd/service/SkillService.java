package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.SkillDTO;
import org.example.projectd.entity.Skill;
import org.example.projectd.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(SkillDTO::fromEntity)
                .toList();
    }
    public List<Skill> getSkillsByIds(List<Integer> skillIds) {
        return skillRepository.findAllById(skillIds);
    }
}
