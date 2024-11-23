package com.duong.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duong.identityservice.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
