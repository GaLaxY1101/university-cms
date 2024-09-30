package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.GroupServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTests {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupDao groupDao;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private StudentDao studentDao;

    @Test
    void findById_shouldReturnGroupDto_whenGroupExists() {
        Long groupId = 1L;
        Group group = new Group();
        GroupDto groupDto = new GroupDto();

        when(groupDao.findById(anyLong())).thenReturn(Optional.of(group));
        when(groupMapper.toDto(any(Group.class))).thenReturn(groupDto);

        GroupDto result = groupService.findById(groupId);

        verify(groupDao).findById(groupId);
        assertEquals(groupDto, result);
    }

    @Test
    void findById_shouldThrowGroupNotFoundException_whenGroupNotFound() {
        when(groupDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(GroupNotFoundException.class, () -> groupService.findById(1L));
    }


    @Test
    void delete_shouldDeleteGroup_ifExists() {
        Long groupId = 1L;
        Group group = new Group();

        when(groupDao.findById(anyLong())).thenReturn(Optional.of(group));

        groupService.deleteById(groupId);

        verify(groupDao).findById(groupId);
        verify(groupDao).delete(group);
    }

    @Test
    void delete_shouldThrowGroupNotFoundException_whenGroupNotFound() {
        when(groupDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(GroupNotFoundException.class, () -> groupService.deleteById(1L));
    }

    @Test
    void findByName_shouldReturnListOfGroups() {
        String name = "Group A";
        Group group = new Group();
        GroupDto groupDto = new GroupDto();
        List<Group> groups = List.of(group);
        List<GroupDto> groupDtos = List.of(groupDto);

        when(groupDao.findByNameContainingIgnoreCase(name)).thenReturn(groups);
        when(groupMapper.toDto(any(Group.class))).thenReturn(groupDto);

        List<GroupDto> result = groupService.findByName(name);

        verify(groupDao).findByNameContainingIgnoreCase(name);
        assertEquals(groupDtos, result);
    }

    @Test
    void findByStudentId_shouldReturnGroupDto_whenStudentExists() {
        Long studentId = 1L;
        Student student = new Student();
        Group group = new Group();
        GroupDto groupDto = new GroupDto();

        student.setGroup(group);

        when(studentDao.findById(anyLong())).thenReturn(Optional.of(student));
        when(groupMapper.toDto(any(Group.class))).thenReturn(groupDto);

        GroupDto result = groupService.findByStudentId(studentId);

        verify(studentDao).findById(studentId);
        assertEquals(groupDto, result);
    }

    @Test
    void findByStudentId_shouldThrowStudentNotFoundException_whenStudentNotFound() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> groupService.findByStudentId(1L));
    }
}
