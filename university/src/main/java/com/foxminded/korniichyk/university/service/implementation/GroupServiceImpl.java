package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.SpecialityDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.registration.GroupRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.GroupUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
import com.foxminded.korniichyk.university.mapper.update.GroupUpdateMapper;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
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
    private final GroupUpdateMapper groupUpdateMapper;

    @Override
    public GroupDto findById(Long id) {
        return groupDao.findById(id)
                .map(groupMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Group with id {} not found", id);
                    return new GroupNotFoundException("Group not found");
                });
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
                            log.info("Group with id {} deleted", id);
                        },
                        () -> {
                            log.error("Group with id {} not found", id);
                            throw new GroupNotFoundException("Group not found");
                        }
                );
    }

    @Override
    public GroupDto findByStudentId(Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> {
            log.error("Student with id {} not found", studentId);
            return new StudentNotFoundException("Student not found");
        });
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
            log.error("Teacher with id {} not found", teacherId);
            throw new TeacherNotFoundException("Teacher not found");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Group> groupPage = groupDao.findByTeacherId(teacherId, pageable);

        List<GroupDto> groups = groupPage.getContent()
                .stream()
                .map(groupMapper::toDto)
                .toList();

        return new PageImpl<>(groups, pageable, groupPage.getTotalElements());
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
                .collect(toList());
    }

    @Override
    public List<NameProjection> findAllNameProjections() {
        return groupDao.findAllGroupOptions();
    }


    @Transactional
    @Override
    public Group registerGroup(GroupRegistrationDto groupRegistrationDto) {
        Group group = new Group();
        group.setName(groupRegistrationDto.getName());
        save(group);

        Long specialityId = groupRegistrationDto.getSpecialityId();
        Long groupId = group.getId();

        assignSpeciality(specialityId, groupId);
        return group;
    }

    @Transactional
    @Override
    public void assignSpeciality(Long specialityId, Long groupId) {
        if (!specialityDao.existsById(specialityId)) {
            log.error("Speciality with id {} not found", specialityId);
            throw new SpecialityNotFoundException("Speciality not found");
        }

        if (!groupDao.existsById(groupId)) {
            log.error("Group with id {} not found", groupId);
            throw new GroupNotFoundException("Group not found");
        }

        Speciality speciality = specialityDao.findReferenceById(specialityId);
        Group group = groupDao.findReferenceById(groupId);

        group.setSpeciality(speciality);
    }


    @Override
    public boolean isExistsByName(String name) {
        return groupDao.existsByName(name);
    }

    @Override
    public GroupUpdateDto getGroupUpdateDtoById(Long groupId) {
        Group group = groupDao.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Group with id {} not found", groupId);
                    return new GroupNotFoundException("Group not found");
                });

        return groupUpdateMapper.toDto(group);
    }


    @Transactional
    @Override
    public void update(GroupUpdateDto groupUpdateDto) {
        Long groupId = groupUpdateDto.getId();
        Group group = groupDao.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Group with id {} not found", groupId);
                    return new GroupNotFoundException("Group not found");
                });

        Long specialityId = groupUpdateDto.getSpecialityId();
        Speciality speciality = specialityDao.findById(specialityId)
                .orElseThrow(() -> {
                    log.error("Speciality with id {} not found", specialityId);
                    return new SpecialityNotFoundException("Speciality not found");
                });

        group.setName(groupUpdateDto.getName());
        group.setSpeciality(speciality);
    }


    @Override
    public String getNameById(Long id) {
        return groupDao.getNameById(id);
    }

    @Override
    public Page<GroupDto> findByName(String name, Pageable pageable) {
        return groupDao.findByNameContainingIgnoreCase(name, pageable)
                .map(groupMapper::toDto);
    }

    @Override
    public List<GroupDto> findAllByName(String name) {
        return groupDao.findAllByName(name)
                .stream()
                .map(groupMapper::toDto)
                .collect(toList());
    }


}
