package org.example.projectd.repository;


import org.example.projectd.entity.Customer;
import org.example.projectd.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByCompanyId(Integer companyId);
}
