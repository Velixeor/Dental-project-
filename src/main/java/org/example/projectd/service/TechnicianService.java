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
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final SkillService skillService;
    private final TechnicianSkillService technicianSkillService;
    private final UserOutboxEventRepository userOutboxEventRepository;

    public List<TechnicianDTO> getTechnician(Long companyId) {
        return technicianRepository.findAllByCompanyId(companyId.intValue()).stream()
                .map(TechnicianDTO::fromEntity)
                .toList();
    }

    public Technician getTechnicianByUserID(Integer userID) {
        return technicianRepository.findByUserId(userID);
    }

    @Transactional
    public TechnicianDTO createTechnician(TechnicianCreateDTO technicianDTO, Long companyId) {
        Technician technician = saveTechnicianEntity(technicianDTO);
        technician.setCompanyId(companyId.intValue());
        List<Skill> skills = fetchSkillsFromDTO(technicianDTO);
        List<TechnicianSkill> technicianSkills = technicianDTO.toTechnicianSkills(technician, skills);
        technicianSkillService.saveAllTechnicianSkills(technicianSkills);
        saveUserOutboxEvent(technicianDTO, technician, companyId);
        return TechnicianDTO.fromEntity(technician);
    }

    private void saveUserOutboxEvent(TechnicianCreateDTO dto, Technician technician, Long companyID) {
        UserOutboxEvent event = UserOutboxEvent.builder()
                .eventType("USER_CREATE")
                .externalKey(technician.getId().intValue())
                .name(technician.getName())
                .email(dto.mail())
                .password(dto.password()) // Убедись, что пароль безопасно хранится!
                .companyId(companyID.intValue())
                .roleId(1)
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
