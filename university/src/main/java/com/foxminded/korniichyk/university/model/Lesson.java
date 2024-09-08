package com.foxminded.korniichyk.university.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"teachers","groups"})
@ToString(exclude = {"teachers","groups"})
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discipline_id",
    foreignKey = @ForeignKey(name = "fk_lessons_discipline_id")
    )
    private Discipline discipline;

    @ManyToOne
    @JoinColumn(name = "lesson_type_id",
        foreignKey = @ForeignKey(name = "fk_lessons_lesson_type_id")
    )
    private LessonType lessonType;

    @ManyToMany(mappedBy = "lessons")
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(mappedBy = "lessons")
    private Set<Group> groups = new HashSet<>();

    @Column(nullable = false)
    private Integer classroomNumber;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
}
