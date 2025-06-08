package org.example.projectd.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pattern_stage", schema = "project_designer")
public class PatternStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_stage_id")
    private BaseStage baseStage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_service_id")
    private TypeService typeService;

    @Column(name = "execution_step_number")
    private Integer executionStepNumber;

    @Column(name = "company_id")
    private Integer companyId;
}
