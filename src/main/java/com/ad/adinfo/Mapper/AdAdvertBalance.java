package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.AD_ADVERT_BALANCE;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdAdvertBalance {
    @Select("SELECT " +
            "         UPDATE_DT" +
            "       , AD_ID" +
            "       , CA_ID" +
            "       , ADVT_MEDIA" +
            "       , CHARGE_AMT" +
            "       , BONUS_AMT" +
            "       , SUPPORT_AMT" +
            "       , BEFORE_CHARGE_AMT" +
            "       , SMS_SEND_YN" +
            " FROM " +
            "       AD_ADVERT_BALANCE" +
            " WHERE " +
            "       AD_ID = #{adId}" +
            "AND    CA_ID = #{caId}" +
            "AND    ADVT_MEDIA = 'A' ")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "advtMedia" , column = "ADVT_MEDIA"),
            @Result(property = "chargeAmt" , column = "CHARGE_AMT"),
            @Result(property = "bonusAmt" , column = "BONUS_AMT"),
            @Result(property = "supportAmt" , column = "SUPPORT_AMT"),
            @Result(property = "beforeChargeAmt" , column = "BEFORE_CHARGE_AMT"),
            @Result(property = "smsSendYn" , column = "SMS_SEND_YN")
    })
    List<AD_ADVERT_BALANCE> getAdAdvertBalance(String adClntId);

    @Update("UPDATE AD_ADVERT_BALANCE " +
            "SET      CHARGE_AMT        = CHARGE_AMT        + #{adPrice} " +
            "       , BEFORE_CHARGE_AMT = BEFORE_CHARGE_AMT - #{adPrice} " +
            "WHERE  AD_ID     = #{adId} " +
            "AND    CA_ID     = #{caId}")
    Long setOneAdAdvertBalance( Long    adId
                              , Long    caId
                              , Long    adPrice);
}
