package com.myboard.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myboard.DataNotFoundException;
import com.myboard.domain.BoardUser;
import com.myboard.domain.BoardUserDTO;
import com.myboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public BoardUserDTO create(BoardUserDTO params) {
        params.setPassword(passwordEncoder.encode(params.getPassword()));
        BoardUser user = BoardUser.builder().params(params).build();
        
        this.userRepository.save(user);
        return params;
    }
    
    public BoardUserDTO getUser(String username) {
        Optional<BoardUser> boardUser = this.userRepository.findByusername(username);
        if(boardUser.isPresent()) {
            BoardUserDTO buDTO = new BoardUserDTO();
            buDTO.setIdx(boardUser.get().getIdx());
            buDTO.setUsername(boardUser.get().getUsername());
            buDTO.setPassword(boardUser.get().getPassword());
            buDTO.setEmail(boardUser.get().getEmail());
            return buDTO;
        } else {
            throw new DataNotFoundException("boarduser not found");
        }
    }
}
