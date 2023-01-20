package com.myboard.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    
    private Long idx;           // 번호(PK)
    private String content;     // 내용
    private Boolean secretYN;    // 비밀 여부
    private Boolean deleteYN;    // 삭제 여부
    private LocalDateTime insertTime; // 등록일
    private LocalDateTime updateTime; // 수정일
    private LocalDateTime deleteTime; // 삭제일
    private Board board;
    private BoardUser author;
}
