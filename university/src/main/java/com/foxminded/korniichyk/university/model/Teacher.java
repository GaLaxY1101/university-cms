package com.foxminded.korniichyk.university.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"disciplines", "lessons"})
@ToString(exclude = {"disciplines", "lessons"})
@Entity
@Table(name = "teachers",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_teachers_user_id", columnNames = "user_id"),
        }
)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_seq")
    @SequenceGenerator(name = "teacher_seq", sequenceName = "teacher_sequence", allocationSize = 1)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_teachers_user")
    )
    private User user;

    @ManyToMany
    @JoinTable(
            name = "teacher_discipline",
            joinColumns = @JoinColumn(name = "teacher_id",
                    foreignKey = @ForeignKey(name = "fk_teacher_discipline_teacher_id")
            ),
            inverseJoinColumns = @JoinColumn(name = "discipline_id",
                    foreignKey = @ForeignKey(name = "fk_teacher_discipline_discipline_id")
            )
    )
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "teacher_lesson",
            joinColumns = @JoinColumn(name = "teacher_id",
                    foreignKey = @ForeignKey(name = "fk_teacher_lesson_teacher_id")
            ),
            inverseJoinColumns = @JoinColumn(name = "lesson_id",
                    foreignKey = @ForeignKey(name = "fk_teacher_lesson_lesson_id")
            )
    )
    private Set<Lesson> lessons = new HashSet<>();

    public void addDiscipline(Discipline discipline) {
        disciplines.add(discipline);
    }
}
