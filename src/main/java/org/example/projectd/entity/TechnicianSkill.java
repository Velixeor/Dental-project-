package org.example.projectd.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "technician_skill", schema = "project_designer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianSkill {

    @EmbeddedId
    private TechnicianSkillId id = new TechnicianSkillId();

    @ManyToOne
    @MapsId("technicianId")
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Column(nullable = false)
    private BigDecimal payment;
}
