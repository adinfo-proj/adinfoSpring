package com.ad.adinfo.Domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String  emailId;            // 로그인 아이디 (in)
    private String  emailPw;            // 비밀번호 (in)
    private String  keepLogin;          // 지동로그인 유지 (in)
    private String  jwtAuthToken;       // 로그인 후 토큰값 (return)
    private String  mbId;               // 회원사 번호 (return)
    private String  adGradeCd;          // 사용자 등급 (return)
}
