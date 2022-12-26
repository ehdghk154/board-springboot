package com.myboard.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myboard.domain.BoardDTO;
import com.myboard.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
    
    @Autowired
    private BoardMapper boardMapper;
    
    @Override
    public boolean registerBoard(BoardDTO params) {
        int queryResult = 0;
        
        // idx가 없다는 건, 아직 생성되지 않는 게시물이기에,
        // 게시물 생성 쿼리 실행
        if(params.getIdx() == null) {
            // 쿼리가 정상 작동하면 1을 반환
            queryResult = boardMapper.insertBoard(params);
        }
        // idx가 있으면 게시물이 존재하므로, 게시물 수정 쿼리 실행
        else {
            queryResult = boardMapper.updateBoard(params);
        }
        
        // 정상 작동되면 true, 아니면 false
        return (queryResult == 1) ? true : false;
    }
    
    // 게시글 목록
    @Override
    public List<BoardDTO> getBoardList() {
        List<BoardDTO> boardList = Collections.emptyList();
        
        // 게시글 총 개수 저장
        int boardTotalCount = boardMapper.selectBoardTotalCount();
        
        // 게시글이 있을 경우, 게시글 목록 가져오는 Mapper 실행
        if(boardTotalCount > 0) {
            boardList = boardMapper.selectBoardList();
        }
        
        return boardList;
    }
    
    // 게시글 조회
    @Override
    public BoardDTO getBoardDetail(Long idx) {
        
        return boardMapper.selectBoardDetail(idx);
    }
}
