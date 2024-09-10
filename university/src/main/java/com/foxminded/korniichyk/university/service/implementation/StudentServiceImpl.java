package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.registration.StudentRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.RoleMapper;
import com.foxminded.korniichyk.university.mapper.display.StudentMapper;
import com.foxminded.korniichyk.university.mapper.update.StudentUpdateMapper;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.security.CustomUserDetails;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StudentServiceImpl.class);
    private final GroupDao groupDao;

    private final StudentDao studentDao;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final StudentUpdateMapper studentUpdateMapper;
    private final GroupService groupService;

    public StudentServiceImpl(StudentDao studentDao,
                              StudentMapper studentMapper,
                              GroupDao groupDao,
                              UserService userService,
                              RoleMapper roleMapper,
                              StudentUpdateMapper studentUpdateMapper, GroupService groupService) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
        this.groupDao = groupDao;
        this.userService = userService;
        this.groupService = groupService;
        this.studentUpdateMapper = studentUpdateMapper;
    }

    @Override
    public StudentDto findById(Long id) {
        return studentDao.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundException("Student with id: " + id + "not found"));
    }

    @Transactional
    @Override
    public void save(Student student) {
        studentDao.save(student);
        log.info("{} saved", student);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        studentDao.findById(id)
                .ifPresentOrElse(
                        student -> {
                            studentDao.delete(student);
                            log.info("{} deleted", student);
                        },
                        () -> {
                            throw new StudentNotFoundException("Student with id: " + id + "not found");
                        }
                );
    }

    @Override
    public Page<StudentDto> findStudentsPageByGroupId(Long groupId, int pageNumber, int pageSize) {
        if (groupDao.findById(groupId).isEmpty()) {
            throw new GroupNotFoundException("Group with id: " + groupId + "not found");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return studentDao.findByGroupId(groupId, pageable).map(studentMapper::toDto);
    }

    @Override
    public List<StudentDto> findStudentsByGroupName(String groupName) {
        var group = groupDao.findByName(groupName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new GroupNotFoundException("Group with name: " + groupName + "not found"));

        return group.getStudents()
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void assignGroup(Long groupId, Long studentId) {
        var group = groupDao.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group with id: " + groupId + "not found"));

        var student = studentDao.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id: " + studentId + "not found"));

        if (!group.getStudents().contains(student)) {
            student.setGroup(group);
            group.getStudents().add(student);
            studentDao.save(student);
        }


    }

    @Override
    public StudentDto findByUserId(Long userId) {

        Student student = studentDao.findByUserId(userId).orElseThrow(
                () -> new StudentNotFoundException("Student with user id: " + userId + "not found")
        );
        return studentMapper.toDto(student);
    }

    @Override
    public Page<StudentDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return studentDao.findAll(pageable).map(studentMapper::toDto);
    }

    @Transactional
    @Override
    public Student registerStudent(StudentRegistrationDto studentRegistrationDto) {
        Role studentRole = Role.ROLE_STUDENT;

        studentRegistrationDto.getUser().setRole(studentRole);

        Student student = new Student();
        User user = userService.registerUser(studentRegistrationDto.getUser());

        student.setUser(user);
        studentDao.save(student);

        assignGroup(studentRegistrationDto.getGroupId(), student.getId());
        return student;
    }

    @Override
    public StudentUpdateDto getStudentUpdateDto(Long id) {
        Student student = studentDao.findById(id).orElseThrow(() -> new StudentNotFoundException("Student with id" + id + "not found"));

        return studentUpdateMapper.toDto(student);
    }

    @Transactional
    @Override
    public void update(StudentUpdateDto studentUpdateDto) {

        Long studentId = studentUpdateDto.getId();

        Student student = studentDao.findById(studentUpdateDto.getId())
                .orElseThrow(
                        () -> new StudentNotFoundException("Student with id " + studentId + " not found")
                );

        Long groupId = studentUpdateDto.getGroupId();


        assignGroup(groupId, studentId);
        student.getUser().setEmail(studentUpdateDto.getUser().getEmail());
        student.getUser().setFirstName(studentUpdateDto.getUser().getFirstName());
        student.getUser().setLastName(studentUpdateDto.getUser().getLastName());
        student.getUser().setDateOfBirth(studentUpdateDto.getUser().getDateOfBirth());
        student.getUser().setPhoneNumber(studentUpdateDto.getUser().getPhoneNumber());
    }

    @Override
    public Student getCurrentStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        Long studentId = findByUserId(userId).getId();
        return studentDao.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException("Student with id " + studentId + "not found")
        );
    }


}


