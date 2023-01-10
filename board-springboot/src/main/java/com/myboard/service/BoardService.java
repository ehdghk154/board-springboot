package com.myboard.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myboard.DataNotFoundException;
import com.myboard.domain.Board;
import com.myboard.domain.BoardDTO;
import com.myboard.mapper.BoardMapper;
import com.myboard.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
    
    private final BoardMapper boardMapper;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    
    // 게시글 등록 + 게시글 수정
    public void registerBoard(BoardDTO params) {
        
        /**
         * idx가 없으면, 게시글 등록
         * idx가 있으면, 게시글 수정
         */
        this.boardRepository.save(Board.builder().params(params).build());
    }

    
    // 게시글 목록 (+ 게시글 총 개수) 페이징
    public Page<BoardDTO> getBoardList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Board> boardList = this.boardRepository.findAllByDeleteYN(false, pageable);
        
        Page<BoardDTO> result = boardList.map(b -> modelMapper.map(b,  BoardDTO.class));
        
        return result;
    }
    
    //게시글 총 개수
    public int getTotal() {
        return boardMapper.selectBoardTotalCount();
    }
    
    // 게시글 조회
    public BoardDTO getBoardDetail(Long idx) {
        Optional<Board> ob = this.boardRepository.findById(idx);
        if(ob.isPresent()) {
            BoardDTO board = modelMapper.map(ob.get(), BoardDTO.class);
            return board;
        }else {
            throw new DataNotFoundException("board not found");
        }
    }
    
    // 게시글 삭제
    public boolean deleteBoard(Long idx) {
        int queryResult = 0;
        
        BoardDTO board = boardMapper.selectBoardDetail(idx);
        
        // 조회한 게시글이 null이 아니고, 삭제된 상태가 아닐 때 실행
        if(board != null && !board.getDeleteYN()) {
            queryResult = boardMapper.deleteBoard(idx);
        }
        
        // 1이면 정상적으로 쿼리 실행되었다는 뜻으로 true 반환
        return(queryResult == 1) ? true : false;
    }
}

/**
 * 엔티티 추가, h2데이터베이스 추가, 레포지터리(+JPA)추가, 도메인 분류
 */