package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.PT_USE_CAMPAIGN;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PtUseCampaign {
    @Select(" SELECT " +
            "          DISTINCT " +
            "          B.AD_PT_ID  PT_ID " +
            "        , B.AD_PT_CD  PT_CD " +
            "        , A.CA_ID     CA_ID " +
            " FROM " +
            "          CPA_DATA       A " +
            "        , AD_USER_MASTER B " +
            " WHERE " +
            "        A.CA_ID = ${campaignId} " +
            " AND    A.PT_ID = B.AD_PT_ID " +
            " ORDER BY B.AD_PT_ID ")
    @Results({
            @Result(property = "ptId" , column = "PT_ID"),
            @Result(property = "ptCd" , column = "PT_CD"),
            @Result(property = "caId" , column = "CA_ID")
    })
    List<PT_USE_CAMPAIGN> CampaignPtIdList(Long campaignId);
}
