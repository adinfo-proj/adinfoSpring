package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class TB_AD_TRANSACTIONAL {
    private     Long        seqNo     ;
    private     String      updateDt     ;
    private     String      creDt        ;
    private     String      creTm        ;
    private     Long        mbId         ;
    private     Long        adId         ;
    private     String      clntId       ;
    private     String      gradeCd      ;
    private     String      summaryCd    ;
    private     Long        amount       ;
    private     Long        amountVat    ;
    private     Long        incomeTax    ;
    private     String      incomeCd     ;
    private     String      incomeAcntNo ;
    private     String      incomeAcntNm ;
    private     String      outcomeCd    ;
    private     String      outcomeAcntNo;
    private     String      outcomeAcntNm;
    private     String      comment      ;
}
