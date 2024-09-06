package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/specialities")
public class SpecialityController {

    private final SpecialityService specialityService;

    @Autowired
    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping("/")
    public String specialities(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<SpecialityDto> specialities = specialityService.findPage(pageNumber, pageSize);
        int totalPageNumber = specialities.getTotalPages();
        int currentPage = specialities.getNumber();
        long totalElements = specialities.getTotalElements();

        model.addAttribute("specialities", specialities);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "specialities";
    }
}
