package com.duong.identityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duong.identityservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
