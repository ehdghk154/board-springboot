package com.myboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myboard.domain.Board;
import com.myboard.repository.BoardRepository;

@SpringBootTest
class BoardListTest {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Test
    void test() {
        /*List<Board> boardList = this.boardRepository.findAllByDeleteYN(true);
        assertEquals(2, boardList.size());
        
        Board board = boardList.get(0);
        assertEquals("[5]번째 게시글입니다.", board.getTitle());*/
    }
}
