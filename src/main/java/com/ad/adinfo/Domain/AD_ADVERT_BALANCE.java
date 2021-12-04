package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class AD_ADVERT_BALANCE {
    private String  UpdateDt;
    private Long    mbId;
    private Long    adId;
    private Long    caId;
    private String  advtMedia;
    private Double  chargeAmt;
    private Double  bonusAmt;
    private Double  supportAmt;
    private Double  beforeChargeAmt;
    private String  smsSendYn;
    private String  zeroAmtSmsYn;
}
