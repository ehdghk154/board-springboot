package com.myboard.service;

import org.springframework.stereotype.Service;

import com.myboard.domain.Board;
import com.myboard.domain.BoardDTO;
import com.myboard.domain.Comment;
import com.myboard.domain.CommentDTO;
import com.myboard.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
    
    private final CommentRepository commentRepository;
    
    public void registerComment(BoardDTO params, String content) {
        CommentDTO cmtDTO = new CommentDTO();
        Board board = Board.builder().params(params).build();
        cmtDTO.setContent(content);
        cmtDTO.setBoard(board);
        this.commentRepository.save(Comment.builder().params(cmtDTO).build());
    }
}
