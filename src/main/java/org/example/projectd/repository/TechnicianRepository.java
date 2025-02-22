package org.example.projectd.repository;


import org.example.projectd.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {

}
