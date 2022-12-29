package com.myboard.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;           // 번호(PK)
    
    @Column(length = 200)
    private String title;       // 제목
    
    @Column(columnDefinition = "TEXT")
    private String content;     // 내용
    
    @Column(length = 9)
    private String writer;      // 작성자
    
    private int viewCnt;        // 조회수
    
    @Convert(converter = BooleanToYNConverter.class)
    private boolean noticeYN;    // 공지 여부
    
    @Convert(converter = BooleanToYNConverter.class)
    private boolean secretYN;    // 비밀 여부
    
    @Convert(converter = BooleanToYNConverter.class)
    private boolean deleteYN;    // 삭제 여부

    private LocalDateTime insertTime; // 등록일
    private LocalDateTime updateTime; // 수정일
    private LocalDateTime deleteTime; // 삭제일

}
