package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a WHERE LOWER(CONCAT(a.user.firstName, ' ', a.user.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Admin> findByFullName(String fullName, Pageable pageable);}
