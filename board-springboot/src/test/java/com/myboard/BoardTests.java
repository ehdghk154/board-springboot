package com.myboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myboard.domain.BoardDTO;
import com.myboard.service.BoardService;

@SpringBootTest
class BoardTests {

    @Autowired
    private BoardService boardService;
    
    @Test
    void test() {
        BoardDTO board = new BoardDTO();
        for(int i = 24; i <= 50; i++) {
            board.setTitle(String.format("[%d]번째 게시글입니다.", i));
            board.setWriter(String.format("테스트이름[%d]", i));
            board.setContent(String.format("게시글등록테스트[:%d]", i));
            boardService.registerBoard(board);
        }
    }

}
