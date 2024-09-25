package com.foxminded.korniichyk.university.controller.admin;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.dto.option.SpecialityOptionDto;
import com.foxminded.korniichyk.university.dto.option.StudentOptionDto;
import com.foxminded.korniichyk.university.dto.registration.AdminRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.GroupRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.StudentRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.TeacherRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.dto.update.GroupUpdateDto;
import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.dto.update.TeacherUpdateDto;
import com.foxminded.korniichyk.university.mapper.update.AdminUpdateMapper;
import com.foxminded.korniichyk.university.mapper.update.StudentUpdateMapper;
import com.foxminded.korniichyk.university.mapper.update.TeacherUpdateMapper;
import com.foxminded.korniichyk.university.projection.edit.group.StudentProjection;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import com.foxminded.korniichyk.university.service.contract.AdminService;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.LessonService;
import com.foxminded.korniichyk.university.service.contract.LessonTypeService;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.EmailAlreadyExistsException;
import com.foxminded.korniichyk.university.service.exception.PhoneNumberAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/administrators")
public class AdminController {

    private final DisciplineService disciplineService;

    private final AdminService adminService;

    private final GroupService groupService;

    private final LessonService lessonService;

    private final LessonTypeService lessonTypeService;

    private final SpecialityService specialityService;

    private final StudentService studentService;

    private final TeacherService teacherService;

    private final UserService userService;

    public AdminController(AdminService adminService,
                           DisciplineService disciplineService,
                           GroupService groupService,
                           LessonService lessonService,
                           LessonTypeService lessonTypeService,
                           SpecialityService specialityService,
                           StudentService studentService,
                           TeacherService teacherService,
                           StudentUpdateMapper studentUpdateMapper,
                           AdminUpdateMapper adminUpdateMapper,
                           TeacherUpdateMapper teacherUpdateMapper,
                           UserService userService
    ) {
        this.adminService = adminService;
        this.disciplineService = disciplineService;
        this.groupService = groupService;
        this.lessonService = lessonService;
        this.lessonTypeService = lessonTypeService;
        this.specialityService = specialityService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.userService = userService;
    }


    @GetMapping("/disciplines")
    public String disciplines(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<DisciplineDto> disciplinesPage = disciplineService.findPage(pageNumber, pageSize);
        int totalPageNumber = disciplinesPage.getTotalPages();
        int currentPage = disciplinesPage.getNumber();
        long totalElements = disciplinesPage.getTotalElements();
        List<DisciplineDto> disciplines = disciplinesPage.getContent();

        model.addAttribute("disciplines", disciplines);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "admin/disciplines";
    }

    @GetMapping("/groups")
    public String groups(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<GroupDto> groupsPage = groupService.findPage(pageNumber, pageSize);
        int totalPageNumber = groupsPage.getTotalPages();
        int currentPage = groupsPage.getNumber();
        long totalElements = groupsPage.getTotalElements();
        List<GroupDto> groups = groupsPage.getContent();

        model.addAttribute("groups", groups);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "admin/groups";
    }


    @GetMapping("/lessons")
    public String lessons(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<LessonDto> lessonsPage = lessonService.findPage(pageNumber, pageSize);
        int totalPageNumber = lessonsPage.getTotalPages();
        int currentPage = lessonsPage.getNumber();
        long totalElements = lessonsPage.getTotalElements();
        List<LessonDto> lessons = lessonsPage.getContent();

        model.addAttribute("lessons", lessons);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "admin/lessons";
    }

    @GetMapping("/lesson-types")
    public String lessonTypes(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<LessonTypeDto> lessonTypesPage = lessonTypeService.findPage(pageNumber, pageSize);
        int totalPageNumber = lessonTypesPage.getTotalPages();
        int currentPage = lessonTypesPage.getNumber();
        long totalElements = lessonTypesPage.getTotalElements();
        List<LessonTypeDto> lessons = lessonTypesPage.getContent();

        model.addAttribute("lessonTypes", lessons);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "admin/lesson-types";
    }

    @GetMapping("/specialities")
    public String specialities(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<SpecialityDto> specialitiesPage = specialityService.findPage(pageNumber, pageSize);
        int totalPageNumber = specialitiesPage.getTotalPages();
        int currentPage = specialitiesPage.getNumber();
        long totalElements = specialitiesPage.getTotalElements();
        List<SpecialityDto> specialities = specialitiesPage.getContent();

        model.addAttribute("specialities", specialities);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "admin/specialities";
    }

