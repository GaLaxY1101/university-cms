package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.DisciplineDao;
import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.service.exception.DisciplineNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.DisciplineServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.DisciplineMapper;
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
public class DisciplineServiceUnitTests {

    @InjectMocks
    private DisciplineServiceImpl disciplineService;

    @Mock
    private DisciplineDao disciplineDao;

    @Mock
    private DisciplineMapper disciplineMapper;

    @Test
    void findById_shouldReturnDiscipline_whenDisciplineExists() {
        Long disciplineId = 1L;
        Discipline discipline = new Discipline();
        DisciplineDto disciplineDto = new DisciplineDto();

        when(disciplineDao.findById(anyLong())).thenReturn(Optional.of(discipline));
        when(disciplineMapper.toDto(any(Discipline.class))).thenReturn(disciplineDto);

        DisciplineDto result = disciplineService.findById(disciplineId);

        verify(disciplineDao).findById(disciplineId);
        assertEquals(disciplineDto, result);
    }

    @Test
    void findById_shouldThrowDisciplineNotFoundException_whenDisciplineNotFound() {
        when(disciplineDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DisciplineNotFoundException.class, () -> disciplineService.findById(1L));
    }


    @Test
    void delete_shouldDeleteDiscipline_ifExists() {
        Long disciplineId = 1L;
        Discipline discipline = new Discipline();

        when(disciplineDao.findById(anyLong())).thenReturn(Optional.of(discipline));

        disciplineService.delete(disciplineId);

        verify(disciplineDao).findById(disciplineId);
        verify(disciplineDao).delete(discipline);
    }

    @Test
    void delete_shouldThrowDisciplineNotFoundException_whenDisciplineNotFound() {
        when(disciplineDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DisciplineNotFoundException.class, () -> disciplineService.delete(1L));
    }

}
