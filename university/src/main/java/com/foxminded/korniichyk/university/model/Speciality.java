package com.foxminded.korniichyk.university.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "groups")
@ToString(exclude = "groups")
@Entity
@Table(name = "specialities",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_specialities_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_specialities_code", columnNames = "code")
        },
        indexes = {
                @Index(name = "idx_specialities_name", columnList = "name"),
                @Index(name = "idx_specialities_code", columnList = "code")
        }
)
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "speciality_seq")
    @SequenceGenerator(name = "speciality_seq", sequenceName = "speciality_sequence", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Integer code;

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();
}
