package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Skill;
import org.example.projectd.entity.Technician;
import org.example.projectd.entity.TechnicianSkill;

import java.util.List;
import java.util.stream.Collectors;


@Builder
public record TechnicianCreateDTO(
        String name,
        Boolean isOlder,
        String mail,
        String password,
        List<SkillPaymentDTO> skills
) {
    public Technician toEntity() {
        Technician technician = new Technician();
        technician.setName(this.name());
        technician.setIsOlder(this.isOlder() != null ? this.isOlder() : false);
        return technician;
    }

    public static TechnicianCreateDTO fromEntity(Technician technician) {
        return TechnicianCreateDTO.builder()
                .name(technician.getName())
                .isOlder(technician.getIsOlder())
                .build();
    }

    public List<TechnicianSkill> toTechnicianSkills(Technician technician, List<Skill> skillEntities) {

        return skills.stream()
                .map(skillPaymentDTO -> {
                    Skill skill = skillEntities.stream()
                            .filter(s -> s.getId().equals(skillPaymentDTO.skillId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Skill not found: " + skillPaymentDTO.skillId()));
                    return skillPaymentDTO.toEntity(technician, skill);
                })
                .collect(Collectors.toList());
    }
}
