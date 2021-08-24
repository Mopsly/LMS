package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.domain.User;

@Service
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDb = userRepository.findAuthorisedUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                userFromDb.getUsername(),
                userFromDb.getPassword(),
                userFromDb.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));

    }
}
