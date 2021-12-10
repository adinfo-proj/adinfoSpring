package com.ad.adinfo.Domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMember {
    private String  emailId;            // 로그인 아이디 (in)
    private String  emailPw;            // 비밀번호 (in)
    private String  userName;           // 사용자명 (in)
    private String  mobileNo;           // 휴대전화번호 (in)

    private String  jwtAuthToken;       // 로그인 후 토큰값 (return)
    private String  mbId;               // 회원사 번호 (return)
    private String  adGradeCd;          // 사용자 등급 (return)
}
