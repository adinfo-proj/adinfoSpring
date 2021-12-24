package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class AD_USER_MASTER {
    private String  updateDt;
    private Long    mbId;
    private Long    adId;
    private Long    mkId;
    private String  mkCd;
    private String  clntId;
    private String  clntNm;
    private String  nickNm;
    private String  clntPw;
    private String  clntSubsNo;
    private String  companyNm;
    private String  companyNo;
    private String  companyUpjong;
    private String  companyKind;
    private String  address;
    private String  bankCd;
    private String  acntNm;
    private String  acntNo;
    private String  actvCd;
    private String  gradeCd;
    private String  regDt;
    private String  abnDt;
    private String  srtDt;
    private String  expDt;
    private Long    sponserId;
    private Double  sponserRate;
    private String  comment;
}
