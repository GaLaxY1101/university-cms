package com.foxminded.korniichyk.university.security;

import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.service.contract.UserService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByEmail(username);
        Hibernate.initialize(user.getRoles());
        return new CustomUserDetails(user);
    }
}
