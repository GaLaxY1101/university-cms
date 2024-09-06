package com.foxminded.korniichyk.university.util;

import com.foxminded.korniichyk.university.annotation.WithMockCustomUser;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = new User();
        user.setId(1L);
        user.setEmail(customUser.email());
        user.setPassword("password");

        Set<Role> roles = Arrays.stream(customUser.roles())
                .map(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return role;
                })
                .collect(Collectors.toSet());
        user.setRoles(roles);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        Collection<SimpleGrantedAuthority> authorities = Arrays.stream(customUser.roles())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                "password",
                authorities
        );

        context.setAuthentication(authentication);
        return context;
    }
}

