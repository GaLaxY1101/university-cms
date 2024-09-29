package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.update.GroupUpdateDto;
import com.foxminded.korniichyk.university.projection.edit.group.StudentProjection;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;
    private final SpecialityService specialityService;


    @GetMapping()
    public String groups(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {

        Page<GroupDto> groupsPage;
        Sort sortOption = Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);

        if ((search != null) && (!search.isEmpty())) {
            groupsPage = groupService.findByName(search, pageable);
        } else {
            groupsPage = groupService.findPage(pageable);
        }

        int totalPageNumber = groupsPage.getTotalPages();
        int currentPage = groupsPage.getNumber();
        long totalElements = groupsPage.getTotalElements();
        List<GroupDto> groups = groupsPage.getContent();

        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("groups", groups);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("pageSize", pageSize);

        return "groups";
    }

    @GetMapping("/{id}")
    public String editGroupPage(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            Model model) {

        List<StudentProjection> students = studentService.findStudentsByGroupId(id);
        model.addAttribute("students", students);

        GroupUpdateDto groupUpdateDto = groupService.getGroupUpdateDtoById(id);

        NameProjection speciality = specialityService.findSpecialityOptionById(groupUpdateDto.getSpecialityId());

        model.addAttribute("speciality", speciality);
        model.addAttribute("groupUpdateDto", groupUpdateDto);
        model.addAttribute("totalElements", students.size());
        return "group-info";
    }

}
