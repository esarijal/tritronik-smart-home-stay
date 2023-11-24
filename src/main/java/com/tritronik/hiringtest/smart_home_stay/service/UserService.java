package com.tritronik.hiringtest.smart_home_stay.service;

import com.tritronik.hiringtest.smart_home_stay.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    User findByUsername(String username);

    User findById(Long userId);
}
