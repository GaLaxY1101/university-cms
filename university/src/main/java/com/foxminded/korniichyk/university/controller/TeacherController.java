package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.security.CustomUserDetails;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final GroupService groupService;

    public TeacherController(TeacherService teacherService, GroupService groupService) {
        this.teacherService = teacherService;
        this.groupService =  groupService;
    }

    @GetMapping("/")
    public String teachers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<TeacherDto> teachers = teacherService.findPage(pageNumber, pageSize);
        int totalPageNumber = teachers.getTotalPages();
        int currentPage = teachers.getNumber();
        long totalElements = teachers.getTotalElements();

        model.addAttribute("teachers", teachers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "teachers";
    }


    @GetMapping("/my-groups")
    public String myGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Teacher teacher = teacherService.getCurrentTeacher();
        Long teacherId = teacher.getId();

        Page<GroupDto> groupsPage  = groupService.findPageByTeacherId(teacherId, page, size);

        model.addAttribute("groups", groupsPage.getContent());
        model.addAttribute("currentPage", groupsPage.getNumber());
        model.addAttribute("totalPages", groupsPage.getTotalPages());
        model.addAttribute("totalElements", groupsPage.getTotalPages());

        return "/teacher/my-groups";

    }


}
