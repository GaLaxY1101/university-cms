package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
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
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;


    @GetMapping("/")
    public String disciplines(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<DisciplineDto> disciplinesPage;
        Sort sortOption = Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);

        if ((search != null) && (!search.isEmpty())) {
            disciplinesPage = disciplineService.findByName(search, pageable);
        } else {
            disciplinesPage = disciplineService.findPage(pageable);
        }

        int totalPageNumber = disciplinesPage.getTotalPages();
        int currentPage = disciplinesPage.getNumber();
        long totalElements = disciplinesPage.getTotalElements();
        List<DisciplineDto> disciplines = disciplinesPage.getContent();

        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("disciplines", disciplines);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("pageSize", pageSize);

        return "disciplines";
    }

}
