package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

    Page<Group> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Group> findAllByName(String name);


    @Query("SELECT g FROM Lesson l " +
            "JOIN l.groups g " +
            "JOIN l.teachers t " +
            "WHERE t.id = :teacherId")
    Page<Group> findByTeacherId(Long teacherId, Pageable pageable);

    @Query("SELECT g FROM Lesson l " +
            "JOIN l.groups g " +
            "JOIN l.teachers t " +
            "WHERE t.id = :teacherId " +
            "AND lower(g.name) LIKE lower(CONCAT('%', :name, '%'))")
    Page<Group> findByNameAndTeacherId(Long teacherId, String name, Pageable pageable);

    boolean existsById(Long id);

    @Query("SELECT new com.foxminded.korniichyk.university.projection.input.NameProjection(g.id, g.name) FROM Group g")
    List<NameProjection> findAllGroupOptions();

    boolean existsByName(String name);

    @Query("SELECT g.name FROM Group g WHERE g.id = :id")
    String getNameById(@Param("id") Long id);

    Group findReferenceById(Long groupId);
}
