package com.myboard.controller;

import java.security.Principal;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.myboard.domain.BoardDTO;
import com.myboard.domain.BoardForm;
import com.myboard.domain.BoardUserDTO;
import com.myboard.domain.CommentForm;
import com.myboard.service.BoardService;
import com.myboard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {
    
    private final BoardService boardService;
    private final UserService userService;
    
    // 게시글 등록 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/register.do")
    public String registerBoard(BoardForm boardForm) {
        
        return "board/write";
    }
    
    // 게시글 등록 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/register.do")
    public String registerBoard(@Valid BoardForm boardForm, 
            BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "board/write";
        }
        BoardDTO boardDTO = new BoardDTO();
        BoardUserDTO userDTO = this.userService.getUser(principal.getName());
        
        boardDTO.setTitle(boardForm.getTitle());
        boardDTO.setContent(boardForm.getContent());
        boardDTO.setNoticeYN(boardForm.getNoticeYN());
        boardDTO.setSecretYN(boardForm.getSecretYN());
        
        
        this.boardService.registerBoard(boardDTO, userDTO);
        
        return "redirect:/";
    }
    
    // 게시글 목록
    @GetMapping(value = "/list.do")
    public String openBoardList(Model model, @RequestParam(value="page", defaultValue="0") int page, 
            @RequestParam(value="kw", defaultValue="") String kw) {
        if(page < 0) { // 0 미만 페이지 오류 처리
         // TODO : 존재하지 않는 페이지 번호라는 메세지 출력
            return "redirect:/";
        }
        Page<BoardDTO> paging = this.boardService.getBoardList(kw, page);
        
        if(page > paging.getTotalPages()) { // 총 페이지 수를 넘어서는 번호 오류 처리
            // TODO : 존재하지 않는 페이지 번호라는 메세지 출력
            return "redirect:/";
        }
        /**
         * 현재페이지 < ceil(한줄페이지수/2) or 현재페이지 > 마지막페이지 - ceil(한줄페이지수/2)
         */
        // 페이지 칸 처리 
        int pageSize1 = 4;
        int pageSize2 = 4;
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
        model.addAttribute("kw", kw);
        
        return "board/list";
    }
    
    // 게시글 조회
    @GetMapping(value = "/view.do")
    public String openBoardDetail(@RequestParam("idx") Long idx, Model model, CommentForm commentForm) {
        // 올바르지 않은 접근
        if(idx == null) {
            System.out.println("실패");
            return "redirect:/";
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
    
    // 게시글 수정 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/modify.do")
    public String modify(BoardForm boardForm, @RequestParam("idx") Long idx, Principal principal) {
        BoardDTO board = this.boardService.getBoardDetail(idx);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        boardForm.setNoticeYN(board.getNoticeYN());
        boardForm.setSecretYN(board.getSecretYN());
        return "board/write";
    }
    
    // 게시글 수정 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/modify.do")
    public String modify(@Valid BoardForm boardForm, BindingResult bindingResult, 
            @RequestParam("idx") Long idx, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "board/write";
        }
        BoardDTO boardDTO = this.boardService.getBoardDetail(idx);
        BoardUserDTO userDTO = this.userService.getUser(principal.getName());
        if(!boardDTO.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        boardDTO.setTitle(boardForm.getTitle());
        boardDTO.setContent(boardForm.getContent());
        boardDTO.setNoticeYN(boardForm.getNoticeYN());
        boardDTO.setSecretYN(boardForm.getSecretYN());
        
        this.boardService.registerBoard(boardDTO, userDTO);
        return String.format("redirect:/board/view.do?idx=%s", idx);
    }
    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/delete.do")
    public String deleteBoard(@RequestParam("idx") Long idx) {
        //System.out.println("/board/delete.do 접근. idx = " + idx);
        
        //올바르지 않은 접근일 경우
        if(idx == null) {
            // TODO : 올바르지 않은 접근이라는 메세지 전달
            return "redirect:/";
        }
        
        try {
            //System.out.println("try 접근. idx = " + idx);
            this.boardService.deleteBoard(idx);
        } catch(DataAccessException e) {
            // TODO : 데이터베이스 처리 과정에 문제 발생 메세지 전달
        } catch(Exception e) {
            // TODO : 시스템에 문제가 발생했다는 메세지 전달
        }
        
        return "redirect:/";
    }
}
