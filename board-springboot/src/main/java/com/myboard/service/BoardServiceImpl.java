package com.myboard.service;

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
        
        // 쿼리가 정상 작동하면 1을 반환
        queryResult = boardMapper.insertBoard(params);
        
        // 정상 작동되면 true, 아니면 false
        return (queryResult == 1) ? true : false;
    }
}
