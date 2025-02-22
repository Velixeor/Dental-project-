package org.example.projectd.repository;


import org.example.projectd.entity.Coupling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CouplingRepository extends JpaRepository<Coupling, Integer> {
    List<Coupling> findByProjectId(Integer projectId);
}
