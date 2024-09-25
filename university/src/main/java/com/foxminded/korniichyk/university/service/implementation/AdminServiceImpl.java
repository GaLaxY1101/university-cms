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
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {


    private final AdminDao adminDao;
    private final AdminMapper adminMapper;
    private final UserService userService;
    private final AdminUpdateMapper adminUpdateMapper;

    @Override
    public AdminDto findById(Long id) {
        return adminDao.findById(id)
                .map(adminMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Admin with id {} not found", id);
                    return new AdminNotFoundException("Admin not found");
                });
    }

    @Transactional
    @Override
    public void save(Admin admin) {
        adminDao.save(admin);
        log.info("{} saved", admin);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        adminDao.findById(id)
                .ifPresentOrElse(
                        admin -> {
                            adminDao.delete(admin);
                            log.info("Admin with id {} deleted", id);
                        },
                        () -> {
                            log.error("Admin with id {} not found", id);
                            throw new AdminNotFoundException("Admin not found");
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


        adminRegistrationDto.getUser().setRole(Role.ROLE_ADMIN);

        User user = userService.registerUser(adminRegistrationDto.getUser());

        Admin admin = new Admin();
        admin.setUser(user);

        save(admin);

    }

    @Override
    public AdminUpdateDto getAdminUpdateDto(Long adminId) {

        Admin admin = adminDao.findById(adminId).orElseThrow(() -> {
            log.error("Admin with id {} not found", adminId);
            return new AdminNotFoundException("Admin not found");
        });


        return adminUpdateMapper.toDto(admin);
    }

    @Override
    @Transactional
    public void update(AdminUpdateDto adminUpdateDto) {

        Long adminId = adminUpdateDto.getId();

        Admin admin = adminDao.findById(adminId).orElseThrow(() -> {
            log.error("Admin with id {} not found", adminId);
            return new AdminNotFoundException("Admin not found");
        });


        admin.getUser().setEmail(adminUpdateDto.getUser().getEmail());
        admin.getUser().setFirstName(adminUpdateDto.getUser().getFirstName());
        admin.getUser().setLastName(adminUpdateDto.getUser().getLastName());
        admin.getUser().setDateOfBirth(adminUpdateDto.getUser().getDateOfBirth());
        admin.getUser().setPhoneNumber(adminUpdateDto.getUser().getPhoneNumber());

    }
}