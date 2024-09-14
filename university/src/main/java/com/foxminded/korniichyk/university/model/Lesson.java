package com.foxminded.korniichyk.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_sequence", allocationSize = 50)
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
