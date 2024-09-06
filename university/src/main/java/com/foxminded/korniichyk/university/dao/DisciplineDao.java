package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DisciplineDao extends JpaRepository<Discipline, Long> {

    Set<Discipline> findAllByIdIn(Set<Long> disciplineIds);
}
