package com.myboard.controller;

import java.util.List;

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
    @GetMapping(value = "/board/write.do")
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
    
    // 게시글 목록
    @GetMapping(value = "/board/list.do")
    public String openBoardList(Model model) {
        List<BoardDTO> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        
        return "board/list";
    }
    
    // 게시글 조회
    @GetMapping(value = "/board/view.do")
    public String openBoardDetail(@RequestParam(value="idx", required=false) Long idx, Model model) {
        // 올바르지 않은 접근
        if(idx == null) {
            // TODO : 올바르지 않은 접근 메시지 전달
            return "redirect:/board/list.do";
        }
        
        BoardDTO board = boardService.getBoardDetail(idx);
        
        // 없는 게시글이거나 삭제된 게시글일 경우
        if(board == null || "Y".equals(board.getDeleteYN())) {
            // TODO : 존재하지 않거나 삭제된 게시글이라는 메시지 전달
            return "redirect:/board/list.do";
        }
        
        model.addAttribute("board", board);
        
        return "board/view";
    }
}
