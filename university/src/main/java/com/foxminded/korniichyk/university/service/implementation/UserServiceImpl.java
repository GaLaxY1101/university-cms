package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.UserDao;
import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import com.foxminded.korniichyk.university.mapper.display.UserMapper;
import com.foxminded.korniichyk.university.mapper.registration.UserRegistrationMapper;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.EmailAlreadyExistsException;
import com.foxminded.korniichyk.university.service.exception.PhoneNumberAlreadyExistsException;
import com.foxminded.korniichyk.university.service.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final UserRegistrationMapper userRegistrationMapper;

    public UserServiceImpl(UserDao userDao,
                           UserMapper userMapper,
                           UserRegistrationMapper userRegistrationMapper
    ) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.userRegistrationMapper = userRegistrationMapper;
    }

    @Transactional
    @Override
    public UserDto findById(Long id) {
        return userDao.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Transactional
    @Override
    public List<UserDto> findAll() {
        return userDao.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);
        log.info("{} saved", user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.findById(id).ifPresentOrElse(user -> {
            userDao.delete(user);
            log.info("{} deleted", user);
        }, () -> {
            throw new UserNotFoundException("User with id " + id + " not found");
        });
    }

    @Override
    public Page<UserDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userDao.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional
    public User findByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    @Override
    @Transactional
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        if (isExistsByEmail(userRegistrationDto.getEmail())) {
            throw new EmailAlreadyExistsException("This email already registered");
        }

        if (isExistsByPhoneNumber(userRegistrationDto.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("This phone number already registered");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userRegistrationDto.getPassword());
        userRegistrationDto.setPassword(encodedPassword);

        User user = userRegistrationMapper.toEntity(userRegistrationDto);

        save(user);
        return user;
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public boolean isExistsByPhoneNumber(String phoneNumber) {
        return userDao.existsByPhoneNumber(phoneNumber);
    }
}