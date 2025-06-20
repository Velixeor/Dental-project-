package org.example.projectd.repository;


import org.example.projectd.entity.Customer;
import org.example.projectd.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
 Technician findByUserId(Integer userid);
 List<Technician> findAllByCompanyId(Integer companyId);
 @Modifying
 @Query("UPDATE Technician t SET t.userId = :userId WHERE t.id = :technicianId")
 void updateUserIdByTechnicianId(@Param("userId") Integer userId, @Param("technicianId") Long technicianId);

}
