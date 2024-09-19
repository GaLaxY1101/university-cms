package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.projection.input.InputOptionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

    boolean existsById(Long id);

    Optional<Student> findByUserId(Long userId);

    @Query("SELECT s FROM Student s WHERE s.group.id= :groupId AND s.id != :studentId")
    Page<Student> findByGroupIdExcludingByStudentId(Long groupId,
                                                    Long studentId,
                                                    Pageable pageable);


    @Query("SELECT new com.foxminded.korniichyk.university.projection.input.InputOptionProjection(s.id, CONCAT(s.user.firstName, ' ', s.user.lastName)) FROM Student s")
    List<InputOptionProjection> findAllStudentOptions();

    Set<Student> findAllByIdIn(Set<Long> studentIds);
}
