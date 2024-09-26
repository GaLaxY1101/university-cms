package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.service.contract.LessonTypeService;
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
@RequestMapping("/lesson-types")
public class LessonTypeController {


    private final LessonTypeService lessonTypeService;


    @GetMapping("/")
    public String lessonTypes(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<LessonTypeDto> lessonTypes = lessonTypeService.findPage(pageable);
        int totalPageNumber = lessonTypes.getTotalPages();
        int currentPage = lessonTypes.getNumber();
        long totalElements = lessonTypes.getTotalElements();

        model.addAttribute("lessonTypes", lessonTypes);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "lesson-types";
    }

}
