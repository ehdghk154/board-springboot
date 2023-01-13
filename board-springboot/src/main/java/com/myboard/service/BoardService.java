package com.myboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myboard.DataNotFoundException;
import com.myboard.domain.Board;
import com.myboard.domain.BoardDTO;
//import com.myboard.domain.Comment;
import com.myboard.mapper.BoardMapper;
import com.myboard.repository.BoardRepository;

//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Join;
//import jakarta.persistence.criteria.JoinType;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
    
    private final BoardMapper boardMapper;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    
//    private Specification<Board> search(String kw) {
//       return new Specification<>() {
//           private static final long serialVersionUID = 1L;
//           @Override
//           public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
//               query.distinct(true);
//               Join<Board, Comment> c1 = b.join("board", JoinType.LEFT);
//               return cb.or(cb.like(b.get("title"), "%" + kw + "%"),    // 제목
//                       cb.like(b.get("content"), "%" + kw + "%"),       // 질문 내용
//                       cb.like(b.get("writer"), "%" + kw + "%"),        // 작성자
//                       cb.like(c1.get("content"), "%" + kw + "%")       // 질문 내용
//                       );
//           }
//       };
//    }
    
    // 게시글 등록 + 게시글 수정
    public void registerBoard(BoardDTO params) {
        
        /**
         * idx가 없으면, 게시글 등록
         * idx가 있으면, 게시글 수정
         */
        this.boardRepository.save(Board.builder().params(params).build());
    }

    // 게시글 목록 (+ 게시글 총 개수) 페이징
    public Page<BoardDTO> getBoardList(String kw, int page) {
        //게시글 공지 여부 및 등록일 기준으로 정렬
        //html에서 공지글 게시글 따로 추가되도록해서 항상 상단 고정하게 바꿀 예정
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("noticeYN"));
        sorts.add(Sort.Order.desc("insertTime"));
        //페이징 ( 한 페이지 당 10개 게시글)
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        //Specification<Board> spec = search(kw);
        Page<Board> boardList = this.boardRepository.findAllByKeyword(kw, pageable);
        
        Page<BoardDTO> result = boardList.map(b -> modelMapper.map(b, BoardDTO.class));
        
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
    public void deleteBoard(Long idx) {
        
        Optional<Board> ob = this.boardRepository.findById(idx);
        
        //게시글이 존재할 경우
        if(ob.isPresent()) {
            BoardDTO board = modelMapper.map(ob.get(), BoardDTO.class);
            board.setDeleteYN(true);
            this.boardRepository.save(Board.builder().params(board).build());
        }
        
    }
}