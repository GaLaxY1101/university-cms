package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.registration.StudentRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.mapper.update.StudentUpdateMapper;
import com.foxminded.korniichyk.university.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService extends CrudService<Student, StudentDto> {

    Page<StudentDto> findStudentsPageByGroupId(Long groupId, int pageNumber, int pageSize);
    List<StudentDto> findStudentsByGroupName(String groupName);
    void assignGroup(Long groupId, Long studentId);
    Page<StudentDto> findPage(int pageNumber, int pageSize);
    StudentDto findByUserId(Long userId);
    Student registerStudent(StudentRegistrationDto studentRegistrationDto);
    StudentUpdateDto getStudentUpdateDto(Long id);
    void update(StudentUpdateDto studentUpdateDto);
    Student getCurrentStudent();
}
