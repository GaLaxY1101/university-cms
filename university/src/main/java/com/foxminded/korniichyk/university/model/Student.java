package com.foxminded.korniichyk.university.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
    @SequenceGenerator(name = "student_seq", sequenceName = "student_sequence", allocationSize = 50)
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
