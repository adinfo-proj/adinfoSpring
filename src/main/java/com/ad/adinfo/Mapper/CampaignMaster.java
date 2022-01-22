package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import com.ad.adinfo.Domain.COMMON_CODE;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CampaignMaster {
    @Select("SELECT " +
            "       MAX(CA_ID) AS CA_ID" +
            " FROM " +
            "       CAMPAIGN_MASTER" +
            " WHERE " +
            "       MB_ID      = #{mbId}" +
            " AND   AD_ID      = #{adId}" )
    @Results({
            @Result(property = "caId" , column = "CA_ID")
    })
    Long getCampaignMasterMaxCaId(Long mbId, Long adId);

    @Select("SELECT " +
            "         UPDATE_DT" +
            "       , MB_ID" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , OPER_ID" +
            "       , CAMPAIGN_KIND" +
            "       , CAMPAIGN_AREA" +
            "       , CAMPAIGN_AREA_ETC" +
            "       , STATUS" +
            "       , NAME" +
            "       , TP" +
            "       , TOP_KIND" +
            "       , MIDDLE_KIND" +
            "       , PURPOSE" +
            "       , PRICE" +
            "       , PROMOTION_PRICE" +
            "       , MARKETER_PRICE" +
            "       , SNS_YN" +
            "       , FORM_YN" +
            "       , POTEN_YN" +
            "       , EXTERN_DATA_YN" +
            "       , DAY_LIMIT" +
            "       , REG_IP" +
            "       , SRT_DT" +
            "       , SRT_TM" +
            "       , END_DT" +
            "       , END_TM" +
            "       , COMMENT" +
            "       , USP" +
            "       , REFER_ID" +
            "       , REQ_WORD_COND" +
            "       , ASK_LIST" +
            "       , EXCEPT_MEANT" +
            "       , CNCL_DATA" +
            "       , SMS_YN" +
            "       , SMS_NO" +
            "       , LANDING_PAGE_TITLE" +
            "       , LANDING_URL" +
            "       , BANNER_PATH" +
            "       , AUTO_CONFIRM" +
            "       , APPROVAL" +
            "       , NULLIFY_COND" +
            "       , CANCEL_COND" +
            "       , BAN_EX_CHANNEL_COND" +
            "       , BAN_CHANNEL_COND" +
            "       , BAN_IMAGE_COND" +
            "       , BAN_WORD_COND" +
            "       , AGE_TARGET" +
            " FROM " +
            "       CAMPAIGN_MASTER " +
            " ORDER BY CA_ID DESC " +
            " LIMIT ${count} ")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "operId" , column = "OPER_ID"),
            @Result(property = "campaignKind" , column = "CAMPAIGN_KIND"),
            @Result(property = "campaignArea" , column = "CAMPAIGN_AREA"),
            @Result(property = "campaignAreaEtc" , column = "CAMPAIGN_AREA_ETC"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "tp" , column = "TP"),
            @Result(property = "topKind" , column = "TOP_KIND"),
            @Result(property = "middleKind" , column = "MIDDLE_KIND"),
            @Result(property = "purpose" , column = "PURPOSE"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "promotionPrice" , column = "PROMOTION_PRICE"),
            @Result(property = "marketerPrice" , column = "MARKETER_PRICE"),
            @Result(property = "snsYn" , column = "SNS_YN"),
            @Result(property = "formYn" , column = "FORM_YN"),
            @Result(property = "potenYn" , column = "POTEN_YN"),
            @Result(property = "externDataYn" , column = "EXTERN_DATA_YN"),
            @Result(property = "dayLimit" , column = "DAY_LIMIT"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "srtDt" , column = "SRT_DT"),
            @Result(property = "srtTm" , column = "SRT_TM"),
            @Result(property = "endDt" , column = "END_DT"),
            @Result(property = "endTm" , column = "END_TM"),
            @Result(property = "comment" , column = "COMMENT"),
            @Result(property = "usp" , column = "USP"),
            @Result(property = "referId" , column = "REFER_ID"),
            @Result(property = "reqWordCond" , column = "REQ_WORD_COND"),
            @Result(property = "askList" , column = "ASK_LIST"),
            @Result(property = "exceptMeant" , column = "EXCEPT_MEANT"),
            @Result(property = "cnclData" , column = "CNCL_DATA"),
            @Result(property = "smsYn" , column = "SMS_YN"),
            @Result(property = "smsNo" , column = "SMS_NO"),
            @Result(property = "landingPageTitle" , column = "LANDING_PAGE_TITLE"),
            @Result(property = "landingUrl" , column = "LANDING_URL"),
            @Result(property = "bannerPath" , column = "BANNER_PATH"),
            @Result(property = "autoConfirm" , column = "AUTO_CONFIRM"),
            @Result(property = "approval" , column = "APPROVAL"),
            @Result(property = "nullifyCond" , column = "NULLIFY_COND"),
            @Result(property = "cancelCond" , column = "CANCEL_COND"),
            @Result(property = "banExChannelCond" , column = "BAN_EX_CHANNEL_COND"),
            @Result(property = "banChannelCond" , column = "BAN_CHANNEL_COND"),
            @Result(property = "banImageCond" , column = "BAN_IMAGE_COND"),
            @Result(property = "banWordCond" , column = "BAN_WORD_COND"),
            @Result(property = "ageTarget" , column = "AGE_TARGET")
    })
    List<CAMPAIGN_MASTER> getCampaignMasterAll(Long count);   // Count값이 0인경우 전체

    @Select("SELECT " +
            "         UPDATE_DT" +
            "       , MB_ID" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , OPER_ID" +
            "       , CAMPAIGN_KIND" +
            "       , CAMPAIGN_AREA" +
            "       , CAMPAIGN_AREA_ETC" +
            "       , STATUS" +
            "       , NAME" +
            "       , TP" +
            "       , TOP_KIND" +
            "       , MIDDLE_KIND" +
            "       , PURPOSE" +
            "       , PRICE" +
            "       , PROMOTION_PRICE" +
            "       , MARKETER_PRICE" +
            "       , SNS_YN" +
            "       , FORM_YN" +
            "       , POTEN_YN" +
            "       , EXTERN_DATA_YN" +
            "       , DAY_LIMIT" +
            "       , REG_IP" +
            "       , SRT_DT" +
            "       , SRT_TM" +
            "       , END_DT" +
            "       , END_TM" +
            "       , COMMENT" +
            "       , USP" +
            "       , REFER_ID" +
            "       , REQ_WORD_COND" +
            "       , ASK_LIST" +
            "       , EXCEPT_MEANT" +
            "       , CNCL_DATA" +
            "       , SMS_YN" +
            "       , SMS_NO" +
            "       , LANDING_PAGE_TITLE" +
            "       , LANDING_URL" +
            "       , BANNER_PATH" +
            "       , AUTO_CONFIRM" +
            "       , APPROVAL" +
            "       , NULLIFY_COND" +
            "       , CANCEL_COND" +
            "       , BAN_EX_CHANNEL_COND" +
            "       , BAN_CHANNEL_COND" +
            "       , BAN_IMAGE_COND" +
            "       , BAN_WORD_COND" +
            "       , AGE_TARGET" +
            " FROM " +
            "       CAMPAIGN_MASTER " +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" )
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "operId" , column = "OPER_ID"),
            @Result(property = "campaignKind" , column = "CAMPAIGN_KIND"),
            @Result(property = "campaignArea" , column = "CAMPAIGN_AREA"),
            @Result(property = "campaignAreaEtc" , column = "CAMPAIGN_AREA_ETC"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "tp" , column = "TP"),
            @Result(property = "topKind" , column = "TOP_KIND"),
            @Result(property = "middleKind" , column = "MIDDLE_KIND"),
            @Result(property = "purpose" , column = "PURPOSE"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "promotionPrice" , column = "PROMOTION_PRICE"),
            @Result(property = "marketerPrice" , column = "MARKETER_PRICE"),
            @Result(property = "snsYn" , column = "SNS_YN"),
            @Result(property = "formYn" , column = "FORM_YN"),
            @Result(property = "potenYn" , column = "POTEN_YN"),
            @Result(property = "externDataYn" , column = "EXTERN_DATA_YN"),
            @Result(property = "dayLimit" , column = "DAY_LIMIT"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "srtDt" , column = "SRT_DT"),
            @Result(property = "srtTm" , column = "SRT_TM"),
            @Result(property = "endDt" , column = "END_DT"),
            @Result(property = "endTm" , column = "END_TM"),
            @Result(property = "comment" , column = "COMMENT"),
            @Result(property = "usp" , column = "USP"),
            @Result(property = "referId" , column = "REFER_ID"),
            @Result(property = "reqWordCond" , column = "REQ_WORD_COND"),
            @Result(property = "askList" , column = "ASK_LIST"),
            @Result(property = "exceptMeant" , column = "EXCEPT_MEANT"),
            @Result(property = "cnclData" , column = "CNCL_DATA"),
            @Result(property = "smsYn" , column = "SMS_YN"),
            @Result(property = "smsNo" , column = "SMS_NO"),
            @Result(property = "landingPageTitle" , column = "LANDING_PAGE_TITLE"),
            @Result(property = "landingUrl" , column = "LANDING_URL"),
            @Result(property = "bannerPath" , column = "BANNER_PATH"),
            @Result(property = "autoConfirm" , column = "AUTO_CONFIRM"),
            @Result(property = "approval" , column = "APPROVAL"),
            @Result(property = "nullifyCond" , column = "NULLIFY_COND"),
            @Result(property = "cancelCond" , column = "CANCEL_COND"),
            @Result(property = "banExChannelCond" , column = "BAN_EX_CHANNEL_COND"),
            @Result(property = "banChannelCond" , column = "BAN_CHANNEL_COND"),
            @Result(property = "banImageCond" , column = "BAN_IMAGE_COND"),
            @Result(property = "banWordCond" , column = "BAN_WORD_COND"),
            @Result(property = "ageTarget" , column = "AGE_TARGET")
    })
    List<Map<String, Object>> getCampaignMasterForMbAd(Long mbId, Long adId);

    @Select("SELECT " +
            "         UPDATE_DT" +
            "       , MB_ID" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , OPER_ID" +
            "       , CAMPAIGN_KIND" +
            "       , CAMPAIGN_AREA" +
            "       , CAMPAIGN_AREA_ETC" +
            "       , STATUS" +
            "       , NAME" +
            "       , TP" +
            "       , TOP_KIND" +
            "       , MIDDLE_KIND" +
            "       , PURPOSE" +
            "       , FORMAT(PRICE, 0) PRICE" +
            "       , FORMAT(PROMOTION_PRICE, 0) PROMOTION_PRICE" +
            "       , FORMAT(MARKETER_PRICE, 0) MARKETER_PRICE" +
            "       , SNS_YN" +
            "       , FORM_YN" +
            "       , POTEN_YN" +
            "       , EXTERN_DATA_YN" +
            "       , DAY_LIMIT" +
            "       , REG_IP" +
            "       , CONCAT( SUBSTRING(SRT_DT, 1, 4), '-', SUBSTRING(SRT_DT, 5, 2), '-', SUBSTRING(SRT_DT, 7, 2)) SRT_DT " +
            "       , CONCAT( SUBSTRING(SRT_TM, 1, 2), ':', SUBSTRING(SRT_TM, 3, 2), ':', SUBSTRING(SRT_TM, 5, 2)) SRT_TM " +
            "       , CONCAT( SUBSTRING(END_DT, 1, 4), '-', SUBSTRING(END_DT, 5, 2), '-', SUBSTRING(END_DT, 7, 2)) END_DT " +
            "       , CONCAT( SUBSTRING(END_TM, 1, 2), ':', SUBSTRING(END_TM, 3, 2), ':', SUBSTRING(END_TM, 5, 2)) END_TM " +
            "       , COMMENT" +
            "       , USP" +
            "       , REFER_ID" +
            "       , REQ_WORD_COND" +
            "       , ASK_LIST" +
            "       , EXCEPT_MEANT" +
            "       , CNCL_DATA" +
            "       , SMS_YN" +
            "       , SMS_NO" +
            "       , LANDING_PAGE_TITLE" +
            "       , LANDING_URL" +
            "       , BANNER_PATH" +
            "       , AUTO_CONFIRM" +
            "       , APPROVAL" +
            "       , NULLIFY_COND" +
            "       , CANCEL_COND" +
            "       , BAN_EX_CHANNEL_COND" +
            "       , BAN_CHANNEL_COND" +
            "       , BAN_IMAGE_COND" +
            "       , BAN_WORD_COND" +
            "       , AGE_TARGET" +
            " FROM " +
            "       CAMPAIGN_MASTER " +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   STATUS LIKE #{status}" )
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "operId" , column = "OPER_ID"),
            @Result(property = "campaignKind" , column = "CAMPAIGN_KIND"),
            @Result(property = "campaignArea" , column = "CAMPAIGN_AREA"),
            @Result(property = "campaignAreaEtc" , column = "CAMPAIGN_AREA_ETC"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "tp" , column = "TP"),
            @Result(property = "topKind" , column = "TOP_KIND"),
            @Result(property = "middleKind" , column = "MIDDLE_KIND"),
            @Result(property = "purpose" , column = "PURPOSE"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "promotionPrice" , column = "PROMOTION_PRICE"),
            @Result(property = "marketerPrice" , column = "MARKETER_PRICE"),
            @Result(property = "snsYn" , column = "SNS_YN"),
            @Result(property = "formYn" , column = "FORM_YN"),
            @Result(property = "potenYn" , column = "POTEN_YN"),
            @Result(property = "externDataYn" , column = "EXTERN_DATA_YN"),
            @Result(property = "dayLimit" , column = "DAY_LIMIT"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "srtDt" , column = "SRT_DT"),
            @Result(property = "srtTm" , column = "SRT_TM"),
            @Result(property = "endDt" , column = "END_DT"),
            @Result(property = "endTm" , column = "END_TM"),
            @Result(property = "comment" , column = "COMMENT"),
            @Result(property = "usp" , column = "USP"),
            @Result(property = "referId" , column = "REFER_ID"),
            @Result(property = "reqWordCond" , column = "REQ_WORD_COND"),
            @Result(property = "askList" , column = "ASK_LIST"),
            @Result(property = "exceptMeant" , column = "EXCEPT_MEANT"),
            @Result(property = "cnclData" , column = "CNCL_DATA"),
            @Result(property = "smsYn" , column = "SMS_YN"),
            @Result(property = "smsNo" , column = "SMS_NO"),
            @Result(property = "landingPageTitle" , column = "LANDING_PAGE_TITLE"),
            @Result(property = "landingUrl" , column = "LANDING_URL"),
            @Result(property = "bannerPath" , column = "BANNER_PATH"),
            @Result(property = "autoConfirm" , column = "AUTO_CONFIRM"),
            @Result(property = "approval" , column = "APPROVAL"),
            @Result(property = "nullifyCond" , column = "NULLIFY_COND"),
            @Result(property = "cancelCond" , column = "CANCEL_COND"),
            @Result(property = "banExChannelCond" , column = "BAN_EX_CHANNEL_COND"),
            @Result(property = "banChannelCond" , column = "BAN_CHANNEL_COND"),
            @Result(property = "banImageCond" , column = "BAN_IMAGE_COND"),
            @Result(property = "banWordCond" , column = "BAN_WORD_COND"),
            @Result(property = "ageTarget" , column = "AGE_TARGET")
    })
    List<Map<String, Object>> getCampaignMasterForMbAdStatus(Long mbId, Long adId, String status);

    @Select("SELECT " +
            "         UPDATE_DT" +
            "       , MB_ID" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , OPER_ID" +
            "       , CAMPAIGN_KIND" +
            "       , CAMPAIGN_AREA" +
            "       , CAMPAIGN_AREA_ETC" +
            "       , STATUS" +
            "       , NAME" +
            "       , TP" +
            "       , TOP_KIND" +
            "       , MIDDLE_KIND" +
            "       , PURPOSE" +
            "       , PRICE" +
            "       , PROMOTION_PRICE" +
            "       , MARKETER_PRICE" +
            "       , SNS_YN" +
            "       , FORM_YN" +
            "       , POTEN_YN" +
            "       , EXTERN_DATA_YN" +
            "       , DAY_LIMIT" +
            "       , REG_IP" +
            "       , SRT_DT" +
            "       , SRT_TM" +
            "       , END_DT" +
            "       , END_TM" +
            "       , COMMENT" +
            "       , USP" +
            "       , REFER_ID" +
            "       , REQ_WORD_COND" +
            "       , ASK_LIST" +
            "       , EXCEPT_MEANT" +
            "       , CNCL_DATA" +
            "       , SMS_YN" +
            "       , SMS_NO" +
            "       , LANDING_PAGE_TITLE" +
            "       , LANDING_URL" +
            "       , BANNER_PATH" +
            "       , AUTO_CONFIRM" +
            "       , APPROVAL" +
            "       , NULLIFY_COND" +
            "       , CANCEL_COND" +
            "       , BAN_EX_CHANNEL_COND" +
            "       , BAN_CHANNEL_COND" +
            "       , BAN_IMAGE_COND" +
            "       , BAN_WORD_COND" +
            "       , AGE_TARGET" +
            " FROM " +
            "       CAMPAIGN_MASTER " +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   CA_ID  = ${caId}" )
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "operId" , column = "OPER_ID"),
            @Result(property = "campaignKind" , column = "CAMPAIGN_KIND"),
            @Result(property = "campaignArea" , column = "CAMPAIGN_AREA"),
            @Result(property = "campaignAreaEtc" , column = "CAMPAIGN_AREA_ETC"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "tp" , column = "TP"),
            @Result(property = "topKind" , column = "TOP_KIND"),
            @Result(property = "middleKind" , column = "MIDDLE_KIND"),
            @Result(property = "purpose" , column = "PURPOSE"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "promotionPrice" , column = "PROMOTION_PRICE"),
            @Result(property = "marketerPrice" , column = "MARKETER_PRICE"),
            @Result(property = "snsYn" , column = "SNS_YN"),
            @Result(property = "formYn" , column = "FORM_YN"),
            @Result(property = "potenYn" , column = "POTEN_YN"),
            @Result(property = "externDataYn" , column = "EXTERN_DATA_YN"),
            @Result(property = "dayLimit" , column = "DAY_LIMIT"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "srtDt" , column = "SRT_DT"),
            @Result(property = "srtTm" , column = "SRT_TM"),
            @Result(property = "endDt" , column = "END_DT"),
            @Result(property = "endTm" , column = "END_TM"),
            @Result(property = "comment" , column = "COMMENT"),
            @Result(property = "usp" , column = "USP"),
            @Result(property = "referId" , column = "REFER_ID"),
            @Result(property = "reqWordCond" , column = "REQ_WORD_COND"),
            @Result(property = "askList" , column = "ASK_LIST"),
            @Result(property = "exceptMeant" , column = "EXCEPT_MEANT"),
            @Result(property = "cnclData" , column = "CNCL_DATA"),
            @Result(property = "smsYn" , column = "SMS_YN"),
            @Result(property = "smsNo" , column = "SMS_NO"),
            @Result(property = "landingPageTitle" , column = "LANDING_PAGE_TITLE"),
            @Result(property = "landingUrl" , column = "LANDING_URL"),
            @Result(property = "bannerPath" , column = "BANNER_PATH"),
            @Result(property = "autoConfirm" , column = "AUTO_CONFIRM"),
            @Result(property = "approval" , column = "APPROVAL"),
            @Result(property = "nullifyCond" , column = "NULLIFY_COND"),
            @Result(property = "cancelCond" , column = "CANCEL_COND"),
            @Result(property = "banExChannelCond" , column = "BAN_EX_CHANNEL_COND"),
            @Result(property = "banChannelCond" , column = "BAN_CHANNEL_COND"),
            @Result(property = "banImageCond" , column = "BAN_IMAGE_COND"),
            @Result(property = "banWordCond" , column = "BAN_WORD_COND"),
            @Result(property = "ageTarget" , column = "AGE_TARGET")
    })
    CAMPAIGN_MASTER getCampaignMasterForMbAdCa(Long mbId, Long adId, Long caId);

    @Insert("INSERT INTO CAMPAIGN_MASTER " +
            "( " +
            "         UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , OPER_ID " +
            "       , CAMPAIGN_KIND " +
            "       , CAMPAIGN_AREA " +
            "       , CAMPAIGN_AREA_ETC " +
            "       , STATUS " +
            "       , NAME " +
            "       , TP " +
            "       , TOP_KIND " +
            "       , MIDDLE_KIND " +
            "       , PURPOSE " +
            "       , PRICE " +
            "       , PROMOTION_PRICE " +
            "       , MARKETER_PRICE " +
            "       , SNS_YN " +
            "       , FORM_YN " +
            "       , POTEN_YN " +
            "       , EXTERN_DATA_YN " +
            "       , DAY_LIMIT " +
            "       , REG_IP " +
            "       , SRT_DT " +
            "       , SRT_TM " +
            "       , END_DT " +
            "       , END_TM " +
            "       , COMMENT " +
            "       , USP " +
            "       , REFER_ID " +
            "       , ASK_LIST " +
            "       , REQ_WORD_COND " +
            "       , EXCEPT_MEANT " +
            "       , CNCL_DATA " +
            "       , SMS_YN " +
            "       , SMS_NO " +
            "       , LANDING_PAGE_TITLE " +
            "       , LANDING_URL " +
            "       , BANNER_PATH " +
            "       , AUTO_CONFIRM " +
            "       , APPROVAL " +
            "       , NULLIFY_COND " +
            "       , CANCEL_COND " +
            "       , BAN_EX_CHANNEL_COND" +
            "       , BAN_CHANNEL_COND " +
            "       , BAN_IMAGE_COND " +
            "       , BAN_WORD_COND " +
            "       , AGE_TARGET " +
            ") " +
            "VALUES(" +
            "         NOW()" +
            "       , #{campaignMaster.mbId}" +
            "       , #{campaignMaster.adId}" +
            "       , #{campaignMaster.caId}" +
            "       , #{campaignMaster.operId}" +
            "       , #{campaignMaster.campaignKind}" +
            "       , #{campaignMaster.campaignArea}" +
            "       , #{campaignMaster.campaignAreaEtc}" +
            "       , #{campaignMaster.status}" +
            "       , #{campaignMaster.name}" +
            "       , #{campaignMaster.tp}" +
            "       , #{campaignMaster.topKind}" +
            "       , #{campaignMaster.middleKind}" +
            "       , #{campaignMaster.purpose}" +
            "       , #{campaignMaster.price}" +
            "       , #{campaignMaster.promotionPrice}" +
            "       , #{campaignMaster.marketerPrice}" +
            "       , #{campaignMaster.snsYn}" +
            "       , #{campaignMaster.formYn}" +
            "       , #{campaignMaster.potenYn}" +
            "       , #{campaignMaster.externDataYn}" +
            "       , #{campaignMaster.dayLimit}" +
            "       , #{campaignMaster.regIp}" +
            "       , #{campaignMaster.srtDt}" +
            "       , #{campaignMaster.srtTm}" +
            "       , #{campaignMaster.endDt}" +
            "       , #{campaignMaster.endTm}" +
            "       , #{campaignMaster.comment}" +
            "       , #{campaignMaster.usp}" +
            "       , #{campaignMaster.referId}" +
            "       , #{campaignMaster.askList}" +
            "       , #{campaignMaster.reqWordCond}" +
            "       , #{campaignMaster.exceptMeant}" +
            "       , #{campaignMaster.cnclData}" +
            "       , #{campaignMaster.smsYn}" +
            "       , #{campaignMaster.smsNo}" +
            "       , #{campaignMaster.landingPageTitle}" +
            "       , #{campaignMaster.landingUrl}" +
            "       , #{campaignMaster.bannerPath}" +
            "       , #{campaignMaster.autoConfirm}" +
            "       , #{campaignMaster.approval}" +
            "       , #{campaignMaster.nullifyCond}" +
            "       , #{campaignMaster.cancelCond}" +
            "       , #{campaignMaster.banExChannelCond}" +
            "       , #{campaignMaster.banChannelCond}" +
            "       , #{campaignMaster.banImageCond}" +
            "       , #{campaignMaster.banWordCond}" +
            "       , #{campaignMaster.ageTarget}" +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "caId")
    Long insCampaignMaster(@Param("campaignMaster") CAMPAIGN_MASTER campaignMaster);

    @Insert("INSERT INTO CAMPAIGN_MASTER_HISTORY " +
            "( " +
            "         SEQ_NO " +
            "       , UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , OPER_ID " +
            "       , CAMPAIGN_KIND " +
            "       , CAMPAIGN_AREA " +
            "       , CAMPAIGN_AREA_ETC " +
            "       , STATUS " +
            "       , NAME " +
            "       , TP " +
            "       , TOP_KIND " +
            "       , MIDDLE_KIND " +
            "       , PURPOSE " +
            "       , PRICE " +
            "       , PROMOTION_PRICE " +
            "       , MARKETER_PRICE " +
            "       , SNS_YN " +
            "       , FORM_YN " +
            "       , POTEN_YN " +
            "       , EXTERN_DATA_YN " +
            "       , DAY_LIMIT " +
            "       , REG_IP " +
            "       , SRT_DT " +
            "       , SRT_TM " +
            "       , END_DT " +
            "       , END_TM " +
            "       , COMMENT " +
            "       , USP " +
            "       , REFER_ID " +
            "       , ASK_LIST " +
            "       , REQ_WORD_COND " +
            "       , EXCEPT_MEANT " +
            "       , CNCL_DATA " +
            "       , SMS_YN " +
            "       , SMS_NO " +
            "       , LANDING_PAGE_TITLE " +
            "       , LANDING_URL " +
            "       , BANNER_PATH " +
            "       , AUTO_CONFIRM " +
            "       , APPROVAL " +
            "       , NULLIFY_COND " +
            "       , CANCEL_COND " +
            "       , BAN_EX_CHANNEL_COND" +
            "       , BAN_CHANNEL_COND " +
            "       , BAN_IMAGE_COND " +
            "       , BAN_WORD_COND " +
            "       , AGE_TARGET " +
            ") " +
            "VALUES(" +
            "         0" +
            "       , NOW()" +
            "       , #{campaignMaster.mbId}" +
            "       , #{campaignMaster.adId}" +
            "       , #{campaignMaster.caId}" +
            "       , #{campaignMaster.operId}" +
            "       , #{campaignMaster.campaignKind}" +
            "       , #{campaignMaster.campaignArea}" +
            "       , #{campaignMaster.campaignAreaEtc}" +
            "       , #{campaignMaster.status}" +
            "       , #{campaignMaster.name}" +
            "       , #{campaignMaster.tp}" +
            "       , #{campaignMaster.topKind}" +
            "       , #{campaignMaster.middleKind}" +
            "       , #{campaignMaster.purpose}" +
            "       , #{campaignMaster.price}" +
            "       , #{campaignMaster.promotionPrice}" +
            "       , #{campaignMaster.marketerPrice}" +
            "       , #{campaignMaster.snsYn}" +
            "       , #{campaignMaster.formYn}" +
            "       , #{campaignMaster.potenYn}" +
            "       , #{campaignMaster.externDataYn}" +
            "       , #{campaignMaster.dayLimit}" +
            "       , #{campaignMaster.regIp}" +
            "       , #{campaignMaster.srtDt}" +
            "       , #{campaignMaster.srtTm}" +
            "       , #{campaignMaster.endDt}" +
            "       , #{campaignMaster.endTm}" +
            "       , #{campaignMaster.comment}" +
            "       , #{campaignMaster.usp}" +
            "       , #{campaignMaster.referId}" +
            "       , #{campaignMaster.askList}" +
            "       , #{campaignMaster.reqWordCond}" +
            "       , #{campaignMaster.exceptMeant}" +
            "       , #{campaignMaster.cnclData}" +
            "       , #{campaignMaster.smsYn}" +
            "       , #{campaignMaster.smsNo}" +
            "       , #{campaignMaster.landingPageTitle}" +
            "       , #{campaignMaster.landingUrl}" +
            "       , #{campaignMaster.bannerPath}" +
            "       , #{campaignMaster.autoConfirm}" +
            "       , #{campaignMaster.approval}" +
            "       , #{campaignMaster.nullifyCond}" +
            "       , #{campaignMaster.cancelCond}" +
            "       , #{campaignMaster.banExChannelCond}" +
            "       , #{campaignMaster.banChannelCond}" +
            "       , #{campaignMaster.banImageCond}" +
            "       , #{campaignMaster.banWordCond}" +
            "       , #{campaignMaster.ageTarget}" +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "caId")
    Long insCampaignMasterHistory(@Param("campaignMaster") CAMPAIGN_MASTER campaignMaster);

    @Update("UPDATE " +
            "       CAMPAIGN_MASTER " +
            "SET" +
            "         UPDATE_DT           = NOW()" +
            "       , MB_ID               = #{campaignMaster.mbId}" +
            "       , AD_ID               = #{campaignMaster.adId}" +
            "       , CA_ID               = #{campaignMaster.caId}" +
            "       , OPER_ID             = #{campaignMaster.operId}" +
            "       , CAMPAIGN_KIND       = #{campaignMaster.campaignKind}" +
            "       , CAMPAIGN_AREA       = #{campaignMaster.campaignArea}" +
            "       , CAMPAIGN_AREA_ETC   = #{campaignMaster.campaignAreaEtc}" +
            "       , STATUS              = #{campaignMaster.status}" +
            "       , NAME                = #{campaignMaster.name}" +
            "       , TP                  = #{campaignMaster.tp}" +
            "       , TOP_KIND            = #{campaignMaster.topKind}" +
            "       , MIDDLE_KIND         = #{campaignMaster.middleKind}" +
            "       , PURPOSE             = #{campaignMaster.purpose}" +
            "       , PRICE               = #{campaignMaster.price}" +
            "       , PROMOTION_PRICE     = #{campaignMaster.promotionPrice}" +
            "       , MARKETER_PRICE      = #{campaignMaster.marketerPrice}" +
            "       , SNS_YN              = #{campaignMaster.snsYn}" +
            "       , FORM_YN             = #{campaignMaster.formYn}" +
            "       , POTEN_YN            = #{campaignMaster.potenYn}" +
            "       , EXTERN_DATA_YN      = #{campaignMaster.externDataYn}" +
            "       , DAY_LIMIT           = #{campaignMaster.dayLimit}" +
            "       , REG_IP              = #{campaignMaster.regIp}" +
            "       , SRT_DT              = #{campaignMaster.srtDt}" +
            "       , SRT_TM              = #{campaignMaster.srtTm}" +
            "       , END_DT              = #{campaignMaster.endDt}" +
            "       , END_TM              = #{campaignMaster.endTm}" +
            "       , COMMENT             = #{campaignMaster.comment}" +
            "       , USP                 = #{campaignMaster.usp}" +
            "       , REFER_ID            = #{campaignMaster.referId}" +
            "       , ASK_LIST            = #{campaignMaster.askList}" +
            "       , REQ_WORD_COND       = #{campaignMaster.reqWordCond}" +
            "       , EXCEPT_MEANT        = #{campaignMaster.exceptMeant}" +
            "       , CNCL_DATA           = #{campaignMaster.cnclData}" +
            "       , SMS_YN              = #{campaignMaster.smsYn}" +
            "       , SMS_NO              = #{campaignMaster.smsNo}" +
            "       , LANDING_PAGE_TITLE  = #{campaignMaster.landingPageTitle}" +
            "       , LANDING_URL         = #{campaignMaster.landingUrl}" +
            "       , BANNER_PATH         = #{campaignMaster.bannerPath}" +
            "       , AUTO_CONFIRM        = #{campaignMaster.autoConfirm}" +
            "       , APPROVAL            = #{campaignMaster.approval}" +
            "       , NULLIFY_COND        = #{campaignMaster.nullifyCond}" +
            "       , CANCEL_COND         = #{campaignMaster.cancelCond}" +
            "       , BAN_EX_CHANNEL_COND = #{campaignMaster.banExChannelCond}" +
            "       , BAN_CHANNEL_COND    = #{campaignMaster.banChannelCond}" +
            "       , BAN_IMAGE_COND      = #{campaignMaster.banImageCond}" +
            "       , BAN_WORD_COND       = #{campaignMaster.banWordCond}" +
            "       , AGE_TARGET          = #{campaignMaster.ageTarget}" +
            " WHERE " +
            "       MB_ID      = #{campaignMaster.mbId}" +
            " AND   AD_ID      = #{campaignMaster.adId}" +
            " AND   CA_ID      = #{campaignMaster.caId}" )
    @Options(useGeneratedKeys = true, keyProperty = "caId")
    Long upCampaignMaster(@Param("campaignMaster") CAMPAIGN_MASTER campaignMaster);

    @Select("SELECT " +
            "         MB_ID" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , NAME" +
            " FROM " +
            "       CAMPAIGN_MASTER" +
            " WHERE " +
            "       MB_ID      = #{mbId}" +
            " AND   AD_ID      = #{adId}" )
    @Results({
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "name" , column = "NAME")
    })
    List<Map<String, Object>> getCampaignMasterNameList(Long mbId, Long adId);

    @Select("SELECT " +
            "         MB_ID" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , PG_ID" +
            "       , NAME" +
            " FROM " +
            "       LANDING_PAGE" +
            " WHERE " +
            "       MB_ID      = #{mbId}" +
            " AND   AD_ID      = #{adId}" +
            " AND   CA_ID      = #{caId}"
    )
    @Results({
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "name" , column = "NAME")
    })
    List<Map<String, Object>> getLandingNameList(Long mbId, Long adId, Long caId);

    @Select("SELECT " +
            "       ASK_LIST " +
            " FROM " +
            "       CAMPAIGN_MASTER" +
            " WHERE " +
            "       MB_ID      = #{mbId}" +
            " AND   AD_ID      = #{adId}" +
            " AND   CA_ID      = #{caId}" )
    @Results({
            @Result(property = "askList" , column = "ASK_LIST")
    })
    List<Map<String, Object>> getCampaignMasterAskList(Long mbId, Long adId, Long caId);

    @Select("SELECT " +
            "       COUNT(*) AS COUNT " +
            " FROM " +
            "       CAMPAIGN_MASTER" +
            " WHERE " +
            "       MB_ID      = #{mbId}" +
            " AND   AD_ID      = #{adId}" +
            " AND   NAME       = #{name}" )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long getCampaignMasterByName(Long mbId, Long adId, String name);
}