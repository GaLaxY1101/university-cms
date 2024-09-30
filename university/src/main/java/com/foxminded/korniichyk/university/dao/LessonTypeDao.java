package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.LessonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonTypeDao extends JpaRepository<LessonType, Long> {

    Page<LessonType> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
