package com.myboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myboard.domain.BoardUser;

public interface UserRepository extends JpaRepository<BoardUser, Long>{
    
}
