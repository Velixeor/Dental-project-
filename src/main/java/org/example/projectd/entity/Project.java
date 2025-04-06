package org.example.projectd.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project", schema = "project_designer")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_create", nullable = false, updatable = false)
    private LocalDateTime dateCreate = LocalDateTime.now();

    private LocalDateTime dateCompleted;

    @Column(nullable = false)
    private String patient;

    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_antagonist")
    private TypeOfAntagonist typeOfAntagonist;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    private TeethColor teethColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technician_id")
    private Technician technician;
}
