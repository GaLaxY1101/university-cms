package com.foxminded.korniichyk.university.service.unit;


import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.service.exception.GroupAlreadyAssignedException;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.StudentServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.StudentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceUnitTests {

    @Mock
    private StudentDao studentDao;

    @Mock
    private GroupDao groupDao;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void findById_shouldReturnStudent_whenStudentExists() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.of(new Student()));
        when(studentMapper.toDto(any())).thenReturn(new StudentDto());
        assertNotNull(studentService.findById(1L));
    }

    @Test
    void findById_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.findById(1L));
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoStudents() {
        when(studentDao.findAll()).thenReturn(new ArrayList<>());

        assertEquals(0, studentService.findAll().size());
        verify(studentDao, times(1)).findAll();
    }

    @Test
    void findAll_shouldReturnStudents_whenStudentsPresented() {
        when(studentDao.findAll()).thenReturn(List.of(new Student(), new Student()));

        assertEquals(2, studentService.findAll().size());
        verify(studentDao, times(1)).findAll();
    }

    @Test
    void save_shouldSaveStudent() {
        when(studentDao.save(any(Student.class))).thenReturn(new Student());
        studentService.save(new Student());
        verify(studentDao, times(1)).save(any(Student.class));
    }

    @Test
    void delete_shouldDeleteStudentIfPresent() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.of(new Student()));

        studentService.delete(1L);

        verify(studentDao, times(1)).findById(anyLong());
        verify(studentDao, times(1)).delete(any(Student.class));
    }

    @Test
    void delete_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.delete(1L));
        verify(studentDao, times(1)).findById(anyLong());
        verify(studentDao, times(0)).delete(any(Student.class));
    }

    @Test
    void findStudentsPageByGroupId_shouldReturnStudentsPage_whenNonEmptyGroupExists() {

        Pageable pageable = PageRequest.of(1, 5);

        when(groupDao.findById(anyLong())).thenReturn(Optional.of(new Group()));
        when(studentDao.findByGroupId(1L, pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Student())));
        when(studentMapper.toDto(new Student())).thenReturn(new StudentDto());

        Page<StudentDto> result = studentService.findStudentsPageByGroupId(1L, 1, 5);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getContent().size());


        verify(studentDao, times(1)).findByGroupId(anyLong(), any(Pageable.class));
        verify(groupDao, times(1)).findById(anyLong());
    }

    @Test
    void findStudentsPageByGroupId_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {
        when(groupDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(GroupNotFoundException.class, () -> studentService.findStudentsPageByGroupId(1L, 1, 5));
        verify(groupDao, times(1)).findById(anyLong());
        verify(studentDao, times(0)).findByGroupId(anyLong(), any(Pageable.class));

    }

    @Test
    void findStudentsByGroupName_shouldThrowGroupNotFoundException_whenGroupDoesntExists() {
        when(groupDao.findByName(anyString())).thenReturn(Collections.emptyList());
        assertThrows(GroupNotFoundException.class, () -> studentService.findStudentsByGroupName(anyString()));
    }

    @Test
    void findStudentsByGroupName_shouldReturnStudents_whenGroupExists() {
        Group group = mock(Group.class);

        when(groupDao.findByName(anyString())).thenReturn(List.of(group));
        when(group.getStudents()).thenReturn(Set.of(new Student()));
        when(studentMapper.toDto(new Student())).thenReturn(new StudentDto());
        assertEquals(1, studentService.findStudentsByGroupName(anyString()).size());
    }


    @Test
    void assignGroup_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {
        when(groupDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(GroupNotFoundException.class, () -> studentService.assignGroup(1L, 1L));
    }

    @Test
    void assignGroup_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        when(groupDao.findById(1L)).thenReturn(Optional.of(new Group()));
        when(studentDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.assignGroup(1L, 1L));
    }

    @Test
    void assignGroup_shouldThrowGroupAlreadyAssignedException_whenGroupAlreadyAssignedToStudent() {

        Long groupId = 1L;
        Long studentId = 1L;
        Group group = new Group();
        Student student = new Student();
        group.getStudents().add(student);

        when(groupDao.findById(groupId)).thenReturn(Optional.of(group));
        when(studentDao.findById(studentId)).thenReturn(Optional.of(student));

        assertThrows(GroupAlreadyAssignedException.class, () -> studentService.assignGroup(groupId, studentId));
        verify(studentDao, never()).save(any(Student.class));

    }

    @Test
    void assignGroup_shouldAssignGroupToStudent_whenGroupAndStudentExistsAndNotAssigned() {
        Long groupId = 1L;
        Long studentId = 1L;
        Group group = new Group();
        Student student = new Student();

        when(groupDao.findById(groupId)).thenReturn(Optional.of(group));
        when(studentDao.findById(studentId)).thenReturn(Optional.of(student));

        studentService.assignGroup(groupId, studentId);

        verify(studentDao, times(1)).save(student);
    }

    @Test
    void findByUserId_shouldThrowStudentNotFoundException_whenUserDoesNotExist() {
        assertThrows(StudentNotFoundException.class, () -> studentService.findByUserId(1L));
    }

    @Test
    void findByUserId_shouldReturnStudent_whenUserExists() {
        Long userId = 1L;
        when(studentDao.findByUserId(userId)).thenReturn(Optional.of(new Student()));
        when(studentMapper.toDto(new Student())).thenReturn(new StudentDto());

        studentService.findByUserId(userId);

        verify(studentDao, times(1)).findByUserId(userId);
    }

    @Test
    void findPage_shouldReturnEmptyPage_whenNoStudentsExist() {
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Student> studentPage = Page.empty(pageable);

        when(studentDao.findAll(pageable)).thenReturn(studentPage);

        Page<StudentDto> result = studentService.findPage(pageNumber, pageSize);

        assertEquals(0, result.getContent().size());
        verify(studentDao, times(1)).findAll(pageable);
        verify(studentMapper, never()).toDto(any(Student.class));
    }

}
