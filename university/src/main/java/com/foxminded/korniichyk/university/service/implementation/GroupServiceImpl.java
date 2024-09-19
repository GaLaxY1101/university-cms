package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.SpecialityDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.registration.GroupRegistrationDto;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.projection.input.InputOptionProjection;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.SpecialityNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.service.exception.TeacherNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {


    private final GroupDao groupDao;
    private final GroupMapper groupMapper;
    private final StudentDao studentDao;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SpecialityDao specialityDao;

    @Override
    public GroupDto findById(Long id) {
        return groupDao.findById(id)
                .map(groupMapper::toDto)
                .orElseThrow(() -> new GroupNotFoundException("Teacher with id " + id + " not found"));
    }

    @Transactional
    @Override
    public void save(Group group) {
        groupDao.save(group);
        log.info("{} saved", group);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        groupDao.findById(id)
                .ifPresentOrElse(
                        group -> {
                            groupDao.delete(group);
                            log.info("{} deleted", group);
                        },
                        () -> {
                            throw new GroupNotFoundException("Teacher with id " + id + " not found");
                        }
                );
    }

    @Override
    public List<GroupDto> findByName(String name) {
        return groupDao.findByName(name).stream().map(groupMapper::toDto).collect(toList());
    }

    @Override
    public GroupDto findByStudentId(Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException("Student with id " + studentId + " not found")
        );
        return groupMapper.toDto(student.getGroup());
    }


    @Override
    public Page<GroupDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return groupDao.findAll(pageable).map(groupMapper::toDto);
    }

    @Override
    public Page<GroupDto> findPageByTeacherId(Long teacherId, int pageNumber, int pageSize) {
        if (!teacherService.isExistsById(teacherId)) {
            throw new TeacherNotFoundException("Teacher with id " + teacherId + " not found");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Group> groupPage = groupDao.findByTeacherId(teacherId, pageable);

        List<GroupDto> groups = groupPage.getContent()
                .stream()
                .map(groupMapper::toDto)
                .toList();

        return new PageImpl<GroupDto>(groups, pageable, groups.size());

    }

    @Override
    public boolean isExistsById(Long id) {
        return groupDao.existsById(id);
    }

    @Override
    public List<GroupDto> findAll() {
        return groupDao.findAll()
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InputOptionProjection> findAllGroupOptions() {
        return groupDao.findAllGroupOptions();
    }


    @Transactional
    @Override
    public Group registerGroup(GroupRegistrationDto groupRegistrationDto) {
        Group group = new Group();
        group.setName(groupRegistrationDto.getName());

        Set<Student> students = studentService.findAllByIdIn(groupRegistrationDto.getStudentsIds());
        group.setStudents(students);

        save(group);

        Long specialityId = groupRegistrationDto.getSpecialityId();
        Long groupId = group.getId();

        assignSpeciality(specialityId, groupId);
        return group;
    }

    @Transactional
    @Override
    public void assignSpeciality(Long specialityId, Long groupId) {
        Speciality speciality = specialityDao.findById(specialityId)
                .orElseThrow(() -> new SpecialityNotFoundException("Speciality with id " + specialityId + "not found"));

        Group group = groupDao.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group with id " + groupId + "not found"));

        group.setSpeciality(speciality);
        speciality.addGroup(group);

    }

    @Override
    public boolean isExistsByName(String name) {
        return groupDao.existsByName(name);
    }
}
