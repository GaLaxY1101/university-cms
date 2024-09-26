package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.service.contract.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/")
    public String lessons(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<LessonDto> lessons = lessonService.findPage(pageable);
        int totalPageNumber = lessons.getTotalPages();
        int currentPage = lessons.getNumber();
        long totalElements = lessons.getTotalElements();

        model.addAttribute("lessons", lessons);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "lessons";
    }

}
