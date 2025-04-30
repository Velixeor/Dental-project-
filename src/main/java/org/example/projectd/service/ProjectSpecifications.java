package org.example.projectd.service;


import org.example.projectd.entity.Project;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;


public class ProjectSpecifications {

    private static final List<String> ALLOWED = List.of(
            "customer", "patient", "status", "comment", "typeOfAntagonist", "teethColor", "technician"
    );

    public static Specification<Project> searchByFields(String query, List<String> filters) {
        String likePattern = "%" + query.toLowerCase() + "%";

        return (root, cq, cb) -> {
            List<Predicate> predicates = filters.stream()
                    .filter(ALLOWED::contains)
                    .map(field -> {
                        switch (field) {
                            case "customer":
                                return cb.like(cb.lower(root.get("customer").get("name")), likePattern);
                            case "technician":
                                return cb.like(cb.lower(root.get("technician").get("name")), likePattern);
                            default:
                                return cb.like(cb.lower(root.get(field).as(String.class)), likePattern);
                        }
                    })
                    .collect(Collectors.toList());

            return predicates.isEmpty() ? cb.conjunction() : cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
