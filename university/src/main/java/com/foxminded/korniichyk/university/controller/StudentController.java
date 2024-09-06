package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.security.CustomUserDetails;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService,
                             GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }



    @GetMapping("/")
    public String students(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {

        Page<StudentDto> students = studentService.findPage(pageNumber, pageSize);
        int totalPageNumber = students.getTotalPages();
        int currentPage = students.getNumber();
        long totalElements = students.getTotalElements();

        model.addAttribute("students", students);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);

        return "students";
    }

    @GetMapping("/my-group")
    public String myGroup(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        Long studentId = studentService.findByUserId(userId).getId();

        GroupDto group = groupService.findByStudentId(studentId);

        Page<StudentDto> studentPage = studentService.findStudentsPageByGroupId(group.getId(), page, size);

        List<StudentDto> filteredStudents = studentPage.getContent()
                .stream()
                .filter(studentDto -> !studentDto.getId().equals(studentId))
                .collect(Collectors.toList());

        model.addAttribute("students", filteredStudents);
        model.addAttribute("group", group);
        model.addAttribute("currentPage", studentPage.getNumber());
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalElements", studentPage.getTotalElements());
        return "/student/my-group";
    }

}
