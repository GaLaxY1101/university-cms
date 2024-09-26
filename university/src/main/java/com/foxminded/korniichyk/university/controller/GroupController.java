package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.service.contract.GroupService;
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
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/")
    public String groups(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<GroupDto> groups = groupService.findPage(pageable);
        int totalPageNumber = groups.getTotalPages();
        int currentPage = groups.getNumber();
        long totalElements = groups.getTotalElements();

        model.addAttribute("groups", groups);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "groups";
    }


}
