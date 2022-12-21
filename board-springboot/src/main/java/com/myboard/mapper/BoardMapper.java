package com.myboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.myboard.domain.BoardDTO;

@Mapper
public interface BoardMapper {
    // 게시글 생성
    public int insertBoard(BoardDTO params);
}
