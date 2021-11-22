package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CPA_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CpaMaster {
    @Select("SELECT " +
            "         CA_UPDATE_DT" +
            "       , CA_CA_ID" +
            "       , CA_AD_ID" +
            "       , CA_STATUS" +
            "       , CA_NAME" +
            "       , CA_AD_TP" +
            "       , CA_KIND" +
            "       , CA_PRICE" +
            "       , CA_PROMOTION_PRICE" +
            "       , CA_SNS_YN" +
            "       , CA_FORM_YN" +
            "       , CA_POTEN_YN" +
            "       , CA_DAY_LIMIT" +
            "       , CA_REG_IP" +
            "       , CA_SRT_DT" +
            "       , CA_END_DT" +
            "       , CA_COMMENT" +
            "       , CA_REFER_ID" +
            "       , CA_ASK_LIST" +
            "       , CA_EXCEPT_MEANT" +
            "       , CA_REQ_MEANT" +
            "       , CA_CNCL_DATA" +
            "       , CA_SMS_YN" +
            "       , CA_SMS" +
            "       , CA_REF_URL" +
            "       , CA_BANNER_PATH" +
            "       , CA_AUTO_CONFIRM" +
            " FROM " +
            "       CPA_MASTER" +
            " ORDER BY CA_CA_ID DESC")
    @Results({
            @Result(property = "caUpdateDt" , column = "CA_UPDATE_DT"),
            @Result(property = "caCaId" , column = "CA_CA_ID"),
            @Result(property = "caAdId" , column = "CA_AD_ID"),
            @Result(property = "caStatus" , column = "CA_STATUS"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "caAdTp" , column = "CA_AD_TP"),
            @Result(property = "caKind" , column = "CA_KIND"),
            @Result(property = "caPrice" , column = "CA_PRICE"),
            @Result(property = "caPromotionPrice" , column = "CA_PROMOTION_PRICE"),
            @Result(property = "caSnsYn" , column = "CA_SNS_YN"),
            @Result(property = "caFormYn" , column = "CA_FORM_YN"),
            @Result(property = "caPotenYn" , column = "CA_POTEN_YN"),
            @Result(property = "caDayLimit" , column = "CA_DAY_LIMIT"),
            @Result(property = "caRegIp" , column = "CA_REG_IP"),
            @Result(property = "caSrtDt" , column = "CA_SRT_DT"),
            @Result(property = "caEndDt" , column = "CA_END_DT"),
            @Result(property = "caComment" , column = "CA_COMMENT"),
            @Result(property = "caReferId" , column = "CA_REFER_ID"),
            @Result(property = "caAskList" , column = "CA_ASK_LIST"),
            @Result(property = "caExceptMeant" , column = "CA_EXCEPT_MEANT"),
            @Result(property = "caReqMeant" , column = "CA_REQ_MEANT"),
            @Result(property = "caCnclData" , column = "CA_CNCL_DATA"),
            @Result(property = "caSmsYn" , column = "CA_SMS_YN"),
            @Result(property = "caSms" , column = "CA_SMS"),
            @Result(property = "caRefUrl" , column = "CA_REF_URL"),
            @Result(property = "caBannerPath" , column = "CA_BANNER_PATH"),
            @Result(property = "caAutoConfirm" , column = "CA_AUTO_CONFIRM")
    })
    List<CPA_MASTER> getCpaMasterAll();

    @Select("SELECT " +
            "         CA_UPDATE_DT" +
            "       , CA_CA_ID" +
            "       , CA_AD_ID" +
            "       , CA_STATUS" +
            "       , CA_NAME" +
            "       , CA_AD_TP" +
            "       , CA_KIND" +
            "       , CA_PRICE" +
            "       , CA_PROMOTION_PRICE" +
            "       , CA_SNS_YN" +
            "       , CA_FORM_YN" +
            "       , CA_POTEN_YN" +
            "       , CA_DAY_LIMIT" +
            "       , CA_REG_IP" +
            "       , CA_SRT_DT" +
            "       , CA_END_DT" +
            "       , CA_COMMENT" +
            "       , CA_REFER_ID" +
            "       , CA_ASK_LIST" +
            "       , CA_EXCEPT_MEANT" +
            "       , CA_REQ_MEANT" +
            "       , CA_CNCL_DATA" +
            "       , CA_SMS_YN" +
            "       , CA_SMS" +
            "       , CA_REF_URL" +
            "       , CA_BANNER_PATH" +
            "       , CA_AUTO_CONFIRM" +
            " FROM " +
            "       CPA_MASTER" +
            " WHERE " +
            "       CA_STATUS = 'R' " +
            " ORDER BY CA_CA_ID ${order}" +
            " LIMIT ${srt}, ${end}")
    @Results({
            @Result(property = "caUpdateDt" , column = "CA_UPDATE_DT"),
            @Result(property = "caCaId" , column = "CA_CA_ID"),
            @Result(property = "caAdId" , column = "CA_AD_ID"),
            @Result(property = "caStatus" , column = "CA_STATUS"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "caAdTp" , column = "CA_AD_TP"),
            @Result(property = "caKind" , column = "CA_KIND"),
            @Result(property = "caPrice" , column = "CA_PRICE"),
            @Result(property = "caPromotionPrice" , column = "CA_PROMOTION_PRICE"),
            @Result(property = "caSnsYn" , column = "CA_SNS_YN"),
            @Result(property = "caFormYn" , column = "CA_FORM_YN"),
            @Result(property = "caPotenYn" , column = "CA_POTEN_YN"),
            @Result(property = "caDayLimit" , column = "CA_DAY_LIMIT"),
            @Result(property = "caRegIp" , column = "CA_REG_IP"),
            @Result(property = "caSrtDt" , column = "CA_SRT_DT"),
            @Result(property = "caEndDt" , column = "CA_END_DT"),
            @Result(property = "caComment" , column = "CA_COMMENT"),
            @Result(property = "caReferId" , column = "CA_REFER_ID"),
            @Result(property = "caAskList" , column = "CA_ASK_LIST"),
            @Result(property = "caExceptMeant" , column = "CA_EXCEPT_MEANT"),
            @Result(property = "caReqMeant" , column = "CA_REQ_MEANT"),
            @Result(property = "caCnclData" , column = "CA_CNCL_DATA"),
            @Result(property = "caSmsYn" , column = "CA_SMS_YN"),
            @Result(property = "caSms" , column = "CA_SMS"),
            @Result(property = "caRefUrl" , column = "CA_REF_URL"),
            @Result(property = "caBannerPath" , column = "CA_BANNER_PATH"),
            @Result(property = "caAutoConfirm" , column = "CA_AUTO_CONFIRM")
    })
    List<CPA_MASTER> getCpaMasterByRownumAndLimit(String order, Long srt, Long end);

    @Insert("INSERT INTO CPA_MASTER " +
            "( " +
            "         CA_UPDATE_DT" +
            "       , CA_CA_ID" +
            "       , CA_AD_ID" +
            "       , CA_STATUS" +
            "       , CA_NAME" +
            "       , CA_AD_TP" +
            "       , CA_KIND" +
            "       , CA_PRICE" +
            "       , CA_PROMOTION_PRICE" +
            "       , CA_SNS_YN" +
            "       , CA_FORM_YN" +
            "       , CA_POTEN_YN" +
            "       , CA_DAY_LIMIT" +
            "       , CA_REG_IP" +
            "       , CA_SRT_DT" +
            "       , CA_END_DT" +
            "       , CA_COMMENT" +
            "       , CA_REFER_ID" +
            "       , CA_ASK_LIST" +
            "       , CA_EXCEPT_MEANT" +
            "       , CA_REQ_MEANT" +
            "       , CA_CNCL_DATA" +
            "       , CA_SMS_YN" +
            "       , CA_SMS" +
            "       , CA_REF_URL" +
            "       , CA_BANNER_PATH" +
            "       , CA_AUTO_CONFIRM" +
            ") " +
            "VALUES(" +
            "         NOW()" +
            "       , #{cpaMaster.caCaId}" +
            "       , #{cpaMaster.caAdId}" +
            "       , #{cpaMaster.caStatus}" +
            "       , #{cpaMaster.caName}" +
            "       , #{cpaMaster.caAdTp}" +
            "       , #{cpaMaster.caKind}" +
            "       , #{cpaMaster.caPrice}" +
            "       , #{cpaMaster.caPromotionPrice}" +
            "       , #{cpaMaster.caSnsYn}" +
            "       , #{cpaMaster.caFormYn}" +
            "       , #{cpaMaster.caPotenYn}" +
            "       , #{cpaMaster.caDayLimit}" +
            "       , #{cpaMaster.caRegIp}" +
            "       , #{cpaMaster.caSrtDt}" +
            "       , #{cpaMaster.caEndDt}" +
            "       , #{cpaMaster.caComment}" +
            "       , #{cpaMaster.caReferId}" +
            "       , #{cpaMaster.caAskList}" +
            "       , #{cpaMaster.caExceptMeant}" +
            "       , #{cpaMaster.caReqMeant}" +
            "       , #{cpaMaster.caCnclData}" +
            "       , #{cpaMaster.caSmsYn}" +
            "       , #{cpaMaster.caSms}" +
            "       , #{cpaMaster.caRefUrl}" +
            "       , #{cpaMaster.caBannerPath}" +
            "       , #{cpaMaster.caAutoConfirm}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "caCaId")
    Long insCpaMaster(@Param("cpaMaster") CPA_MASTER cpaMaster);

    @Select("SELECT COUNT(*) AS COUNT, '00' AS STATUS FROM CAMPAIGN_MASTER WHERE STATUS = '00' AND AD_ID = ${adId} " +
            "UNION ALL " +
            "SELECT COUNT(*) AS COUNT, '01' AS STATUS FROM CAMPAIGN_MASTER WHERE STATUS = '01' AND AD_ID = ${adId} " +
            "UNION ALL " +
            "SELECT COUNT(*) AS COUNT, '02' AS STATUS FROM CAMPAIGN_MASTER WHERE STATUS = '02' AND AD_ID = ${adId} " +
            "UNION ALL " +
            "SELECT COUNT(*) AS COUNT, '03' AS STATUS FROM CAMPAIGN_MASTER WHERE STATUS = '03' AND AD_ID = ${adId} " +
            "UNION ALL " +
            "SELECT COUNT(*) AS COUNT, '04' AS STATUS FROM CAMPAIGN_MASTER WHERE STATUS = '04' AND AD_ID = ${adId} " +
            "UNION ALL " +
            "SELECT COUNT(*) AS COUNT, '05' AS STATUS FROM CAMPAIGN_MASTER WHERE STATUS = '05' AND AD_ID = ${adId} ")
    @Results({
            @Result(property = "count" , column = "COUNT"),
            @Result(property = "status" , column = "STATUS")
    })
    List<Map<String, Object>> GetCampaignStatusCount(Long adId);

    @Select("SELECT " +
            "          UPDATE_DT " +
            "        , AD_ID " +
            "        , CA_ID " +
            "        , NAME " +
            "        , OPER_ID " +
            "FROM " +
            "        CAMPAIGN_MASTER " +
            "WHERE " +
            "      AD_ID      = ${adId} " +
            "AND   AD_TP      = 'A' " +
            "AND   STATUS     = #{status} " +
            "ORDER BY CA_ID DESC ")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "operId" , column = "OPER_ID")
    })
    ArrayList<Map<String, Object>> GetCampaignForStatus(Long adId, String status);

    @Select("SELECT " +
            "        CHARGE_AMT " +
            "FROM " +
            "        AD_ADVERT_BALANCE " +
            "WHERE " +
            "      AD_ID      = ${adId} " +
            "AND   CA_ID      = ${caId} " )
    @Results({
            @Result(property = "chargeAmt" , column = "CHARGE_AMT")

    })
    Long GetCampaignForRemainAmt(Long adId, Long caId);

    @Select("SELECT " +
            "        COUNT(*) COUNT " +
            "FROM " +
            "        CPA_DATA " +
            "WHERE " +
            "      AD_ID      = ${adId} " +
            "AND   CA_ID      = ${caId} " +
            "AND   INS_DT     = #{insDt} ")
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long GetCampaignForDateCount(Long adId, Long caId, String insDt);

    @Select("SELECT " +
            "        COUNT(*) COUNT " +
            "FROM " +
            "        CPA_DATA " +
            "WHERE " +
            "      AD_ID      = ${adId} " +
            "AND   CA_ID      = ${caId} " )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long GetCampaignForAllDayCount(Long adId, Long caId);
}
