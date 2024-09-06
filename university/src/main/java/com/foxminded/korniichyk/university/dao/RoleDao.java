package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
