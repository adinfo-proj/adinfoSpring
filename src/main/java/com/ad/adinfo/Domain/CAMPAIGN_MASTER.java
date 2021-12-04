package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class CAMPAIGN_MASTER {
    private String  updateDt;
    private Long    mbId;
    private Long    adId;
    private Long    caId;
    private String  operId;
    private String  campaignKind;
    private String  campaignArea;
    private String  campaignAreaEtc;
    private String  status;
    private String  name;
    private String  tp;
    private String  topKind;
    private String  middleKind;
    private String  purpose;
    private Long    price;
    private Long    promotionPrice;
    private String  snsYn;
    private String  formYn;
    private String  potenYn;
    private String  externDataYn;
    private Long    dayLimit;
    private String  regIp;
    private String  srtDt;
    private String  srtTm;
    private String  endDt;
    private String  endTm;
    private String  comment;
    private String  usp;
    private String  referId;
    private String  askList;
    private String  reqWordCond;
    private String  exceptMeant;
    private String  cnclData;
    private String  smsYn;
    private String  smsNo;
    private String  landingPageTitle;
    private String  landingUrl;
    private String  bannerPath;
    private Long    autoConfirm;
    private Double  approval;
    private String  nullifyCond;
    private String  cancelCond;
    private String  banExChannelCond;
    private String  banChannelCond;
    private String  banImageCond;
    private String  banWordCond;
    private String  ageTarget;
}
