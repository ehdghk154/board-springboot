package com.myboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myboard.UserRole;
import com.myboard.domain.BoardUser;
import com.myboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;


// UserSecurityService : 스프링 시큐리티 로그인 처리의 핵심 부분
// 스프링 시큐리티의 UserDetailsService : loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    // loadUserByUsername 메서드 : 사용자명으로 비밀번호를 조회하여 리턴하는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BoardUser> _boardUser = this.userRepository.findByusername(username);
        if (_boardUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        BoardUser boardUser = _boardUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(boardUser.getUsername(), boardUser.getPassword(), authorities);
    }
}
