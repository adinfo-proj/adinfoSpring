package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CampaignMaster {
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
            "       , BAN_CHANNEL_COND" +
            "       , BAN_IMAGE_COND" +
            "       , BAN_WORD_COND" +
            "       , AGE_TARGET" +
            " FROM " +
            "       CAMPAIGN_MASTER " +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   CA_ID  = ${caId}")
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
            @Result(property = "banChannelCond" , column = "BAN_CHANNEL_COND"),
            @Result(property = "banImageCond" , column = "BAN_IMAGE_COND"),
            @Result(property = "banWordCond" , column = "BAN_WORD_COND"),
            @Result(property = "ageTarget" , column = "AGE_TARGET")
    })
    CAMPAIGN_MASTER getCampaignMasterForMbCa(Long mbId, Long caId);

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
            "       , AD_STATUS " +
            "       , NAME " +
            "       , TP " +
            "       , TOP_KIND " +
            "       , MIDDLE_KIND " +
            "       , PURPOSE " +
            "       , PRICE " +
            "       , PROMOTION_PRICE " +
            "       , SNS_YN " +
            "       , FORM_YN " +
            "       , POTEN_YN " +
            "       , EXTERN_DATA_YN " +
            "       , DAY_LIMIT " +
            "       , REG_IP " +
            "       , SRT_DT " +
            "       , SRT_TM " +
            "       , END_DT " +
            "       , ENDTM " +
            "       , COMMENT " +
            "       , USP " +
            "       , REFER_ID " +
            "       , REQ_WORD_COND " +
            "       , ASK_LIST " +
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
            "       , BAN_CHANNEL_COND " +
            "       , BAN_IMAGE_COND " +
            "       , BAN_WORD_COND " +
            "       , AGE_TARGET " +
            ") " +
            "VALUES(" +
            "         NOW()" +
            "       , ${campaignMaster.mbId " +
            "       , ${campaignMaster.adId " +
            "       , ${campaignMaster.caId " +
            "       , #{campaignMaster.operId " +
            "       , #{campaignMaster.campaignKind " +
            "       , #{campaignMaster.campaignArea " +
            "       , #{campaignMaster.campaignAreaEtc " +
            "       , #{campaignMaster.status " +
            "       , #{campaignMaster.name " +
            "       , #{campaignMaster.tp " +
            "       , #{campaignMaster.topKind " +
            "       , #{campaignMaster.middleKind " +
            "       , #{campaignMaster.purpose " +
            "       , #{campaignMaster.pirce " +
            "       , #{campaignMaster.promotionPrice " +
            "       , #{campaignMaster.snsYn " +
            "       , #{campaignMaster.formYn " +
            "       , #{campaignMaster.potenYn " +
            "       , #{campaignMaster.externDataYn " +
            "       , #{campaignMaster.dayLimit " +
            "       , #{campaignMaster.regIp " +
            "       , #{campaignMaster.srtDt " +
            "       , #{campaignMaster.srtTm " +
            "       , #{campaignMaster.endDt " +
            "       , #{campaignMaster.endTm " +
            "       , #{campaignMaster.comment " +
            "       , #{campaignMaster.usp " +
            "       , #{campaignMaster.referId " +
            "       , #{campaignMaster.reqWordCond " +
            "       , #{campaignMaster.askList " +
            "       , #{campaignMaster.exceptMeant " +
            "       , #{campaignMaster.cnclData " +
            "       , #{campaignMaster.smsYn " +
            "       , #{campaignMaster.smsNo " +
            "       , #{campaignMaster.landingPageTitle " +
            "       , #{campaignMaster.landingUrl " +
            "       , #{campaignMaster.bannerPath " +
            "       , #{campaignMaster.autoConfirm " +
            "       , #{campaignMaster.approval " +
            "       , #{campaignMaster.nullifyCond " +
            "       , #{campaignMaster.cancelCond " +
            "       , #{campaignMaster.banCannelCond " +
            "       , #{campaignMaster.banImageCond " +
            "       , #{campaignMaster.banWordCond " +
            "       , #{campaignMaster.ageTarget " +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "caCaId")
    Long insCampaignMaster(@Param("campaignMaster") CAMPAIGN_MASTER campaignMaster);
}
