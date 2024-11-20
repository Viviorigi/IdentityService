package com.duong.identityservice.repository;

import com.duong.identityservice.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository  extends JpaRepository<InvalidatedToken, String> {
}
