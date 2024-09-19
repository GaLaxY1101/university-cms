package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final GroupMapper groupMapper;

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

        Student currentStudent = studentService.getCurrentStudent();
        Group group = currentStudent.getGroup();

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDto> studentPage = studentService.findByGroupIdExcludingByStudentId(group.getId(), currentStudent.getId(), pageable);
        List<StudentDto> students = studentPage.getContent();

        model.addAttribute("students", students);
        model.addAttribute("group", groupMapper.toDto(group));
        model.addAttribute("currentPage", studentPage.getNumber());
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalElements", studentPage.getTotalElements());
        return "/student/my-group";
    }

}
