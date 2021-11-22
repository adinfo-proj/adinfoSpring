package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class CPA_MASTER {
    private String  caUpdateDt;
    private Long    caCaId;
    private Long    caAdId;
    private String  caStatus;
    private String  caName;
    private String  caAdTp;
    private String  caKind;
    private Long    caPrice;
    private Long    caPromotionPrice;
    private String  caSnsYn;
    private String  caFormYn;
    private String  caPotenYn;
    private Long    caDayLimit;
    private String  caRegIp;
    private String  caSrtDt;
    private String  caEndDt;
    private String  caComment;
    private String  caReferId;
    private String  caAskList;
    private String  caExceptMeant;
    private String  caReqMeant;
    private String  caCnclData;
    private String  caSmsYn;
    private String  caSms;
    private String  caRefUrl;
    private String  caBannerPath;
    private Long    caAutoConfirm;
}
