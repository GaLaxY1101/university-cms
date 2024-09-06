package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.UserDao;
import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.mapper.display.UserMapper;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.service.exception.UserNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    UserDao userDao;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findById_shouldReturnUserIfExists() {
        when(userDao.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
        userService.findById(anyLong());

        verify(userDao, times(1)).findById(anyLong());
    }

    @Test
    void findById_shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(anyLong()));
    }


    @Test
    void findAll_shouldReturnUsers() {
        when(userDao.findAll()).thenReturn(List.of(new User(), new User()));
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());

        assertEquals(2, userService.findAll().size());

        verify(userDao, times(1)).findAll();
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoUsers() {
        when(userDao.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0, userService.findAll().size());

        verify(userDao, times(1)).findAll();
    }


    @Test
    void delete_shouldDeleteUser_whenUserExists() {
        when(userDao.findById(anyLong())).thenReturn(Optional.of(new User()));
        doNothing().when(userDao).delete(any(User.class));
        userService.delete(1L);

        verify(userDao, times(1)).delete(any(User.class));
    }

    @Test
    void delete_shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.delete(anyLong()));
    }

    @Test
    void findByEmail_shouldReturnUserIfExists() {
        when(userDao.findByEmail(anyString())).thenReturn(new User());
        userService.findByEmail(anyString());
        verify(userDao, times(1)).findByEmail(anyString());
    }

    @Test
    void findByEmail_shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> userService.findByEmail(anyString()));
    }

}
