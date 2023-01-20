package com.myboard.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myboard.domain.BoardDTO;
import com.myboard.domain.BoardUserDTO;
import com.myboard.domain.CommentForm;
import com.myboard.service.BoardService;
import com.myboard.service.CommentService;
import com.myboard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write.do")
    public String registerComment(Model model, @RequestParam(value="idx") Long idx, 
            @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        BoardDTO boardDTO = this.boardService.getBoardDetail(idx);
        BoardUserDTO userDTO = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("board", boardDTO);
            return "board/view";
        }
        this.commentService.registerComment(boardDTO, commentForm.getContent(), userDTO);
        return String.format("redirect:/board/view.do?idx=%s", idx);
    }
}
