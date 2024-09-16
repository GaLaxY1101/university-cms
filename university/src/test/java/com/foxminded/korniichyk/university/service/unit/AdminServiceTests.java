package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.AdminDao;
import com.foxminded.korniichyk.university.dto.display.AdminDto;
import com.foxminded.korniichyk.university.dto.registration.AdminRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.AdminMapper;
import com.foxminded.korniichyk.university.model.Admin;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.AdminNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.AdminServiceImpl;
import com.foxminded.korniichyk.university.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminDao adminDao;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private UserService userService;

    @Test
    void findById_shouldReturnAdmin_whenAdminExists() {
        Long adminId = 1L;
        Admin admin = new Admin();
        AdminDto adminDto = new AdminDto();

        when(adminDao.findById(anyLong())).thenReturn(Optional.of(admin));
        when(adminMapper.toDto(any(Admin.class))).thenReturn(adminDto);

        AdminDto result = adminService.findById(adminId);

        verify(adminDao).findById(adminId);
        assertEquals(adminDto, result);
    }

    @Test
    void findById_shouldThrowAdminNotFoundException_whenAdminNotFound() {
        when(adminDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AdminNotFoundException.class, () -> adminService.findById(1L));
    }

    @Test
    void delete_shouldDeleteAdmin_ifExists() {
        Long adminId = 1L;
        Admin admin = new Admin();

        when(adminDao.findById(anyLong())).thenReturn(Optional.of(admin));

        adminService.deleteById(adminId);

        verify(adminDao).findById(adminId);
        verify(adminDao).delete(admin);
    }

    @Test
    void delete_shouldThrowAdminNotFoundException_whenAdminNotFound() {
        when(adminDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AdminNotFoundException.class, () -> adminService.deleteById(1L));
    }

    @Test
    void registerAdmin_shouldInvokeRegisterUserAndSaveMethods() {

        User user = TestUtil.generateUser();
        user.setRole(Role.ROLE_ADMIN);

        when(userService.registerUser(any(UserRegistrationDto.class))).thenReturn(user);

        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        adminRegistrationDto.setUser(userRegistrationDto);

        adminService.registerAdmin(adminRegistrationDto);

        verify(userService).registerUser(userRegistrationDto);
        verify(adminDao).save(any(Admin.class));

    }

    @Test
    void update_shouldThrowAdminNotFoundException_whenAdminDoesntExists() {

        when(adminDao.findById(anyLong())).thenReturn(Optional.empty());

        AdminUpdateDto adminUpdateDto = new AdminUpdateDto();
        adminUpdateDto.setId(1L);

        assertThrows(AdminNotFoundException.class, () -> adminService.update(adminUpdateDto));

    }
}
