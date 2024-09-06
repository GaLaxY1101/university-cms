package com.foxminded.korniichyk.university.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = { "teachers", "lessons"})
@ToString(exclude = { "teachers", "lessons"})
@Entity
@Table(name = "disciplines",
        indexes = {
        @Index(name = "idx_disciplines_name", columnList = "name")
        }
)
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "disciplines", cascade = CascadeType.ALL)
    private Set<Teacher> teachers = new HashSet<>();

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();
}
