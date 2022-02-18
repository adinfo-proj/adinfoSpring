package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class TB_AD_EXTERNAL_USER {
    private Long    seqNo;
    private String  createDt;
    private String  updateDt;
    private String  clntId;

    private String  externalClntId;
    private String  externalClntPw;

    private Long    mbId;
    private Long    adId;
    private Long    caId;
    private Long    pgId;

    private String  status;
    private String  srtDt;
    private String  endDt;
    private String  description;

}
