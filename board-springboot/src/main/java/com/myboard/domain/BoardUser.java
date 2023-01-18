package com.myboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BoardUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 번호(PK)
    
    @Column(unique = true)
    private String username; // 유저이름
    
    private String password; // 비밀번호
    
    @Column(unique = true)
    private String email; // 이메일
    
    @Builder
    public BoardUser(BoardUserDTO params) {
        this.idx = params.getIdx();
        this.username = params.getUsername();
        this.password = params.getPassword();
        this.email = params.getEmail();
        
    }
}
