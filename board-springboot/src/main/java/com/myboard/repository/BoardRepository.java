package com.myboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myboard.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByDeleteYN(Boolean deleteYN, Pageable pageable);
}
