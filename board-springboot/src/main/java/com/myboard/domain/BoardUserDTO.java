package com.myboard.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUserDTO {
    private Long idx; // PK
    private String username; // 유저이름
    private String password; // 비밀번호
    private String email; // 이메일
}
