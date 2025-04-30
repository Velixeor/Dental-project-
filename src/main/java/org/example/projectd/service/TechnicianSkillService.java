package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.entity.TechnicianSkill;
import org.example.projectd.repository.TechnicianSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TechnicianSkillService {
    private final TechnicianSkillRepository technicianSkillRepository;

    public List<TechnicianSkill> saveAllTechnicianSkills(List<TechnicianSkill> technicianSkills) {
        return technicianSkillRepository.saveAll(technicianSkills);
    }
}
