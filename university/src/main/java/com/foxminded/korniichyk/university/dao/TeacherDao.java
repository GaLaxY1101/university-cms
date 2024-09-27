package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Long> {
    Teacher findByUserEmail(String userName);

    Teacher findByUserId(Long userId);

    boolean existsById(Long id);

    @Query("SELECT t FROM Teacher t WHERE LOWER(CONCAT(t.user.firstName, ' ', t.user.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Teacher> findByFullName(String fullName, Pageable pageable);}
