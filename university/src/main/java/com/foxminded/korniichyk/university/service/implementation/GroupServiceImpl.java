package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.StudentDao;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.service.exception.TeacherNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
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
}
