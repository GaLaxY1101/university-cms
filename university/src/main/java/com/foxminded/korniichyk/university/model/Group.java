package com.foxminded.korniichyk.university.model;


import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id",
            foreignKey = @ForeignKey(name = "fk_groups_speciality")
    )
    private Speciality speciality;

    @ManyToMany(fetch = FetchType.LAZY)
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
