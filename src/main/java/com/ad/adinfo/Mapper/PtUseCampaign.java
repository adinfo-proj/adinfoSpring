package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PtUseCampaign {
    @Select(" SELECT " +
            "          DISTINCT " +
            "          B.MK_ID  MK_ID " +
            "        , B.MK_CD  MK_CD " +
            "        , A.CA_ID  CA_ID " +
            " FROM " +
            "          CPA_DATA       A " +
            "        , AD_USER_MASTER B " +
            " WHERE " +
            "        A.CA_ID = ${campaignId} " +
            " AND    A.MK_ID = B.MK_ID " +
            " ORDER BY B.MK_ID ")
    @Results({
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "mkCd" , column = "MK_CD"),
            @Result(property = "caId" , column = "CA_ID")
    })
    List<AD_USER_MASTER> CampaignPtIdList(Long campaignId);
}
