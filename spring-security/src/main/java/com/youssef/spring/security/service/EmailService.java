package com.youssef.spring.security.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    @Value("${app.frontend.url}")
    private String frontendUrl;



    public void sendEmailVerification(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("Please click the following link to verify your email: " +
                frontendUrl + "/verify-email?token=" + token);
        mailSender.send(message);
    }



    public void sendPasswordReset(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Please click the following link to reset your password: " +
                frontendUrl + "/reset-password?token=" + token);
        mailSender.send(message);
    }



}