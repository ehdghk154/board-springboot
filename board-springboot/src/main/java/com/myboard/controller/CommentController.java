package com.myboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myboard.domain.BoardDTO;
import com.myboard.service.BoardService;
import com.myboard.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    
    private final BoardService boardService;
    private final CommentService commentService;
    
    @PostMapping("/write.do")
    public String registerComment(Model model, @RequestParam(value="idx") Long idx, @RequestParam String content) {
        BoardDTO boardDTO = this.boardService.getBoardDetail(idx);
        this.commentService.registerComment(boardDTO, content);
        return String.format("redirect:/board/view.do?idx=%s", idx);
    }
}
