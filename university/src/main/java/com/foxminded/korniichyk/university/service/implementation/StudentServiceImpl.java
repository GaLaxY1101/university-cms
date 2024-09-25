package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.option.StudentOptionDto;
import com.foxminded.korniichyk.university.dto.registration.StudentRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.StudentMapper;
import com.foxminded.korniichyk.university.mapper.update.StudentUpdateMapper;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.projection.edit.group.StudentProjection;
import com.foxminded.korniichyk.university.security.CustomUserDetails;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final GroupDao groupDao;
    private final StudentDao studentDao;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final StudentUpdateMapper studentUpdateMapper;

    @Override
    public StudentDto findById(Long id) {
        return studentDao.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Student with id: {} not found", id);
                    return new StudentNotFoundException("No such student found.");
                });
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
                            log.error("Student with id: {} not found", id);
                            throw new StudentNotFoundException("No such student.");
                        }
                );
    }


    @Override
    public List<StudentDto> findStudentsByGroupName(String groupName) {
        var group = groupDao.findByName(groupName)
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                            log.error("Group with name: {} not found", groupName);
                            throw new GroupNotFoundException("No such group found");
                        }
                );

        return group.getStudents()
                .stream()
                .map(studentMapper::toDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public void assignGroup(Long groupId, Long studentId) {

        if (!groupDao.existsById(groupId)) {
            log.error("Group with id {} not found", groupId);
            throw new GroupNotFoundException("No such group found");
        }

        if (!studentDao.existsById(studentId)) {
            log.error("Student with id: {} not found", studentId);
            throw new StudentNotFoundException("No such student found");
        }

        Group group = groupDao.findReferenceById(groupId);

        Student student = studentDao.findReferenceById(studentId);


        if (!group.getStudents().contains(student)) {
            student.setGroup(group);
        }

    }

    @Override
    public StudentDto findByUserId(Long userId) {

        Student student = studentDao.findByUserId(userId).orElseThrow(
                () -> {
                    log.error("Student with user id: {} not found", userId);
                    throw new StudentNotFoundException("No such user found");
                }
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

        if (!(studentRegistrationDto.getGroupId() == null)) {
            assignGroup(studentRegistrationDto.getGroupId(), student.getId());
        }
        return student;
    }

    @Override
    public StudentUpdateDto getStudentUpdateDto(Long id) {
        Student student = studentDao.findById(id).orElseThrow(
                () -> {
                    log.error("Student with id {} not found", id);
                    throw new StudentNotFoundException("No such student found");
                }
        );

        return studentUpdateMapper.toDto(student);
    }

    @Transactional
    @Override
    public void update(StudentUpdateDto studentUpdateDto) {

        Long studentId = studentUpdateDto.getId();

        Student student = studentDao.findById(studentUpdateDto.getId())
                .orElseThrow(
                        () -> {
                            log.error("Student with id {} not found", studentId);
                            throw new StudentNotFoundException("No such student found.");
                        }
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
        return studentDao.findById(studentId).orElseThrow(() -> {
            log.error("Student with id {} not found", studentId);
            throw new StudentNotFoundException("No such student found.");
        });
    }

    @Override
    public Page<StudentDto> findByGroupIdExcludingByStudentId(Long groupId, Long studentId, Pageable pageable) {

        if (!groupDao.existsById(groupId)) {
            log.error("Group with id {} not found", groupId);
            throw new GroupNotFoundException("Group not found");
        }

        if (!isExistsById(studentId)) {
            log.error("Student with id {} not found", studentId);
            throw new StudentNotFoundException("Student not found");
        }

        return studentDao.findByGroupIdExcludingByStudentId(groupId, studentId, pageable)
                .map(studentMapper::toDto);
    }

    @Override
    public boolean isExistsById(Long id) {
        return studentDao.existsById(id);
    }

    @Override
    public Set<Student> findAllByIdIn(Set<Long> studentIds) {
        return studentDao.findAllByIdIn(studentIds);
    }

    @Override
    public Page<StudentProjection> findStudentsByGroupId(Long groupId, Pageable pageable) {
        return studentDao.findStudentsByGroupId(groupId, pageable);
    }

    @Transactional
    @Override
    public void unassignGroup(Long studentId) {
        if (!studentDao.existsById(studentId)) {
            log.error("Student with id {} not found", studentId);
            throw new StudentNotFoundException("Student not found");
        }


        Student student = studentDao.findReferenceById(studentId);
        student.setGroup(null);
    }

    @Override
    public List<StudentOptionDto> findAllStudentOptionsWithoutGroup() {
        return studentDao.findAllStudentOptionsWithoutGroup()
                .stream()
                .map((projection) ->
                        new StudentOptionDto(projection.getId(),
                                projection.getFirstName(),
                                projection.getLastName())

                ).collect(toList());
    }


}


