package com.tritronik.hiringtest.smart_home_stay.repository;

import com.tritronik.hiringtest.smart_home_stay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
