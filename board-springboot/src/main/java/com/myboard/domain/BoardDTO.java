package com.myboard.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    
    // Board
    private Long idx;           // 번호(PK)
    private String title;       // 제목
    private String content;     // 내용
    private int viewCnt;        // 조회수
    private Boolean noticeYN;    // 공지 여부
    private Boolean secretYN;    // 비밀 여부
    private Boolean deleteYN;    // 삭제 여부
    private LocalDateTime insertTime; // 등록일
    private LocalDateTime updateTime; // 수정일
    private LocalDateTime deleteTime; // 삭제일
    private List<Comment> commentList; // 댓글목록
    private BoardUser author;    // 작성자
}
