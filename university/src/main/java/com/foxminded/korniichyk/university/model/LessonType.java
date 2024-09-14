package com.foxminded.korniichyk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
