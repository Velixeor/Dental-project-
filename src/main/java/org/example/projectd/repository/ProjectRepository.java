package org.example.projectd.repository;


import org.example.projectd.entity.Project;
import org.example.projectd.entity.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    Page<Project> findAll(Pageable pageable);
    Page<Project> findAllByCompanyId(Integer companyId,Pageable pageable);
    List<Project> findByStatusIn(List<ProjectStatus> statuses);
}
