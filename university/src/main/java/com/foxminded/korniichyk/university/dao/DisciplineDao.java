package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DisciplineDao extends JpaRepository<Discipline, Long> {

    Set<Discipline> findAllByIdIn(Set<Long> disciplineIds);

    @Query("SELECT new com.foxminded.korniichyk.university.projection.input.NameProjection(d.id, d.name) FROM Discipline d")
    List<NameProjection> findAllDisciplineOptions();

    Page<Discipline> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
