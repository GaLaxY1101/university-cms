package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.projection.edit.group.SpecialityOptionProjection;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityDao extends JpaRepository<Speciality, Long> {

    @Query("SELECT new com.foxminded.korniichyk.university.projection.edit.group.SpecialityOptionProjection(s.id, s.name, s.code ) FROM Speciality s")
    List<SpecialityOptionProjection> findAllSpecialityOptions();

    Speciality findReferenceById(Long id);

    Page<Speciality> findByNameContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT new com.foxminded.korniichyk.university.projection.input.NameProjection(s.id, s.name) FROM Speciality s WHERE s.id = :id")
    NameProjection findSpecialityOptionById(Long id);
}
