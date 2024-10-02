package com.stylesystem.service;

import com.stylesystem.dto.CustomUserDetails;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(users);
    }
}
