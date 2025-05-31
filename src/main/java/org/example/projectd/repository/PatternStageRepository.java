package org.example.projectd.repository;


import org.example.projectd.entity.PatternStage;
import org.example.projectd.entity.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatternStageRepository extends JpaRepository<PatternStage, Integer> {
    List<PatternStage> findByTypeService(TypeService typeService);
    List<PatternStage> findByTypeServiceIdOrderByExecutionStepNumber(Integer typeServiceId);
    @Modifying
    @Query("UPDATE PatternStage ps SET ps.executionStepNumber = :number WHERE ps.id = :id")
    void updateExecutionStepNumber(@Param("id") Integer id, @Param("number") Integer number);
    @Modifying
    @Query(value = """
    INSERT INTO project_designer.pattern_stage (base_stage_id, type_service_id, execution_step_number)
    VALUES (:baseStageId, :typeServiceId, :executionStepNumber)
    """, nativeQuery = true)
    void insertPatternStage(@Param("baseStageId") Integer baseStageId,
                            @Param("typeServiceId") Integer typeServiceId,
                            @Param("executionStepNumber") Integer executionStepNumber);

}
