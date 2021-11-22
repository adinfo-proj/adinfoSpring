package com.ad.adinfo.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String  emailId;
    private String  emailPw;
    private String  jwtAuthToken;
}
