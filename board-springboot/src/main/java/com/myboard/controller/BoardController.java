package com.myboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myboard.domain.BoardDTO;
import com.myboard.service.BoardService;

@Controller
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    // 게시글 작성 GET
    @GetMapping(value = "board/write.do")
    public String openBoardWrite(@RequestParam(value="idx", required=false) Long idx, Model model) {
        
        model.addAttribute("board", new BoardDTO());
        
        return "board/write";
    }
    
    // 게시글 작성 POST
    @PostMapping(value = "/board/register.do")
    public String registerBoard(final BoardDTO params) {
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if (isRegistered == false) {
                // TODO : 게시글 등록 실패 메세지 전달
            }
        } catch(DataAccessException e) {
            // TODO : 데이터베이스 처리 과정에서 문제가 발생했다는 메세지 전달
        } catch(Exception e) {
            // TODO : 시스템에 문제가 발생했다는 메세지 전달
        }
        
        return "redirect:/board/list.do";
    }
}
