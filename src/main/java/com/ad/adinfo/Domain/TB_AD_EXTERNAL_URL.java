package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class TB_AD_EXTERNAL_URL {
    private Long    seqNo;
    private String  createDt;
    private String  updateDt;

    private Long    mbId;
    private Long    adId;
    private Long    caId;
    private Long    pgId;

    private String  externalUrl;
    private String  status;
    private String  description;
}