    @GetMapping("/students")
    public String students(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {

        Page<StudentDto> studentsPage = studentService.findPage(pageNumber, pageSize);
        int totalPageNumber = studentsPage.getTotalPages();
        int currentPage = studentsPage.getNumber();
        long totalElements = studentsPage.getTotalElements();
        List<StudentDto> students = studentsPage.getContent();

        model.addAttribute("students", students);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);

        return "admin/students";
    }

    @GetMapping("/students/create-page")
    public String createStudentPage(Model model) {

        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();

        model.addAttribute("studentRegistrationDto", studentRegistrationDto);
        Pageable pageable = PageRequest.of(0, 10);
        model.addAttribute("groups", groupService.findAllNameProjections());

        return "admin/create/create-student";
    }

    @PostMapping("/students/create-page")
    public String createStudent(
            Model model,
            @ModelAttribute("studentRegistrationDto") @Valid StudentRegistrationDto studentRegistrationDto,
            BindingResult bindingResult
    ) {

        if (!studentRegistrationDto.getUser().isPasswordConfirmed()) {
            bindingResult.rejectValue("user.confirmPassword", "error.confirmPassword", "Passwords must match");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("groups", groupService.findAllNameProjections());
            return "admin/create/create-student";
        }

        try {
            studentService.registerStudent(studentRegistrationDto);
        } catch (EmailAlreadyExistsException | PhoneNumberAlreadyExistsException ex) {

            if (ex instanceof EmailAlreadyExistsException) {
                bindingResult.rejectValue("user.email", "error.email", ex.getMessage());
            }
            if (ex instanceof PhoneNumberAlreadyExistsException) {
                bindingResult.rejectValue("user.phoneNumber", "error.phoneNumber", ex.getMessage());
            }
            model.addAttribute("groups", groupService.findAllNameProjections());
            return "admin/create/create-student";
        }

        return "redirect:/administrators/students";
    }


    @GetMapping("/teachers")
    public String teachers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        Page<TeacherDto> teachersPage = teacherService.findPage(pageNumber, pageSize);
        int totalPageNumber = teachersPage.getTotalPages();
        int currentPage = teachersPage.getNumber();
        long totalElements = teachersPage.getTotalElements();
        List<TeacherDto> teachers = teachersPage.getContent();

