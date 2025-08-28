package com.youssef.spring.security.repository;

import com.youssef.spring.security.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {



    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUserId(Long userId);



}
