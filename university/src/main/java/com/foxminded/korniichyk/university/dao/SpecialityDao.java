package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.projection.input.InputOptionProjection;
import com.foxminded.korniichyk.university.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityDao extends JpaRepository<Speciality, Long> {

    @Query("SELECT new com.foxminded.korniichyk.university.projection.input.InputOptionProjection(s.id, CONCAT(s.code,' ',s.name) ) FROM Speciality s")
    List<InputOptionProjection> findAllSpecialityOptions();

}
