package org.example.projectd.repository;


import org.example.projectd.entity.BaseStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BaseStageRepository extends JpaRepository<BaseStage, Integer> {
}
