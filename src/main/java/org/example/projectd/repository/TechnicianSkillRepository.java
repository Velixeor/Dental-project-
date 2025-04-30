package org.example.projectd.repository;


import org.example.projectd.entity.TechnicianSkill;
import org.example.projectd.entity.TechnicianSkillId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TechnicianSkillRepository extends JpaRepository<TechnicianSkill, TechnicianSkillId> {
}
