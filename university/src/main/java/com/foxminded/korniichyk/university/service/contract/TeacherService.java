package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.dto.registration.TeacherRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.TeacherUpdateDto;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.model.Teacher;

public interface TeacherService extends CrudService<Teacher, TeacherDto>{

    void addDiscipline(Long teacherId, Long disciplineId);
    void removeDiscipline(Long teacherId, Long disciplineId);
    void addLesson(Long teacherId, Long lessonId);
    void removeLesson(Long teacherId, Long disciplineId);
    Teacher findByUserId(Long userId);
    Teacher registerTeacher(TeacherRegistrationDto registrationDto);
    TeacherUpdateDto getTeacherUpdateDto(Long id);
    void update(TeacherUpdateDto teacherUpdateDto);
    Teacher getCurrentTeacher();
    boolean isExistsById(Long id);
}
