package com.stylesystem.service;

import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDeleteService {
    
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Users> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    @Transactional
    public void deactivateUser(String userId) {
        userRepository.updateDeleteFlag(userId, true);
    }

    @Transactional
    public void activateUser(String userId) {
        userRepository.updateDeleteFlag(userId, false);
    }
}
