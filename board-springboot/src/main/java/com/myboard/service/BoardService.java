package com.myboard.service;

import com.myboard.domain.BoardDTO;

public interface BoardService {
    // 게시글 등록
    public boolean registerBoard(BoardDTO params);
}
