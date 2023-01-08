package com.myboard;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.myboard.domain.Board;
import com.myboard.domain.BoardDTO;
import com.myboard.repository.BoardRepository;


@SpringBootTest
class ModelMapperTests {
    @Autowired
    private BoardRepository boardRepository;
    @Test
    void test() {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Board> board = this.boardRepository.findById(1L);
        if(board.isPresent()) {
            Board b = board.get();
            BoardDTO dto = modelMapper.map(b, BoardDTO.class);
            assertEquals(false, dto.getDeleteYN());
        }else {
            throw new DataNotFoundException("board not found");
        }
    }
}
