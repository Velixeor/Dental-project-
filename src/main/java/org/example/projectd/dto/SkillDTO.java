package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Skill;


@Builder
public record SkillDTO(
        Integer id,
        String name
) {
    public static SkillDTO fromEntity(Skill skill) {
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }

    public Skill toEntity() {
        Skill skill = new Skill();
        skill.setId(id);
        skill.setName(name);
        return skill;
    }
}
