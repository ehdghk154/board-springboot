package com.myboard.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotEmpty(message="제목은 필수 항목입니다.")
    @Size(max=200)
    private String title;       // 제목
    
    @NotEmpty(message="내용은 필수 항목입니다.")
    private String content;     // 내용
    
    @NotEmpty(message="작성자는 필수 항목입니다.")
    private String writer;      // 작성자
    private Boolean noticeYN;    // 공지 여부
    private Boolean secretYN;    // 비밀 여부
}
