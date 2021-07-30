package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails)this.userRepository.findUserByUsername(username).map((user) -> {
            return new User(user.getUsername(), user.getPassword(), (Collection)user.getRoles().stream().map((role) -> {
                return new SimpleGrantedAuthority(role.getName());
            }).collect(Collectors.toList()));
        }).orElseThrow(() -> {
            return new UsernameNotFoundException("User not found");
        });
    }
}
