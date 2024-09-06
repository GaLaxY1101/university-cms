package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.AdminDao;
import com.foxminded.korniichyk.university.dto.display.AdminDto;
import com.foxminded.korniichyk.university.dto.registration.AdminRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.mapper.display.AdminMapper;
import com.foxminded.korniichyk.university.mapper.update.AdminUpdateMapper;
import com.foxminded.korniichyk.university.model.Admin;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.service.contract.AdminService;
import com.foxminded.korniichyk.university.service.contract.RoleService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.AdminNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {


    private final AdminDao adminDao;
    private final AdminMapper adminMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final AdminUpdateMapper adminUpdateMapper;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao,
                            AdminMapper adminMapper,
                            UserService userService,
                            RoleService roleService,
                            AdminUpdateMapper adminUpdateMapper) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.adminUpdateMapper = adminUpdateMapper;
    }

    @Transactional
    @Override
    public AdminDto findById(Long id) {
        return adminDao.findById(id)
                .map(adminMapper::toDto)
                .orElseThrow(
                        () -> new AdminNotFoundException("Admin with id " + id + " not found")
                );
    }

    @Transactional
    @Override
    public List<AdminDto> findAll() {
        return adminDao.findAll()
                .stream()
                .map(adminMapper::toDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public void save(Admin admin) {
        adminDao.save(admin);
        log.info("{} saved", admin);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        adminDao.findById(id)
                .ifPresentOrElse(
                        admin -> {
                            adminDao.delete(admin);
                            log.info("{} deleted", admin);
                        },
                        () -> {
                            throw new AdminNotFoundException("Admin with id " + id + " not found");
                        }
                );
    }

    @Override
    public Page<AdminDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return adminDao.findAll(pageable).map(adminMapper::toDto);
    }

    @Transactional
    @Override
    public void registerAdmin(AdminRegistrationDto adminRegistrationDto) {

        Role adminRole = roleService.findByName("ROLE_ADMIN");
        adminRegistrationDto.getUser().setRoles(singleton(adminRole));

        User user = userService.registerUser(adminRegistrationDto.getUser());

        Admin admin = new Admin();
        admin.setUser(user);

        save(admin);

    }

    @Override
    public AdminUpdateDto getAdminUpdateDto(Long adminId) {

        Admin admin = adminDao.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException("Admin with id" + adminId + "not found")
        );

        return adminUpdateMapper.toDto(admin);
    }

    @Override
    @Transactional
    public void update(AdminUpdateDto adminUpdateDto) {

        Long adminId = adminUpdateDto.getId();

        Admin admin = adminDao.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException("Admin with id" + adminId + "not found")
        );

        admin.getUser().setEmail(adminUpdateDto.getUser().getEmail());
        admin.getUser().setFirstName(adminUpdateDto.getUser().getFirstName());
        admin.getUser().setLastName(adminUpdateDto.getUser().getLastName());
        admin.getUser().setDateOfBirth(adminUpdateDto.getUser().getDateOfBirth());
        admin.getUser().setPhoneNumber(adminUpdateDto.getUser().getPhoneNumber());

    }
}