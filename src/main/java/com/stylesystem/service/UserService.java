package com.stylesystem.service;

import com.stylesystem.dto.UserDto;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        Users users = Users.builder()
            .userName(userDto.getUserName())
            .password(userDto.getPassword())
            .role("ROLE_USER")
            .deleteFlag(false)
            .build();
        userRepository.save(users);
    }
}
