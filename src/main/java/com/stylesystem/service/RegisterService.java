package com.stylesystem.service;

import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    public void saveUser(UserAuthDto userDto) {
        Users users = Users.builder()
            .userId(userDto.getUserId())
            .password(userDto.getPassword())
            .role(userDto.getRole()) 
            .deleteFlag(false)
            .build();
        userRepository.save(users);
    }
}
