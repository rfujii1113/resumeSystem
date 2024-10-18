package com.stylesystem.service;

import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException(userId + " not found."));

        return new AppUserDetails(user);
    }

    // Inner class to manage user details
    @RequiredArgsConstructor
    private static class AppUserDetails implements UserDetails {

        private final Users users;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(users.getRole()));
        }

        @Override
        public String getPassword() {
            return users.getPassword();
        }

        @Override
        public String getUsername() {
            return users.getUserId();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return !users.getDeleteFlag();
        }
    }
}
