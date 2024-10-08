package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
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
@RequestMapping("/specialities")
public class SpecialityController {

    private final SpecialityService specialityService;

    @GetMapping("")
    public String specialities(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {

        Page<SpecialityDto> specialitiesPage;
        Sort sortOption = Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);

        if ((search != null) && (!search.isEmpty())) {
            specialitiesPage = specialityService.findByName(search, pageable);
        } else {
            specialitiesPage = specialityService.findPage(pageable);
        }

        int totalPageNumber = specialitiesPage.getTotalPages();
        int currentPage = specialitiesPage.getNumber();
        long totalElements = specialitiesPage.getTotalElements();
        List<SpecialityDto> specialities = specialitiesPage.getContent();

        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("specialities", specialities);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("pageSize", pageSize);

        return "specialities";
    }
}
