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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

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

        verify(userDao).findById(anyLong());
    }

    @Test
    void findById_shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(anyLong()));
    }



    @Test
    void delete_shouldDeleteUser_whenUserExists() {
        when(userDao.findById(anyLong())).thenReturn(Optional.of(new User()));
        doNothing().when(userDao).delete(any(User.class));
        userService.deleteById(1L);

        verify(userDao, times(1)).delete(any(User.class));
    }

    @Test
    void delete_shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteById(anyLong()));
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
