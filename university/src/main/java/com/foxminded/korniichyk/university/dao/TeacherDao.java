package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Long> {
    Teacher findByUserEmail(String userName);

    Teacher findByUserId(Long userId);

}
