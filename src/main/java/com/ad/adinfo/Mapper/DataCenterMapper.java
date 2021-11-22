package com.ad.adinfo.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DataCenterMapper {
    //------------------------------------------------------------------------
    // 참여 마케터의 수
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       PT_USE_CAMPAIGN" +
            " WHERE " +
            "       CA_ID  = ${caId}")
    Long DataCenterBySummaryForMaketerCount(Long caId);

    //------------------------------------------------------------------------
    // 누적 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" +
            " AND   INS_DT BETWEEN #{srtDt} AND #{endDt} " )
    Long DataCenterBySummaryForSumDBCount(String srtDt, String endDt, Long adId, Long caId);

    //------------------------------------------------------------------------
    // 당일 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" +
            " AND   INS_DT = #{date}")
    Long DataCenterBySummaryForTodayDBCount(Long adId, Long caId, String date);

    //------------------------------------------------------------------------
    // 누적 유효 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       AD_ID  = #{adId}" +
            " AND   CA_ID  = #{caId}" +
            " AND   CONFIRM_TP = #{confirmTp}" +
            " AND   INS_DT BETWEEN #{srtDt} AND #{endDt} " )
    Long DataCenterBySummaryForValidDBCount(String srtDt, String endDt, Long adId, Long caId, String confirmTp);

    //------------------------------------------------------------------------
    // 누적 전체 캠페인 중 중복 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       AD_ID  = #{adId}" +
            " AND   CA_ID  = #{caId}" +
            " AND   INS_DT BETWEEN #{srtDt} AND #{endDt} " +
            " AND   ALL_MOBILE_DUP_YN = 'Y'" )
    Long DataCenterBySummaryForAllDupDBCount(String srtDt, String endDt, Long adId, Long caId);

    //------------------------------------------------------------------------
    // 누적 전체 캠페인 중 중복 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       AD_ID  = #{adId}" +
            " AND   CA_ID  = #{caId}" +
            " AND   INS_DT BETWEEN #{srtDt} AND #{endDt} " +
            " AND   THIS_MOBILE_DUP_YN = 'Y'" )
    Long DataCenterBySummaryForThisDupDBCount(String srtDt, String endDt, Long adId, Long caId);
}
