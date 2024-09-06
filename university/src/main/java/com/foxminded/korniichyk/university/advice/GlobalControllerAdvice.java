package com.foxminded.korniichyk.university.advice;

import com.foxminded.korniichyk.university.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        if (userDetails != null) {
            model.addAttribute("firstName", userDetails.getUser().getFirstName());
            model.addAttribute("lastName", userDetails.getUser().getLastName());
        }
    }
}
