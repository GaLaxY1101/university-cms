package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.SpecialityDao;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.service.exception.SpecialityNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.SpecialityServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.SpecialityMapper;
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
public class SpecialityServiceUnitTests {

    @InjectMocks
    private SpecialityServiceImpl specialityService;

    @Mock
    private SpecialityDao specialityDao;

    @Mock
    private SpecialityMapper specialityMapper;

    @Test
    void findById_shouldReturnSpeciality() {
        when(specialityDao.findById(anyLong())).thenReturn(Optional.of(new Speciality()));
        when(specialityMapper.toDto(any(Speciality.class))).thenReturn(new SpecialityDto());

        specialityService.findById(1L);

        verify(specialityDao).findById(1L);
    }

    @Test
    void findById_shouldThrowSpecialityNotFoundException_whenSpecialityNotFound() {
        when(specialityDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(SpecialityNotFoundException.class, () -> specialityService.findById(1L));
    }

    @Test
    void delete_shouldDeleteSpeciality_ifExists() {
        when(specialityDao.findById(anyLong())).thenReturn(Optional.of(new Speciality()));

        specialityService.delete(1L);

        verify(specialityDao).findById(anyLong());
        verify(specialityDao).delete(any(Speciality.class));
    }

    @Test
    void delete_shouldThrowSpecialityNotFoundException_whenSpecialityNotFound() {
        when(specialityDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(SpecialityNotFoundException.class, () -> specialityService.delete(1L));
    }
}
