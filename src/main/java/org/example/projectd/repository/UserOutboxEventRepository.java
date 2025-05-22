package org.example.projectd.repository;


import org.example.projectd.entity.UserOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserOutboxEventRepository extends JpaRepository<UserOutboxEvent, Integer> {
    List<UserOutboxEvent> findAllByProcessedAtIsNull();
}
