package com.myboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myboard.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAllByDeleteYN(Boolean deleteYN);
}
