package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.projection.edit.group.StudentOptionProjection;
import com.foxminded.korniichyk.university.projection.edit.group.StudentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query("SELECT new com.foxminded.korniichyk.university.projection.input.NameProjection(s.id, CONCAT(s.user.firstName, ' ', s.user.lastName)) FROM Student s")
    List<StudentOptionProjection> findAllStudentOptions();

    @Query("SELECT new com.foxminded.korniichyk.university.projection.edit.group.StudentProjection(s.id, s.user.firstName, s.user.lastName, s.user.email) FROM Student s WHERE s.group.id = :groupId")
    List<StudentProjection> findStudentsByGroupId(Long groupId);

    Set<Student> findAllByIdIn(Set<Long> studentIds);

    @Query("SELECT new com.foxminded.korniichyk.university.projection.edit.group.StudentOptionProjection(s.id, s.user.firstName, s.user.lastName) FROM Student s WHERE s.group IS NULL")
    List<StudentOptionProjection> findAllStudentOptionsWithoutGroup();

    Student findReferenceById(Long studentId);

    @Query("SELECT s FROM Student s WHERE LOWER(CONCAT(s.user.firstName, ' ', s.user.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Student> findByFullName(@Param("fullName") String fullName, Pageable pageable);

}
