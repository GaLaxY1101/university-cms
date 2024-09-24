package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.DisciplineDao;
import com.foxminded.korniichyk.university.dao.LessonDao;
import com.foxminded.korniichyk.university.dao.TeacherDao;
import com.foxminded.korniichyk.university.dao.UserDao;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.dto.registration.TeacherRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.TeacherUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.TeacherMapper;
import com.foxminded.korniichyk.university.mapper.update.TeacherUpdateMapper;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.security.CustomUserDetails;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.AdminNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.service.exception.TeacherNotFoundException;
import com.foxminded.korniichyk.university.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {


    private final LessonDao lessonDao;
    private final TeacherDao teacherDao;
    private final DisciplineDao disciplineDao;
    private final DisciplineService disciplineService;
    private final UserDao userDao;
    private final UserService userService;
    private final TeacherMapper teacherMapper;
    private final TeacherUpdateMapper teacherUpdateMapper;

    @Override
    public TeacherDto findById(Long id) {
        return teacherDao.findById(id)
                .map(teacherMapper::toDto)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id: " + id + " not found"));
    }

    @Transactional
    @Override
    public void save(Teacher teacher) {
        teacherDao.save(teacher);
        log.info("{} saved", teacher);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        teacherDao.findById(id)
                .ifPresentOrElse(
                        teacher -> {
                            teacherDao.delete(teacher);
                            log.info("{} deleted", teacher);
                        },
                        () -> {
                            throw new TeacherNotFoundException("Teacher with id: " + id + " not found");
                        });
    }

    @Override
    public Teacher findByUserId(Long userId) {
        if (userDao.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        return teacherDao.findByUserId(userId);
    }


    @Override
    public Page<TeacherDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return teacherDao.findAll(pageable).map(teacherMapper::toDto);
    }

    @Transactional
    @Override
    public Teacher registerTeacher(TeacherRegistrationDto teacherRegistrationDto) {

        Role teacherRole = Role.ROLE_TEACHER;

        teacherRegistrationDto.getUser().setRole(teacherRole);

        Teacher teacher = new Teacher();

        User user = userService.registerUser(teacherRegistrationDto.getUser());

        teacher.setUser(user);

        teacher.setDisciplines(disciplineDao.findAllByIdIn(teacherRegistrationDto.getDisciplineIds()));
        teacherDao.save(teacher);
        return teacher;
    }

    @Override
    public TeacherUpdateDto getTeacherUpdateDto(Long id) {

        Teacher teacher = teacherDao.findById(id).orElseThrow(() -> new AdminNotFoundException("Admin with id " + id + " not found"));
        return teacherUpdateMapper.toDto(teacher);
    }

    @Transactional
    @Override
    public void update(TeacherUpdateDto teacherUpdateDto) {
        Long teacherId = teacherUpdateDto.getId();
        Teacher teacher = teacherDao.findById(teacherUpdateDto.getId()).orElseThrow(
                () -> new TeacherNotFoundException("Teacher with id: " + teacherId + " not found")
        );

        Set<Long> existingDisciplineIds = teacher.getDisciplines()
                .stream()
                .map(discipline -> discipline.getId())
                .collect(toSet());

        if (!existingDisciplineIds.equals(existingDisciplineIds)) {
            Set<Discipline> disciplines = disciplineService.findAllByIdIn(teacherUpdateDto.getDisciplineIds());
            teacher.setDisciplines(disciplines);
        }

        teacher.getUser().setEmail(teacherUpdateDto.getUser().getEmail());
        teacher.getUser().setFirstName(teacherUpdateDto.getUser().getFirstName());
        teacher.getUser().setLastName(teacherUpdateDto.getUser().getLastName());
        teacher.getUser().setDateOfBirth(teacherUpdateDto.getUser().getDateOfBirth());
        teacher.getUser().setPhoneNumber(teacherUpdateDto.getUser().getPhoneNumber());

    }

    @Override
    public Teacher getCurrentTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        Long teacherId = findByUserId(userId).getId();
        return teacherDao.findById(teacherId).orElseThrow(
                () -> new StudentNotFoundException("Teacher with id " + teacherId + "not found")
        );
    }

    @Override
    public boolean isExistsById(Long id) {
        return teacherDao.existsById(id);
    }

}
