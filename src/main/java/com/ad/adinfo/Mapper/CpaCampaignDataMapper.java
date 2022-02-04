package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CPA_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CpaCampaignDataMapper {
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       PT_ID  = #{ptId}" +
            " AND   INS_DT = #{date}")
    Long getCpaCampaignDataTodayInCount(Long ptId, String date);

}
