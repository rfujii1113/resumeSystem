package com.stylesystem.service;

import com.stylesystem.dto.UserPasswordResetDto;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void resetPassword(UserPasswordResetDto userPasswordResetDto) {
        // find user by userId
        Users user = userRepository.findByUserId(userPasswordResetDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // save new password
        String encodedPassword = passwordEncoder.encode(userPasswordResetDto.getNewPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
