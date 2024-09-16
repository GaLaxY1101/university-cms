package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.AdminDto;
import com.foxminded.korniichyk.university.dto.registration.AdminRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.model.Admin;

public interface AdminService extends CrudService<Admin, AdminDto>{

    void registerAdmin(AdminRegistrationDto adminRegistrationDto);

    AdminUpdateDto getAdminUpdateDto(Long adminId);

    void update(AdminUpdateDto adminUpdateDto);
}
