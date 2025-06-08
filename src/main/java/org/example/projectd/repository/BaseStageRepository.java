package org.example.projectd.repository;


import org.example.projectd.entity.BaseStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BaseStageRepository extends JpaRepository<BaseStage, Integer> {
    @Query("""
    SELECT b FROM BaseStage b 
    WHERE b.companyId = :companyId OR b.companyId IS NULL
""")
    List<BaseStage> findAllByCompanyIdOrCommon(@Param("companyId") Integer companyId);
}
