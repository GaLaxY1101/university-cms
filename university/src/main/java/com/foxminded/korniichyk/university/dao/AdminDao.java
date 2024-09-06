package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {

}
