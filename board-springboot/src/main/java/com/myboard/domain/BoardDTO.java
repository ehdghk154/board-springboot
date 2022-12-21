package com.myboard.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    
    // Board_Table
    private Long idx;           // 번호(PK)
    private String title;       // 제목
    private String content;     // 내용
    private String writer;      // 작성자
    private int viewCnt;        // 조회수
    private String noticeYN;    // 공지 여부
    private String secretYN;    // 비밀 여부
    private String deleteYN;    // 삭제 여부
    private LocalDateTime insertTime; // 등록일
    private LocalDateTime updateTime; // 수정일
    private LocalDateTime deleteTime; // 삭제일
}
