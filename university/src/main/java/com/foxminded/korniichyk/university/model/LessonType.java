package com.foxminded.korniichyk.university.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "lesson_types")
public class LessonType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonType_seq")
    @SequenceGenerator(name = "lessonType_seq", sequenceName = "lessonType_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "lessonType", cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();
}
