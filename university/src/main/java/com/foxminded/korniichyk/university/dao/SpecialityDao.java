package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityDao extends JpaRepository<Speciality, Long> {

}
