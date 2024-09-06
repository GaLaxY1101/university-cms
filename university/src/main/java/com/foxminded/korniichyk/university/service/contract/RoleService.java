package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.RoleDto;
import com.foxminded.korniichyk.university.model.Role;

public interface RoleService extends CrudService<Role, RoleDto> {

    Role findByName(String name);

}
