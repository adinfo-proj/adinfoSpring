package com.ad.adinfo.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
    // 합산 예치금
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(AMOUNT), 0) AS AMOUNT" +
            " FROM " +
            "       AD_TRANSACTIONAL" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   SUMMARY_CD IN ('00001', '00003', '00005', '00007', '00009') " )
    String DataCenterBySummaryForDeposit(Long mbId, Long adId);

    //------------------------------------------------------------------------
    // 합산 예치금
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(CHARGE_AMT), 0) AS CHARGE_AMT " +
            " FROM " +
            "       AD_ADVERT_BALANCE" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   ADVT_MEDIA = 'T' " )
    String DataCenterBySummaryForDepositRemain(Long mbId, Long adId);

    //------------------------------------------------------------------------
    // 합산 충전금(광고주기준)
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(CHARGE_AMT), 0) AS CHARGE_AMT " +
            " FROM " +
            "       AD_ADVERT_BALANCE" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   ADVT_MEDIA = 'A' " )
    String DataCenterBySummaryForChargeAmt(Long mbId, Long adId);

    //------------------------------------------------------------------------
    // 합산 충전금 (캠페인기준)
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(AMOUNT), 0) AS CHARGE_AMT " +
            " FROM " +
            "       AD_EXCHANGE" +
            " WHERE " +
            "       MB_ID         = ${mbId}" +
            " AND   AD_ID         = ${adId}" +
            " AND   TO_CA_ID      = ${caId}" +
            " AND   TO_ADVT_MEDIA = 'A' " )
    String DataCenterBySummaryForExchangeAmt(Long mbId, Long adId, Long caId);

    //------------------------------------------------------------------------
    // 캠페인별 합산 충전금
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "         NAME " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            " FROM " +
            "       CAMPAIGN_MASTER" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   STATUS = #{flag} " )
    List<Map<String, Object>> DataCenterByLiveGridToMaster(Long mbId, Long adId, String flag);

    //------------------------------------------------------------------------
    // 캠페인별 잔여충전금
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(CHARGE_AMT), 0) AS CHARGE_AMT " +
            " FROM " +
            "       AD_ADVERT_BALANCE" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" +
            " AND   ADVT_MEDIA = 'A' " )
    String DataCenterBySummaryForAdAdvtyBalanceCampRemainAmt(Long mbId, Long adId, Long caId);











}
