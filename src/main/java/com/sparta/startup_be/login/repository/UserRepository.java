package com.sparta.startup_be.login.repository;

import com.sparta.startup_be.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByUserEmailAndNickname(String userEmail, String nickname);
}
