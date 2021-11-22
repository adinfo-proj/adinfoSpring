package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class PT_USE_CAMPAIGN {
    private String  caUpdateDt;
    private Long    ptId;
    private Long    caId;
    private String  ptCd;
    private String  actvCd;
    private String  regDt;
    private String  regTm;
    private String  expDt;
    private String  expTm;
    private Long    price;
    private Long    ptPrice;
    private Long    specPrice;
    private Long    bonusPrice;
    private String  SmsYn;
    private String  Sms;
}
