package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.RoleDao;
import com.foxminded.korniichyk.university.dto.display.RoleDto;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.service.exception.RoleNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.RoleServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.RoleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceUnitTests {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleDao roleDao;

    @Mock
    private RoleMapper roleMapper;

    @Test
    void findById_shouldReturnRole() {
        when(roleDao.findById(anyLong())).thenReturn(Optional.of(new Role()));
        when(roleMapper.toDto(any(Role.class))).thenReturn(new RoleDto());

        roleService.findById(1L);

        verify(roleDao).findById(1L);
    }

    @Test
    void findById_shouldThrowRoleNotFoundException_whenRoleNotFound() {
        when(roleDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class, () -> roleService.findById(1L));
    }

    @Test
    void delete_shouldDeleteRole_ifExists() {
        when(roleDao.findById(anyLong())).thenReturn(Optional.of(new Role()));

        roleService.deleteById(1L);

        verify(roleDao).findById(anyLong());
        verify(roleDao).delete(any(Role.class));
    }

    @Test
    void delete_shouldThrowRoleNotFoundException_whenRoleNotFound() {
        when(roleDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class, () -> roleService.deleteById(1L));
    }

    @Test
    void findByName_shouldReturnRole() {
        when(roleDao.findByName(any(String.class))).thenReturn(new Role());

        roleService.findByName("ADMIN");

        verify(roleDao).findByName("ADMIN");
    }

    @Test
    void findByName_shouldThrowRoleNotFoundException_whenRoleNotFound() {
        when(roleDao.findByName(any(String.class))).thenReturn(null);

        assertThrows(RoleNotFoundException.class, () -> roleService.findByName("ADMIN"));
    }
}
