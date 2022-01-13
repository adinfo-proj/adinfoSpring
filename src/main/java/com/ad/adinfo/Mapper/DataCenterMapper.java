package com.ad.adinfo.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataCenterMapper {
    //------------------------------------------------------------------------
    // 캠페인별 참여 마케터의 수
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT " +
            " FROM " +
            "       MAKETER_MASTER" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   CA_ID  = #{caId} " )
    Long DataCenterSummaryByTopToMaketer(Long mbId, Long caId);

    //------------------------------------------------------------------------
    // 누적 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" )
    Long DataCenterSummaryByTopToSumDBCount(Long mbId, Long adId, Long caId);

    //------------------------------------------------------------------------
    // 당일 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" +
            " AND   INS_DT = #{today}" )
    Long DataCenterBySummaryForTodayDBCount(Long mbId, Long adId, Long caId, String today);

    //------------------------------------------------------------------------
    // 누적 유효 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       MB_ID  = #{mbId}" +
            " AND   AD_ID  = #{adId}" +
            " AND   CA_ID  = #{caId}" +
            " AND   CONFIRM_TP = #{confirmTp}" )
    Long DataCenterBySummaryForValidDBCount(Long mbId, Long adId, Long caId, String confirmTp);

    //------------------------------------------------------------------------
    // 누적 무효 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       MB_ID  = #{mbId}" +
            " AND   AD_ID  = #{adId}" +
            " AND   CA_ID  = #{caId}" +
            " AND   CONFIRM_TP = #{confirmTp}" )
    Long DataCenterBySummaryForInvalidDBCount(Long mbId, Long adId, Long caId, String confirmTp);

    //------------------------------------------------------------------------
    // 누적 전체 캠페인 중 중복 DB 접수건
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       COUNT(*) AS COUNT" +
            " FROM " +
            "       CPA_DATA" +
            " WHERE " +
            "       MB_ID  = #{mbId}" +
            " AND   AD_ID  = #{adId}" +
            " AND   CA_ID  = #{caId}" +
            " AND   ALL_MOBILE_DUP_YN = 'Y'" )
    Long DataCenterBySummaryForAllDupDBCount(Long mbId, Long adId, Long caId);

    //------------------------------------------------------------------------
    // 누적 전체 캠페인 중 중복 DB 접수건 (클릭수 / 노출수 * 100)
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "A.VIEW_COUNT / B.CLICK_COUNT * 100 AS CLICK_PER " +
            "FROM " +
            "( SELECT COUNT(EVENT_CD) AS VIEW_COUNT " +
            "FROM   CPA_PAGE_USING_COUNT " +
            "WHERE MB_ID  = #{mbId} " +
            "AND   AD_ID  = #{adId} " +
            "AND   CA_ID  = #{caId} " +
            "AND   EVENT_CD = 'M') A, " +
            "( SELECT COUNT(EVENT_CD) AS CLICK_COUNT " +
            "FROM   CPA_PAGE_USING_COUNT " +
            "WHERE MB_ID  = #{mbId} " +
            "AND   AD_ID  = #{adId} " +
            "AND   CA_ID  = #{caId} " +
            "AND   EVENT_CD = 'S') B " )
    Double DataCenterBySummaryForClickPer(Long mbId, Long adId, Long caId);

    //**************************************************************************************
    // DASHBOARD
    //**************************************************************************************
    //------------------------------------------------------------------------
    // 합산 예치금 (대행사 기준)
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "         FORMAT(SUM(AMOUNT), 0) AS AMOUNT" +
            " FROM " +
            "       AD_TRANSACTIONAL" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   SUMMARY_CD IN ('00001', '00003', '00005', '00007', '00009') " )
    String DataCenterBySummaryForDepositToAd(Long mbId, Long adId);

    //------------------------------------------------------------------------
    // 합산 예치금 (캠페인 기준)
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "         FORMAT(SUM(AMOUNT), 0) AS AMOUNT" +
            " FROM " +
            "       AD_TRANSACTIONAL" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" +
            " AND   SUMMARY_CD IN ('00001', '00003', '00005', '00007', '00009') " )
    String DataCenterBySummaryForDepositToCa(Long mbId, Long adId, Long caId);

    //------------------------------------------------------------------------
    // 합산 사용가능 충전금 (대행사 기준)
    //   - ADVT_MEDIA = 'T': 충전가능금액, 'A': CPA캠페인이 사용중인 충전금
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(CHARGE_AMT), 0) AS CHARGE_AMT " +
            " FROM " +
            "       AD_ADVERT_BALANCE" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   ADVT_MEDIA = #{tp} " )
    String DataCenterBySummaryForChargeAmtToAd(Long mbId, Long adId, String tp);

    //------------------------------------------------------------------------
    // 합산 사용가능 충전금 (캠페인 기준)
    //   - ADVT_MEDIA = 'T': 충전가능금액, 'A': CPA캠페인이 사용중인 충전금
    //------------------------------------------------------------------------
    @Select("SELECT " +
            "       FORMAT(SUM(CHARGE_AMT), 0) AS CHARGE_AMT " +
            " FROM " +
            "       AD_ADVERT_BALANCE" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   ADVT_MEDIA = #{tp} " )
    String DataCenterBySummaryForChargeAmtToCa(Long mbId, Long adId, Long caId, String tp);

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
