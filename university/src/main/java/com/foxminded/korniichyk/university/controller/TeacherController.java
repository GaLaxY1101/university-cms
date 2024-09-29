package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
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
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final GroupService groupService;
    private final DisciplineService disciplineService;

    @GetMapping()
    public String teachers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Sort sortOption;
        if ("name".equals(sort)) {
            sortOption = Sort.by("user.firstName").ascending()
                    .and(Sort.by("user.lastName").ascending());
        } else {
            sortOption = Sort.by(sort).ascending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);

        Page<TeacherDto> teachersPage;
        if ((search != null) && !(search.isEmpty())) {
            teachersPage = teacherService.findByName(search, pageable);
        } else {
            teachersPage = teacherService.findPage(pageable);
        }

        List<TeacherDto> teachers = teachersPage.getContent();
        int totalPages = teachersPage.getTotalPages();
        int currentPage = teachersPage.getNumber();
        int totalElements = teachers.size();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("teachers", teachers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        return "teachers";
    }


    @GetMapping("/my-groups")
    public String myGroups(@RequestParam(required = false) String search,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = "7") int pageSize,
                                Model model
    ) {
        Teacher teacher = teacherService.getCurrentTeacher();
        Long teacherId = teacher.getId();

        Sort sortOption = Sort.by(sort);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);
        Page<GroupDto> groupsPage;

        if ((search != null) && !(search.isEmpty())) {
            groupsPage = groupService.findByNameAndTeacherId(teacherId, search, pageable);
        } else {
            groupsPage = groupService.findByTeacherId(teacherId, pageable);
        }

        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("groups", groupsPage.getContent());
        model.addAttribute("currentPage", groupsPage.getNumber());
        model.addAttribute("totalPages", groupsPage.getTotalPages());
        model.addAttribute("totalElements", groupsPage.getTotalPages());

        return "/teacher/my-groups";
    }


    @GetMapping("/my-disciplines")
    public String myDisciplines(@RequestParam(required = false) String search,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = "7") int pageSize,
                                Model model
    ) {
        Teacher teacher = teacherService.getCurrentTeacher();
        Long teacherId = teacher.getId();

        Sort sortOption = Sort.by(sort);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOption);
        Page<DisciplineDto> disciplinesPage;

        if ((search != null) && !(search.isEmpty())) {
            disciplinesPage = disciplineService.findAllByNameAndTeacherId(teacherId, search, pageable);
        } else {
            disciplinesPage = disciplineService.findAllByTeacherId(teacherId, pageable);
        }

        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("disciplines", disciplinesPage.getContent());
        model.addAttribute("currentPage", disciplinesPage.getNumber());
        model.addAttribute("totalPages", disciplinesPage.getTotalPages());
        model.addAttribute("totalElements", disciplinesPage.getTotalPages());

        return "/teacher/my-disciplines";
    }
}

