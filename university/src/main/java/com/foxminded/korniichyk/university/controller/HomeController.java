package com.foxminded.korniichyk.university.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class HomeController {


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "error", defaultValue = "false") boolean error,
            Model model
    ) {
        model.addAttribute("error", error);
        return "login";
    }

}
