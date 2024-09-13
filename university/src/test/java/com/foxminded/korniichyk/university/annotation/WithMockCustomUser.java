package com.foxminded.korniichyk.university.annotation;

import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.util.WithMockCustomUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String email() default "email@gmail.com";

    Role role() default Role.ROLE_ADMIN;
}

