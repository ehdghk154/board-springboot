package com.myboard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                new AntPathRequestMatcher("/**")).permitAll() // 모든 인증되지 않은 요청을 허락
            .and()
                .csrf().ignoringRequestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")) // CSRF 처리시 H2 콘솔은 예외로 처리
            .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        // X-Frame-Options 헤더의 값으로 sameorigin을 설정하면
                        // frame에 포함된 페이지가 페이지를 제공하는 사이트와 동일한 경우에는 계속 사용할 수 있음
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
            .and()
                .formLogin()
                .loginPage("/user/login.do") // 로그인 페이지 URL 지정
                .defaultSuccessUrl("/") // 로그인 성공 시 이동 페이지
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout.do")) // 로그아웃 페이지 URL 지정
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동 페이지
                .invalidateHttpSession(true); // 로그아웃 시 생성된 사용자 세션 삭제
            ;
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    // AuthenticationManager : 스프링 시큐리티의 인증을 담당
    /**
     * AuthenticationManager 빈 생성시 스프링의 내부 동작으로 인해 위에서 작성한
     * UserSecurityService와 PasswordEncoder가 자동으로 설정
     * 
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
