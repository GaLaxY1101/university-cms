package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;


    @GetMapping("/")
    public String disciplines(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<DisciplineDto> disciplines = disciplineService.findPage(pageNumber, pageSize);
        int totalPageNumber = disciplines.getTotalPages();
        int currentPage = disciplines.getNumber();
        long totalElements = disciplines.getTotalElements();

        model.addAttribute("disciplines", disciplines);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "disciplines";
    }

}
