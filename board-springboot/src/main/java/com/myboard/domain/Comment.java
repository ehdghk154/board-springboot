package com.myboard.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;           // 번호(PK)
    
    @Column(columnDefinition = "TEXT")
    private String content;     // 내용
    
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean secretYN;    // 비밀 여부
    
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean deleteYN;    // 삭제 여부
    
    private LocalDateTime insertTime; // 등록일
    private LocalDateTime updateTime; // 수정일
    private LocalDateTime deleteTime; // 삭제일
    
    @ManyToOne
    private Board board;
    
    @Builder
    public Comment(CommentDTO params) {
        if(params.getIdx() != null) {
            this.idx = params.getIdx();
            this.insertTime = params.getInsertTime();
            this.updateTime = LocalDateTime.now();
        }else {
            this.insertTime = LocalDateTime.now();
        }
        this.board = params.getBoard();
        this.content = params.getContent();
        this.secretYN = params.getSecretYN();
        this.deleteYN = params.getDeleteYN();
        
    }
}
