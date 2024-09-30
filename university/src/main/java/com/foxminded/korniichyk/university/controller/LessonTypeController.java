package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.service.contract.LessonTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lesson-types")
public class LessonTypeController {


    private final LessonTypeService lessonTypeService;


    @GetMapping("")
    public String lessonTypes(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<LessonTypeDto> lessonTypesPage;
        Sort sortOption = Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);

        if ((search != null) && (!search.isEmpty())) {
            lessonTypesPage = lessonTypeService.findByName(search, pageable);
        } else {
            lessonTypesPage = lessonTypeService.findPage(pageable);
        }

        int totalPageNumber = lessonTypesPage.getTotalPages();
        int currentPage = lessonTypesPage.getNumber();
        long totalElements = lessonTypesPage.getTotalElements();
        List<LessonTypeDto> lessonTypes = lessonTypesPage.getContent();

        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("lessonTypes", lessonTypes);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("pageSize", pageSize);

        return "lesson-types";
    }

}
