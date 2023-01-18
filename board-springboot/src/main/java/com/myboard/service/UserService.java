package com.myboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myboard.domain.BoardUser;
import com.myboard.domain.BoardUserDTO;
import com.myboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public BoardUser create(BoardUserDTO params) {
        params.setPassword(passwordEncoder.encode(params.getPassword()));
        BoardUser user = BoardUser.builder().params(params).build();
        
        this.userRepository.save(user);
        return user;
    }
}
