package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.registration.GroupRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.GroupUpdateDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService extends CrudService<Group, GroupDto>, SearchableByName<GroupDto> {

    GroupDto findByStudentId(Long studentId);


    Page<GroupDto> findByTeacherId(Long teacherId, Pageable pageable);

    boolean isExistsById(Long id);

    List<GroupDto> findAll();

    List<NameProjection> findAllNameProjections();

    Group registerGroup(GroupRegistrationDto groupRegistrationDto);

    void assignSpeciality(Long specialityId, Long groupId);

    boolean isExistsByName(String name);

    GroupUpdateDto getGroupUpdateDtoById(Long groupId);

    void update(GroupUpdateDto groupUpdateDto);

    String getNameById(Long id);

    List<GroupDto> findAllByName(String name);

    Page<GroupDto> findByNameAndTeacherId(Long teacherId, String name, Pageable pageable);

}
