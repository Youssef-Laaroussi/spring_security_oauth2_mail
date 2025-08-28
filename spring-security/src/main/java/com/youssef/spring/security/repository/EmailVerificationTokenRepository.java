package com.youssef.spring.security.repository;

import com.youssef.spring.security.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {


    Optional<EmailVerificationToken> findByToken(String token);
    void deleteByUserId(Long userId);

}
