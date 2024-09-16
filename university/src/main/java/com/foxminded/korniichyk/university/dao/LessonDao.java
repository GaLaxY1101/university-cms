package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonDao extends JpaRepository<Lesson, Long> {
}