        model.addAttribute("teachers", teachers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPageNumber);
        model.addAttribute("totalElements", totalElements);
        return "admin/teachers";
    }

    @GetMapping("/teachers/create-page")
    public String createTeacherPage(Model model) {
        TeacherRegistrationDto teacherRegistrationDto = new TeacherRegistrationDto();
        model.addAttribute("teacherRegistrationDto", teacherRegistrationDto);
        model.addAttribute("disciplines", disciplineService.findAllDisciplineOptions());
        return "admin/create/create-teacher";
    }

    @PostMapping("/teachers/create-page")
    public String createTeacher(
            @ModelAttribute("teacherRegistrationDto") @Valid TeacherRegistrationDto teacherRegistrationDto,
            BindingResult bindingResult,
            Model model
    ) {

        if (!teacherRegistrationDto.getUser().isPasswordConfirmed()) {
            bindingResult.rejectValue("user.confirmPassword", "error.confirmPassword", "Passwords must match");
        }


        if (bindingResult.hasErrors()) {
            model.addAttribute("teacherRegistrationDto", teacherRegistrationDto);
            model.addAttribute("disciplines", disciplineService.findAllDisciplineOptions());
            return "admin/create/create-teacher";
        }

        try {
            teacherService.registerTeacher(teacherRegistrationDto);
        } catch (EmailAlreadyExistsException | PhoneNumberAlreadyExistsException ex) {

            if (ex instanceof EmailAlreadyExistsException) {
                bindingResult.rejectValue("user.email", "error.email", ex.getMessage());
            }
            if (ex instanceof PhoneNumberAlreadyExistsException) {
                bindingResult.rejectValue("user.phoneNumber", "error.phoneNumber", ex.getMessage());

            }
            model.addAttribute("disciplines", disciplineService.findAllDisciplineOptions());
            return "admin/create/create-teacher";
        }
        return "redirect:/administrators/teachers";
    }


    @GetMapping("/admins")
    public String admins(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            Model model
    ) {
        var page = adminService.findPage(pageNumber, pageSize);

        var admins = page.getContent();
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();
        int totalElements = admins.size();

        model.addAttribute("administrators", admins);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        return "admin/administrators";
    }

    @GetMapping("/admins/create-page")
    public String createAdminPage(Model model) {
        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();
        model.addAttribute("adminRegistrationDto", adminRegistrationDto);

        return "admin/create/create-admin";
    }

    @PostMapping("/admins/create-page")
    public String createAdmin(@ModelAttribute @Valid AdminRegistrationDto adminRegistrationDto,
                              BindingResult bindingResult,
                              Model model) {

        if (!adminRegistrationDto.getUser().isPasswordConfirmed()) {
            bindingResult.rejectValue("user.confirmPassword", "error.confirmPassword", "Passwords must match");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("adminRegistrationDto", adminRegistrationDto);
            return "admin/create/create-admin";
        }

        try {
            adminService.registerAdmin(adminRegistrationDto);
        } catch (EmailAlreadyExistsException ex) {
            bindingResult.rejectValue("user.email", "error.email", ex.getMessage());
            return "admin/create/create-admin";
        } catch (PhoneNumberAlreadyExistsException ex) {
            bindingResult.rejectValue("user.phoneNumber", "error.phoneNumber", ex.getMessage());
            return "admin/create/create-admin";
        }

        return "redirect:/administrators/admins";

    }

    @DeleteMapping("/admins/delete/{id}")
    public String deleteAdmin(
            @PathVariable("id") Long adminId,
            RedirectAttributes redirectAttributes
    ) {

        adminService.deleteById(adminId);
        redirectAttributes.addFlashAttribute("successMessage", "Admin deleted successfully");

        return "redirect:/administrators/admins";
    }


    @DeleteMapping("/students/delete/{id}")
    public String deleteStudent(
            @PathVariable("id") Long studentId,
            RedirectAttributes redirectAttributes
    ) {

        studentService.deleteById(studentId);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully");

        return "redirect:/administrators/students";
    }

    @DeleteMapping("/teachers/delete/{id}")
    public String deleteTeacher(
            @PathVariable("id") Long teacherId,
            RedirectAttributes redirectAttributes
    ) {

        teacherService.deleteById(teacherId);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher deleted successfully");

        return "redirect:/administrators/teachers";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentPage(
            @PathVariable(name = "id") Long studentId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        StudentUpdateDto studentUpdateDto = studentService.getStudentUpdateDto(studentId);
        List<NameProjection> groups = groupService.findAllNameProjections();

        model.addAttribute("studentUpdateDto", studentUpdateDto);
        model.addAttribute("groups", groups);

        return "admin/edit/edit-student";
    }

    @PutMapping("/students/edit")
    public String editStudent(
            @ModelAttribute @Valid StudentUpdateDto studentUpdateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        String emailBeforeUpdate = studentService.findById(studentUpdateDto.getId()).getUser().getEmail();
        String phoneNumberBeforeUpdate = studentService.findById(studentUpdateDto.getId()).getUser().getPhoneNumber();

        String emailAfterUpdate = studentUpdateDto.getUser().getEmail();
        String phoneNumberAfterUpdate = studentUpdateDto.getUser().getPhoneNumber();


        if ((!emailBeforeUpdate.equals(emailAfterUpdate)) && (userService.isExistsByEmail(emailAfterUpdate))) {
            bindingResult.rejectValue("user.email", "error.email", "This email already registered");
            model.addAttribute("groups", groupService.findAllNameProjections());
            return "admin/edit/edit-student";
        }

        if ((!phoneNumberBeforeUpdate.equals(phoneNumberAfterUpdate)) && (userService.isExistsByPhoneNumber(phoneNumberAfterUpdate))) {
            bindingResult.rejectValue("user.phoneNumber", "error.phoneNumber", "This phone number already registered");
            model.addAttribute("groups", groupService.findAllNameProjections());
            return "admin/edit/edit-student";
        }

        if (bindingResult.hasErrors()) {
            List<NameProjection> groups = groupService.findAllNameProjections();
            model.addAttribute("groups", groups);
            model.addAttribute("studentUpdateDto", studentUpdateDto);
            return "admin/edit/edit-student";
        }


        studentService.update(studentUpdateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
        return "redirect:/administrators/students";

    }


    @GetMapping("/admins/edit/{id}")
    public String showEditAdminPage(
            @PathVariable(name = "id") Long adminId,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        AdminUpdateDto adminUpdateDto = adminService.getAdminUpdateDto(adminId);
        model.addAttribute("adminUpdateDto", adminUpdateDto);


        return "admin/edit/edit-admin";
    }

    @PostMapping("/admins/edit")
    String editAdmin(
            @ModelAttribute @Valid AdminUpdateDto adminUpdateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        String emailBeforeUpdate = adminService.findById(adminUpdateDto.getId()).getUser().getEmail();
        String phoneNumberBeforeUpdate = adminService.findById(adminUpdateDto.getId()).getUser().getPhoneNumber();

        String emailAfterUpdate = adminUpdateDto.getUser().getEmail();
        String phoneNumberAfterUpdate = adminUpdateDto.getUser().getPhoneNumber();


        if ((!emailBeforeUpdate.equals(emailAfterUpdate)) && (userService.isExistsByEmail(emailAfterUpdate))) {
            bindingResult.rejectValue("user.email", "error.email", "This email already registered");
            return "admin/edit/edit-admin";
        }

        if ((!phoneNumberBeforeUpdate.equals(phoneNumberAfterUpdate)) && (userService.isExistsByPhoneNumber(phoneNumberAfterUpdate))) {
            bindingResult.rejectValue("user.phoneNumber", "error.phoneNumber", "This phone number already registered");
            return "admin/edit/edit-admin";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("adminUpdateDto", adminUpdateDto);
            return "admin/edit/edit-admin";
        }


        adminService.update(adminUpdateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Admin updated successfully");
        return "redirect:/administrators/admins";
    }

    @GetMapping("/teachers/edit/{id}")
    String showEditTeacherPage(
            @PathVariable(name = "id") Long teacherId,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        TeacherUpdateDto teacherUpdateDto = teacherService.getTeacherUpdateDto(teacherId);
        List<NameProjection> disciplines = disciplineService.findAllDisciplineOptions();
        model.addAttribute("disciplines", disciplines);
        model.addAttribute("teacherUpdateDto", teacherUpdateDto);


        return "admin/edit/edit-teacher";

    }


    @PostMapping("/teachers/edit")
    String editTeacher(
            @ModelAttribute @Valid TeacherUpdateDto teacherUpdateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        String emailBeforeUpdate = teacherService.findById(teacherUpdateDto.getId()).getUser().getEmail();
        String phoneNumberBeforeUpdate = teacherService.findById(teacherUpdateDto.getId()).getUser().getPhoneNumber();

        String emailAfterUpdate = teacherUpdateDto.getUser().getEmail();
        String phoneNumberAfterUpdate = teacherUpdateDto.getUser().getPhoneNumber();


        if ((!emailBeforeUpdate.equals(emailAfterUpdate)) && (userService.isExistsByEmail(emailAfterUpdate))) {
            bindingResult.rejectValue("user.email", "error.email", "This email already registered");
            model.addAttribute("disciplines", disciplineService.findAllDisciplineOptions());
            return "admin/edit/edit-teacher";
        }

        if ((!phoneNumberBeforeUpdate.equals(phoneNumberAfterUpdate)) && (userService.isExistsByPhoneNumber(phoneNumberAfterUpdate))) {
            bindingResult.rejectValue("user.phoneNumber", "error.phoneNumber", "This phone number already registered");
            model.addAttribute("disciplines", disciplineService.findAllDisciplineOptions());
            return "admin/edit/edit-teacher";
        }


        if (bindingResult.hasErrors()) {
            model.addAttribute("teacherUpdateDto", teacherUpdateDto);
            List<NameProjection> disciplines = disciplineService.findAllDisciplineOptions();
            model.addAttribute("disciplines", disciplines);
            return "admin/edit/edit-teacher";
        }


        teacherService.update(teacherUpdateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher updated successfully");
        return "redirect:/administrators/teachers";

    }

    @GetMapping("/groups/create-page")
    String createGroupPage(Model model) {

        List<SpecialityOptionDto> specialities = specialityService.findAllSpecialityOptions();
        GroupRegistrationDto groupRegistrationDto = new GroupRegistrationDto();

        model.addAttribute("groupRegistrationDto", groupRegistrationDto);
        model.addAttribute("specialities", specialities);

        return "admin/create/create-group";
    }

    @PostMapping("/groups/create-page")
    String createGroup(
            @ModelAttribute @Valid GroupRegistrationDto groupRegistrationDto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            List<SpecialityOptionDto> specialities = specialityService.findAllSpecialityOptions();

            model.addAttribute("groupRegistrationDto", groupRegistrationDto);
            model.addAttribute("specialities", specialities);
            return "admin/create/create-group";
        }

        String newGroupName = groupRegistrationDto.getName();

        if (groupService.isExistsByName(newGroupName)) {
            bindingResult.rejectValue("name", "error.name", "This name already in use by another group");

            List<SpecialityOptionDto> specialities = specialityService.findAllSpecialityOptions();
            model.addAttribute("groupRegistrationDto", groupRegistrationDto);
            model.addAttribute("specialities", specialities);
            return "admin/create/create-group";
        }

        groupService.registerGroup(groupRegistrationDto);
        return "redirect:/administrators/groups";

    }

    @DeleteMapping("/groups/delete/{id}")
    public String deleteGroup(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        groupService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Group deleted successfully");


        return "redirect:/administrators/groups";
    }

    @GetMapping("/groups/edit/{id}")
    public String editGroupPage(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "7") int pageSize,
            RedirectAttributes redirectAttributes,
            Model model) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<StudentProjection> students = studentService.findStudentsByGroupId(id, pageable);

        model.addAttribute("students", students);

        GroupUpdateDto groupUpdateDto = groupService.getGroupUpdateDtoById(id);
        model.addAttribute("groupUpdateDto", groupUpdateDto);

        model.addAttribute("totalElements", students.getTotalElements());


        List<StudentOptionDto> studentsWithoutGroups = studentService.findAllStudentOptionsWithoutGroup();
        model.addAttribute("studentsWithoutGroup", studentsWithoutGroups);

        List<SpecialityOptionDto> specialities = specialityService.findAllSpecialityOptions();
        model.addAttribute("specialities", specialities);
        return "admin/edit/edit-group";
    }

    @PutMapping("/groups/edit/unassign-group/{studentId}")
    String unassignGroup(
            @PathVariable Long studentId,
            @RequestParam Long groupId,
            RedirectAttributes redirectAttributes
    ) {

        studentService.unassignGroup(studentId);
        redirectAttributes.addFlashAttribute("successMessage", "Student removed from group successfully");

        return "redirect:/administrators/groups/edit/" + groupId;
    }

    @PutMapping("/groups/edit")
    String editGroup(@ModelAttribute @Valid GroupUpdateDto groupUpdateDto,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes,
                     Model model) {


        String nameBeforeUpdate = groupService.getNameById(groupUpdateDto.getId());

        if (!nameBeforeUpdate.equals(groupUpdateDto.getName()) && groupService.isExistsByName(nameBeforeUpdate)) {
            bindingResult.rejectValue("name", "error.name", "This name already in use by another group");
        }

        if (bindingResult.hasErrors()) {
            List<SpecialityOptionDto> specialities = specialityService.findAllSpecialityOptions();

            Pageable pageable = PageRequest.of(0, 7);
            Page<StudentProjection> students = studentService.findStudentsByGroupId(groupUpdateDto.getId(), pageable);

            model.addAttribute("students", students);
            model.addAttribute("groupUpdateDto", groupUpdateDto);
            model.addAttribute("specialities", specialities);
            model.addAttribute("totalElements", model.getAttribute("totalElements"));

            return "admin/edit/edit-group";
        }


        groupService.update(groupUpdateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Group updated successfully");

        return "redirect:/administrators/groups";

    }


    @PutMapping("/groups/add-student/{studentId}")
    String addStudentToGroup(
            @PathVariable Long studentId,
            @RequestParam Long groupId,
            RedirectAttributes redirectAttributes,
            Model model
    ) {


        studentService.assignGroup(groupId, studentId);
        redirectAttributes.addFlashAttribute("successMessage", "Student was added to group successfully");


        List<SpecialityOptionDto> specialities = specialityService.findAllSpecialityOptions();

        Pageable pageable = PageRequest.of(0, 7);
        Page<StudentProjection> students = studentService.findStudentsByGroupId(groupId, pageable);

        GroupUpdateDto groupUpdateDto = groupService.getGroupUpdateDtoById(groupId);

        model.addAttribute("students", students);
        model.addAttribute("groupUpdateDto", groupUpdateDto);
        model.addAttribute("specialities", specialities);
        model.addAttribute("totalElements", model.getAttribute("totalElements"));

        return "redirect:/administrators/groups/edit/" + groupId;
    }
}
