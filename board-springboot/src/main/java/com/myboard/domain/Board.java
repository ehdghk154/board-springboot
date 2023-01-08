package com.myboard.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;           // 번호(PK)
    
    @Column(length = 200)
    private String title;       // 제목
    
    @Column(columnDefinition = "TEXT")
    private String content;     // 내용
    
    @Column(length = 10)
    private String writer;      // 작성자
    
    private int viewCnt;        // 조회수
    
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean noticeYN;    // 공지 여부
    
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean secretYN;    // 비밀 여부
    
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean deleteYN;    // 삭제 여부
    
    private LocalDateTime insertTime; // 등록일
    private LocalDateTime updateTime; // 수정일
    private LocalDateTime deleteTime; // 삭제일
    
    @OneToMany(mappedBy = "board")
    private List<Comment> commentList;
    
    @Builder
    public Board(String title, String content, String writer, Boolean noticeYN, Boolean secretYN, Boolean deleteYN) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.noticeYN = noticeYN;
        this.secretYN = secretYN;
        this.deleteYN = deleteYN;
        this.insertTime = LocalDateTime.now();
    }
}
