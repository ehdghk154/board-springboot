package com.myboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myboard.domain.Board;
import com.myboard.domain.BoardDTO;
import com.myboard.repository.BoardRepository;

@SpringBootTest
class BoardTests {

    @Autowired
    private BoardRepository boardRepository;
    
    @Test
    void test() {
        BoardDTO dto = new BoardDTO();
        for(int i = 11; i <= 300; i++) {
            dto.setTitle(String.format("[%d]번째 게시글입니다.", i));
            dto.setContent(String.format("게시글등록테스트[:%d]", i));
            dto.setNoticeYN(false);
            dto.setSecretYN(false);
            dto.setDeleteYN(false);
            
            this.boardRepository.save(Board.builder()
                                    .params(dto)
                                    .build());
        }
    }

}
