package com.foxminded.korniichyk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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

    public void removeDiscipline(Discipline discipline) {
        disciplines.remove(discipline);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
