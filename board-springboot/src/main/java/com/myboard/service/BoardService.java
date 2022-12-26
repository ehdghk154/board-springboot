package com.myboard.service;

import java.util.List;

import com.myboard.domain.BoardDTO;

public interface BoardService {
    // 게시글 등록 + 게시글 수정
    public boolean registerBoard(BoardDTO params);
    
    // 게시글 목록 (+ 게시글 총 개수)
    public List<BoardDTO> getBoardList();
    
    // 게시글 조회
    public BoardDTO getBoardDetail(Long idx);
}
