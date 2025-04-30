package org.example.projectd.repository;


import org.example.projectd.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StageRepository  extends JpaRepository<Stage, Integer> {
    List<Stage> findByTechnicianId(Integer technicianId);
}
