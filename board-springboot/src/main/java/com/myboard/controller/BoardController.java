package com.myboard.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myboard.domain.BoardDTO;
import com.myboard.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {
    
    private final BoardService boardService;
    
    // 게시글 등록 GET
    @GetMapping(value = "/write.do")
    public String openBoardWrite(@RequestParam(value="idx", required=false) Long idx, Model model) {
        
        // idx가 없으면 게시물이 존재하지 않으므로,
        // 새로운 BoardDTO를 모델에 담아 리턴
        if(idx == null) {
            model.addAttribute("board", new BoardDTO());
        }
        // idx가 있으면 게시물이 존재하므로,
        // 게시물을 조회하고 그 정보를 모델에 담아 리턴
        else {
            BoardDTO board = boardService.getBoardDetail(idx);
            
            // 아무 정보도 조회되지 않았다면, list로 리다이렉트
            if(board == null) {
                return "redirect:/board/list.do";
            }
            
            model.addAttribute("board", board);
        }
        
        return "board/write";
    }
    
    // 게시글 등록 POST
    @PostMapping(value = "/register.do")
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
    @GetMapping(value = "/list.do")
    public String openBoardList(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        if(page < 0) { // 0 미만 페이지 오류 처리
         // TODO : 존재하지 않는 페이지 번호라는 메세지 출력
            return "redirect:/board/list.do";
        }
        Page<BoardDTO> paging = this.boardService.getBoardList(page);
        int pageSize1 = 4;
        int pageSize2 = 4;
        if(page >= paging.getTotalPages()) { // 총 페이지 수를 넘어서는 번호 오류 처리
            // TODO : 존재하지 않는 페이지 번호라는 메세지 출력
            return "redirect:/board/list.do";
        }
        /**
         * 현재페이지 < ceil(한줄페이지수/2) or 현재페이지 > 마지막페이지 - ceil(한줄페이지수/2)
         */
        // 페이지 칸 처리 
        if(paging.getNumber() < 5) {
            pageSize1 = paging.getNumber();
            pageSize2 = 8-pageSize1;
        }
        if(paging.getNumber() > paging.getTotalPages()-5) {
            pageSize2 = (paging.getTotalPages()-1)-paging.getNumber();
            pageSize1 = 8-pageSize2;
        }
        model.addAttribute("pageSize1", pageSize1);
        model.addAttribute("pageSize2", pageSize2);
        model.addAttribute("paging", paging);
        
        return "board/list";
    }
    
    // 게시글 조회
    @GetMapping(value = "/view.do")
    public String openBoardDetail(@RequestParam("idx") Long idx, Model model) {
        // 올바르지 않은 접근
        if(idx == null) {
            System.out.println("실패");
            return "redirect:/board/list.do";
        }
        
        BoardDTO board = boardService.getBoardDetail(idx);
        
        // 없는 게시글이거나 삭제된 게시글일 경우
        if(board == null || board.getDeleteYN()) {
            // TODO : 존재하지 않거나 삭제된 게시글이라는 메시지 전달
            return "redirect:/board/list.do";
        }
        
        model.addAttribute("board", board);
        
        return "board/view";
    }
    
    // 게시글 삭제
    @GetMapping(value = "/delete.do")
    public String deleteBoard(@RequestParam(value="idx", required=false) Long idx) {
        System.out.println("/board/delete.do 접근. idx = " + idx);
        
        //올바르지 않은 접글일 경우
        if(idx == null) {
            // TODO : 올바르지 않은 접근이라는 메세지 전달
            return "redirect:/board/list.do";
        }
        
        try {
            System.out.println("try 접근. idx = " + idx);
            boolean isDeleted = boardService.deleteBoard(idx);
            System.out.println("deleteBoard 실행 후. isDeleted = " + isDeleted);
            
            // false일 경우 이미 게시글이 삭제된 상태
            if(isDeleted == false) {
                // TODO : 게시글 삭제에 실패했다는 메세지 전달
            }
        } catch(DataAccessException e) {
            // TODO : 데이터베이스 처리 과정에 문제 발생 메세지 전달
        } catch(Exception e) {
            // TODO : 시스템에 문제가 발생했다는 메세지 전달
        }
        
        return "redirect:/board/list.do";
    }
}
