package com.foxminded.korniichyk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @SequenceGenerator(name = "speciality_seq", sequenceName = "speciality_sequence", allocationSize = 50)
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
