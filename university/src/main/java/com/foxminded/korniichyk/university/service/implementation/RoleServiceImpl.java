package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.RoleDao;
import com.foxminded.korniichyk.university.dto.display.RoleDto;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.service.contract.RoleService;
import com.foxminded.korniichyk.university.service.exception.RoleNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleDao roleDao, RoleMapper roleMapper) {
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto findById(Long id) {
        return roleDao.findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findByName(name);
        if (role == null) {
            throw new RoleNotFoundException("Role with name " + name + " not found");
        }
        return role;
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleDao.save(role);
        log.info("{} saved", role);
    }

    @Transactional
    @Override
    public void delete(Long id){
        var role = roleDao.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with id "+ id + "not found"));
        roleDao.delete(role);
        log.info("{} deleted", role);
    }

    @Override
    public Page<RoleDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return roleDao.findAll(pageable).map(roleMapper::toDto);
    }
}




