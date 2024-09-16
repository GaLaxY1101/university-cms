package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.model.Group;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService extends CrudService<Group, GroupDto> {

    List<GroupDto> findByName(String name);

    GroupDto findByStudentId(Long studentId);


    Page<GroupDto> findPageByTeacherId(Long teacherId, int pageNumber, int pageSize);

    boolean isExistsById(Long id);
}
