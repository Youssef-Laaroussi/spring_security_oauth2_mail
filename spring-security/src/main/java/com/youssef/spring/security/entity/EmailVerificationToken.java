package com.youssef.spring.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification_tokens")
@Data
@NoArgsConstructor
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    public EmailVerificationToken(String token, User user) {
        this.token=token;
        this.user=user;
        this.expiryDate=LocalDateTime.now().plusHours(24);

    }








}
