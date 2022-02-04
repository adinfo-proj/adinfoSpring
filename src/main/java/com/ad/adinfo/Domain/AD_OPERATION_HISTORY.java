package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class AD_OPERATION_HISTORY {
    private Long    seqNo;
    private String  creDt;
    private String  creTm;
    private String  clntTp;
    private Long    mbId;
    private String  clntId;
    private String  commonCd;
    private String  comment;
}
