package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Skill;
import org.example.projectd.entity.Technician;
import org.example.projectd.entity.TechnicianSkill;
import org.example.projectd.entity.TechnicianSkillId;

import java.math.BigDecimal;


@Builder
public record SkillPaymentDTO(
        Integer skillId,
        BigDecimal payment
) {
    public TechnicianSkill toEntity(Technician technician, Skill skill) {
        TechnicianSkill technicianSkill = new TechnicianSkill();
        technicianSkill.setId(new TechnicianSkillId(technician.getId(), skill.getId()));
        technicianSkill.setTechnician(technician);
        technicianSkill.setSkill(skill);
        technicianSkill.setPayment(payment);
        return technicianSkill;
    }
}
