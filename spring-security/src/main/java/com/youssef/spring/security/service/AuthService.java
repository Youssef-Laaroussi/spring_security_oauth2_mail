package com.youssef.spring.security.service;

import com.youssef.spring.security.dto.AuthRequest;
import com.youssef.spring.security.dto.AuthResponse;
import com.youssef.spring.security.dto.RegisterRequest;
import com.youssef.spring.security.entity.EmailVerificationToken;
import com.youssef.spring.security.entity.PasswordResetToken;
import com.youssef.spring.security.entity.User;
import com.youssef.spring.security.repository.EmailVerificationTokenRepository;
import com.youssef.spring.security.repository.PasswordResetTokenRepository;
import com.youssef.spring.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        user.setLastLogin(LocalDateTime.now());
        userService.save(user);
        String token = jwtUtil.generateToken(user.getUsername());


        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole().name());
    }


    @Transactional
    public void register(RegisterRequest request) {

        if (userService.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        User savedUser = userService.save(user);

// Generate email verification token
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(token, savedUser);
        emailVerificationTokenRepository.save(verificationToken);

// Send verification email
        emailService.sendEmailVerification(savedUser.getEmail(), token);
    }


    @Transactional
    public void verifyEmail(String token) {
        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token has expired");
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        emailVerificationTokenRepository.delete(verificationToken);
    }


    @Transactional
    public void forgotPassword(String email) {
        User user = userService.findByEmail(email);
// Delete any existing password reset tokens for this user
        passwordResetTokenRepository.deleteByUserId(user.getId());
// Generate new token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(resetToken);
// Send reset email
        emailService.sendPasswordReset(email, token);
    }



    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }
        if (resetToken.isUsed()) {
            throw new RuntimeException("Reset token has already been used");
        }
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);


    }



























}
