package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

    List<Group> findByName(String name);


    @Query("SELECT DISTINCT g FROM Lesson l " +
            "JOIN l.groups g " +
            "JOIN l.teachers t " +
            "WHERE t.id = :teacherId")
    List<Group> findByTeacherId(Long teacherId);

    boolean existsById(Long id);
}
