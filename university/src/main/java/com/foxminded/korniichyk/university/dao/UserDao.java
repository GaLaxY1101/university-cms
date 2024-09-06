package com.foxminded.korniichyk.university.dao;

import com.foxminded.korniichyk.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
