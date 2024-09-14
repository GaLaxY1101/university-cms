package com.foxminded.korniichyk.university.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"students", "lessons"})
@ToString(exclude = {"students", "lessons"})
@Data
@Entity
@Table(name = "groups",
        indexes = {
        @Index(name = "idx_groups_name", columnList = "name")
        }
)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq", sequenceName = "group_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "speciality_id",
            foreignKey = @ForeignKey(name = "fk_groups_speciality")
    )
    private Speciality speciality;

    @ManyToMany
    @JoinTable(
            name = "group_lesson",
            joinColumns = @JoinColumn(name = "group_id",
                    foreignKey = @ForeignKey(name = "fk_groups_lesson_group_id")
            ),
            inverseJoinColumns = @JoinColumn(name = "lesson_id",
                    foreignKey = @ForeignKey(name = "fk_groups_lesson_lesson_id")
            )
    )
    private Set<Lesson> lessons = new HashSet<>();

}
