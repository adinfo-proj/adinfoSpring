package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class MAKETER_MASTER {
    private String  caUpdateDt;
    private Long    mbId;
    private Long    mkId;
    private Long    caId;
    private String  mkCd;
    private String  actvCd;
    private String  regDt;
    private String  regTm;
    private String  expDt;
    private String  expTm;
    private Long    price;
    private Long    mkPrice;
    private Long    specPrice;
    private Long    bonusPrice;
    private String  SmsYn;
    private String  Sms;
}
