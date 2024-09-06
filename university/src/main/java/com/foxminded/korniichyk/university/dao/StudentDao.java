package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

    Optional<Student> findByUserId(Long userId);
    Page<Student> findByGroupId(Long groupId, Pageable pageable);
}
