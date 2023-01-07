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
        for(int i = 5; i <= 6; i++) {
            dto.setTitle(String.format("[%d]번째 게시글입니다.", i));
            dto.setWriter(String.format("테스트이름[%d]", i));
            dto.setContent(String.format("게시글등록테스트[:%d]", i));
            dto.setNoticeYN(true);
            dto.setSecretYN(false);
            dto.setDeleteYN(true);
            
            this.boardRepository.save(Board.builder()
                                    .title(dto.getTitle())
                                    .content(dto.getContent())
                                    .writer(dto.getWriter())
                                    .noticeYN(dto.getNoticeYN())
                                    .secretYN(dto.getSecretYN())
                                    .deleteYN(dto.getDeleteYN())
                                    .build());
        }
    }

}
