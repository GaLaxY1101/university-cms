package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.option.StudentOptionDto;
import com.foxminded.korniichyk.university.dto.registration.StudentRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.projection.edit.group.StudentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface StudentService extends CrudService<Student, StudentDto> {

    List<StudentDto> findStudentsByGroupName(String groupName);
    void assignGroup(Long groupId, Long studentId);
    Page<StudentDto> findPage(int pageNumber, int pageSize);
    StudentDto findByUserId(Long userId);
    Student registerStudent(StudentRegistrationDto studentRegistrationDto);
    StudentUpdateDto getStudentUpdateDto(Long id);
    void update(StudentUpdateDto studentUpdateDto);
    Student getCurrentStudent();
    Page<StudentDto> findByGroupIdExcludingByStudentId(Long groupId, Long studentId, Pageable pageable);
    boolean isExistsById(Long id);
    Set<Student> findAllByIdIn(Set<Long> studentIds);
    Page<StudentProjection> findStudentsByGroupId(Long groupId, Pageable pageable);
    void unassignGroup(Long studentId);
    List<StudentOptionDto> findAllStudentOptionsWithoutGroup();

}
