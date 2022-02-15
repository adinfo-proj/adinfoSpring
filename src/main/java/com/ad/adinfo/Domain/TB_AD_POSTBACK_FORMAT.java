package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class TB_AD_POSTBACK_FORMAT {
    private     String      updateDt;
    private     String      clntId;

    private     Long        mbId;
    private     Long        adId;
    private     Long        caId;
    private     Long        pgId;
    private     Long        pbId;

    private     String      status;
    private     String      postbackIo;
    private     String      postbackUrl;
    private     String      sendType;
    private     String      sslYn;
    private     String      accessFlag;

    private     String      value01;
    private     String      value02;
    private     String      value03;
    private     String      value04;
    private     String      value05;
    private     String      value06;
    private     String      value07;
    private     String      value08;
    private     String      value09;
    private     String      value10;
}
