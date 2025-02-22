package org.example.projectd.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "type_service_material", schema = "project_designer")
public class TypeServiceMaterial {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_service_id", nullable = false)
    private TypeService typeService;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Double quantity;
}
