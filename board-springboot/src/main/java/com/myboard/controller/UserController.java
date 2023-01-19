package com.myboard.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myboard.domain.BoardUserDTO;
import com.myboard.domain.SignUpForm;
import com.myboard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/signup.do")
    public String signup(SignUpForm signUpForm) {
        return "board/signup";
    }
    
    @PostMapping("/signup.do")
    public String signup(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "board/signup";
        }
        
        if(!signUpForm.getPassword1().equals(signUpForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 비밀번호가 일치하지 않습니다.");
            return "board/signup";
        }
        BoardUserDTO buDTO = new BoardUserDTO();
        buDTO.setUsername(signUpForm.getUsername());
        buDTO.setPassword(signUpForm.getPassword1());
        buDTO.setEmail(signUpForm.getEmail());
        
        try {
        userService.create(buDTO);
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "board/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "board/signup";
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/login.do")
    public String login() {
        // PostMapping 방식의 메서드는 스프링 시큐리티가 대신 처리하므로 구현할 필요 없음
        return "board/login";
    }
}
