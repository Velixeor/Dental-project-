package org.example.projectd.repository;


import org.example.projectd.entity.Tooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToothRepository extends JpaRepository<Tooth, Integer> {
    List<Tooth> findByProjectId(Integer projectId);
    Tooth findByService_Id(Integer serviceId);
}
