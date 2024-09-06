package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dao.TeacherDao;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.service.exception.TeacherNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {


    private final GroupDao groupDao;
    private final GroupMapper groupMapper;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao,
                            GroupMapper groupMapper,
                            StudentDao studentDao,
                            TeacherDao teacherDao
    ) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
    }

    @Transactional
    @Override
    public GroupDto findById(Long id) {
        return groupDao.findById(id)
                .map(groupMapper::toDto)
                .orElseThrow(() -> new GroupNotFoundException("Teacher with id " + id + " not found"));
    }

    @Transactional
    @Override
    public List<GroupDto> findAll() {
        return groupDao.findAll()
                .stream()
                .map(groupMapper::toDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public void save(Group group) {
        groupDao.save(group);
        log.info("{} saved", group);
    }

    @Transactional
    @Override
    public void delete(Long id) {
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

    @Transactional
    public List<GroupDto> findByName(String name) {
        return groupDao.findByName(name).stream().map(groupMapper::toDto).collect(toList());
    }

    @Transactional
    @Override
    public GroupDto findByStudentId(Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException("Student with id " + studentId + " not found")
        );
        return groupMapper.toDto(student.getGroup());
    }

    @Transactional
    @Override
    public Page<GroupDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return groupDao.findAll(pageable).map(groupMapper::toDto);
    }

    @Transactional
    @Override
    public Page<GroupDto> findPageByTeacherId(Long teacherId, int pageNumber, int pageSize) {
        if (teacherDao.findById(teacherId).isEmpty()) {
            throw new TeacherNotFoundException("Teacher with id " + teacherId + " not found");
        }

        var groups = groupDao.findByTeacherId(teacherId)
                .stream()
                .map(groupMapper::toDto)
                .toList();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), groups.size());

        return new PageImpl<>(groups.subList(start, end), pageable, groups.size());
    }
}
