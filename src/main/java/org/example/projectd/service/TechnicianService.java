package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.SkillPaymentDTO;
import org.example.projectd.dto.TechnicianCreateDTO;
import org.example.projectd.dto.TechnicianDTO;
import org.example.projectd.entity.Skill;
import org.example.projectd.entity.Technician;
import org.example.projectd.entity.TechnicianSkill;
import org.example.projectd.entity.UserOutboxEvent;
import org.example.projectd.repository.TechnicianRepository;
import org.example.projectd.repository.UserOutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final SkillService skillService;
    private final TechnicianSkillService technicianSkillService;
    private final UserOutboxEventRepository userOutboxEventRepository;

    public List<TechnicianDTO> getTechnician() {
        return technicianRepository.findAll().stream()
                .map(TechnicianDTO::fromEntity)
                .toList();
    }

    public Technician getTechnicianByUserID(Integer userID) {
        return technicianRepository.findByUserId(userID);
    }

    @Transactional
    public TechnicianDTO createTechnician(TechnicianCreateDTO technicianDTO, Long companyId) {
        Technician technician = saveTechnicianEntity(technicianDTO);
        List<Skill> skills = fetchSkillsFromDTO(technicianDTO);
        List<TechnicianSkill> technicianSkills = technicianDTO.toTechnicianSkills(technician, skills);
        technicianSkillService.saveAllTechnicianSkills(technicianSkills);
        saveUserOutboxEvent(technicianDTO, technician,companyId);
        return TechnicianDTO.fromEntity(technician);
    }

    private void saveUserOutboxEvent(TechnicianCreateDTO dto, Technician technician,Long companyID) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", technician.getName());
        payload.put("email", dto.mail());
        payload.put("password", dto.password());

        payload.put("companyId",companyID);
        payload.put("roleId", 1);

        UserOutboxEvent event = UserOutboxEvent.builder()
                .eventType("USER_CREATE")
                .externalKey(String.valueOf(technician.getId()))
                .payload(payload)
                .createdAt(LocalDateTime.now())
                .build();

        userOutboxEventRepository.save(event);
    }


    private Technician saveTechnicianEntity(TechnicianCreateDTO dto) {
        Technician technician = dto.toEntity();
        return technicianRepository.save(technician);
    }

    private List<Skill> fetchSkillsFromDTO(TechnicianCreateDTO dto) {
        List<Integer> skillIds = dto.skills().stream()
                .map(SkillPaymentDTO::skillId)
                .toList();
        return skillService.getSkillsByIds(skillIds);
    }

}
