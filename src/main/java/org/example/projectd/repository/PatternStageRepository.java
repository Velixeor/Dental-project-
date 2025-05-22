package org.example.projectd.repository;


import org.example.projectd.entity.PatternStage;
import org.example.projectd.entity.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatternStageRepository extends JpaRepository<PatternStage, Integer> {
    List<PatternStage> findByTypeService(TypeService typeService);
    List<PatternStage> findByTypeServiceIdOrderByExecutionStepNumber(Integer typeServiceId);
}
