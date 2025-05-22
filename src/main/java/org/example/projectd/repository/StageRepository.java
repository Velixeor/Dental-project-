package org.example.projectd.repository;


import org.example.projectd.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StageRepository  extends JpaRepository<Stage, Integer> {
    List<Stage> findByTechnicianId(Integer technicianId);
    @Query("""
    SELECT s
    FROM Stage s
    JOIN Service serv ON s.service = serv
    JOIN Tooth t ON t.service = serv
    JOIN Project p ON t.project = p
    WHERE s.technician.id = :technicianId
""")
    List<Stage> findByTechnicianWithProject(Integer technicianId);

    @Query("""
    SELECT s.id, p.id
    FROM Stage s
    JOIN Service serv ON s.service = serv
    JOIN Tooth t ON t.service = serv
    JOIN Project p ON t.project = p
    WHERE s.technician.id = :technicianId
""")
    List<Object[]> findStageIdToProjectIdByTechnician(Integer technicianId);

    @Query("""
    SELECT s
    FROM Stage s
    WHERE s.sentForQualityControl = true AND s.confirmed = false
""")
    List<Stage> findPendingStages();
}
