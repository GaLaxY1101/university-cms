package com.foxminded.korniichyk.university.model;


import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Entity
@Table(name = "students",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_students_user_id", columnNames = "user_id")
        }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_students_user")
    )
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id",
            foreignKey = @ForeignKey(name = "fk_students_group")
    )
    private Group group;

}
