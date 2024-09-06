package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.DisciplineDao;
import com.foxminded.korniichyk.university.dao.LessonDao;
import com.foxminded.korniichyk.university.dao.TeacherDao;
import com.foxminded.korniichyk.university.dao.UserDao;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.dto.registration.TeacherRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.TeacherUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.RoleMapper;
import com.foxminded.korniichyk.university.mapper.display.TeacherMapper;
import com.foxminded.korniichyk.university.mapper.update.TeacherUpdateMapper;
import com.foxminded.korniichyk.university.model.*;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.contract.RoleService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final LessonDao lessonDao;
    private final TeacherDao teacherDao;
    private final DisciplineDao disciplineDao;
    private final DisciplineService disciplineService;
    private final UserDao userDao;
    private final UserService userService;
    private final TeacherMapper teacherMapper;
    private final RoleService roleService;
    private final TeacherUpdateMapper teacherUpdateMapper;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao,
                              TeacherMapper teacherMapper,
                              DisciplineDao disciplineDao,
                              LessonDao lessonDao, DisciplineService disciplineService,
                              UserDao userDao,
                              UserService userService,
                              RoleService roleService,
                              RoleMapper roleMapper,
                              TeacherUpdateMapper teacherUpdateMapper
    ) {
        this.teacherDao = teacherDao;
        this.teacherMapper = teacherMapper;
        this.disciplineDao = disciplineDao;
        this.lessonDao = lessonDao;
        this.disciplineService = disciplineService;
        this.userDao = userDao;
        this.userService = userService;
        this.roleService = roleService;
        this.teacherUpdateMapper = teacherUpdateMapper;
    }

    @Transactional
    @Override
    public TeacherDto findById(Long id) {
        return teacherDao.findById(id)
                .map(teacherMapper::toDto)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id: " + id + " not found"));
    }

    @Transactional
    @Override
    public List<TeacherDto> findAll() {
        return teacherDao.findAll()
                .stream()
                .map(teacherMapper::toDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public void save(Teacher teacher) {
        teacherDao.save(teacher);
        log.info("{} saved", teacher);
    }

    @Transactional
    @Override
    public void delete(Long id) {
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

    @Transactional
    @Override
    public void addDiscipline(Long teacherId, Long disciplineId) {

        Teacher teacher = teacherDao.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id " + teacherId + " not found"));

        Discipline discipline = disciplineDao.findById(disciplineId)
                .orElseThrow(() -> new DisciplineNotFoundException("Discipline with id " + disciplineId + " not found"));

        if (teacher.getDisciplines().contains(discipline)) {
            throw new DisciplineAlreadyAssignedException(teacher + " already has " + discipline);
        }
        teacher.getDisciplines().add(discipline);
        discipline.getTeachers().add(teacher);
        teacherDao.save(teacher);

    }

    @Transactional
    @Override
    public void removeDiscipline(Long teacherId, Long disciplineId) {
        Teacher teacher = teacherDao.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id " + teacherId + " not found"));

        Discipline discipline = disciplineDao.findById(disciplineId)
                .orElseThrow(() -> new DisciplineNotFoundException("Discipline with id " + disciplineId + " not found"));

        if (!teacher.getDisciplines().contains(discipline)) {
            throw new DisciplineNotFoundException(teacher + " does not have " + discipline);
        }
        teacher.getDisciplines().remove(discipline);
        discipline.getTeachers().remove(teacher);
        teacherDao.save(teacher);
    }

    @Transactional
    @Override
    public void addLesson(Long teacherId, Long lessonId) {
        Teacher teacher = teacherDao.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id " + teacherId + " not found"));

        Lesson lesson = lessonDao.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with id " + lessonId + " not found"));

        if (teacher.getLessons().contains(lesson)) {
            throw new LessonAlreadyAssignedException(teacher + " already has " + lesson);
        }
        teacher.getLessons().add(lesson);
        lesson.getTeachers().add(teacher);
        teacherDao.save(teacher);
    }

    @Transactional
    @Override
    public void removeLesson(Long teacherId, Long lessonId) {
        Teacher teacher = teacherDao.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id " + teacherId + " not found"));

        Lesson lesson = lessonDao.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with id " + lessonId + " not found"));

        if (!teacher.getLessons().contains(lesson)) {
            throw new LessonNotFoundException(teacher + " does not have " + lesson);
        }
        teacher.getLessons().remove(lesson);
        lesson.getTeachers().remove(teacher);
        teacherDao.save(teacher);
    }

    @Override
    public Teacher findByUserId(Long userId) {
        if (userDao.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        return teacherDao.findByUserId(userId);
    }



    @Transactional
    @Override
    public Page<TeacherDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return teacherDao.findAll(pageable).map(teacherMapper::toDto);
    }

    @Override
    @Transactional
    public Teacher registerTeacher(TeacherRegistrationDto teacherRegistrationDto) {

        Role teacherRole = roleService.findByName("ROLE_TEACHER");

        teacherRegistrationDto.getUser().setRoles(Collections.singleton(teacherRole));

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

        Set<Discipline> disciplines = disciplineService.findAllByIdIn(teacherUpdateDto.getDisciplineIds());

        teacher.setDisciplines(disciplines);
        teacher.getUser().setEmail(teacherUpdateDto.getUser().getEmail());
        teacher.getUser().setFirstName(teacherUpdateDto.getUser().getFirstName());
        teacher.getUser().setLastName(teacherUpdateDto.getUser().getLastName());
        teacher.getUser().setDateOfBirth(teacherUpdateDto.getUser().getDateOfBirth());
        teacher.getUser().setPhoneNumber(teacherUpdateDto.getUser().getPhoneNumber());

    }

}
