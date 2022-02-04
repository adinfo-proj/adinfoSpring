package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdUtilityMapper {
    @Select("SELECT DATE_FORMAT(DATE_SUB(#{calcDate}, INTERVAL ${calcDay} DAY), '%Y-%m-%d') CALC_DATE FROM DUAL")
    @Results({
            @Result(property = "calcDate" , column = "CALC_DATE")
    })
    String getCalcDays(String calcDate, Long calcDay);

    @Select("SELECT DATE_FORMAT(DATE_SUB(#{calcDate}, INTERVAL ${calcDay} WEEK), '%Y-%m-%d') CALC_DATE FROM DUAL")
    @Results({
            @Result(property = "calcDate" , column = "CALC_DATE")
    })
    String getCalcWeeks(String calcDate, Long calcDay);

    @Select("SELECT DATE_FORMAT(DATE_SUB(#{calcDate}, INTERVAL ${calcMonth} MONTH), '%Y-%m-%d') CALC_DATE FROM DUAL")
    @Results({
            @Result(property = "calcDate" , column = "CALC_DATE")
    })
    String getCalcMonth(String calcDate, Long calcMonth);

    @Select("SELECT DATE_FORMAT(#{calcDate}, '%Y-%m-%d') CALC_DATE FROM DUAL")
    @Results({
            @Result(property = "calcDate" , column = "CALC_DATE")
    })
    String getCalcCurDt(String calcDate);

    @Insert("INSERT INTO msg.msg(payload) " +
            "VALUES( " +
            "       json_object( " +
            "                   'to'  , #{to}, " +
            "                   'from', #{from}, " +
            "                   'text', #{text} " +
            "                  ) " +
            "      ) ")
    Long insertSms(String to, String from, String text);

    @Insert("INSERT INTO SMS_AUTH " +
            "VALUES( " +
            "          NOW() " +
            "        , #{smsNo} " +
            "        , #{authCode} " +
            "        , #{regId} " +
            "        , #{processTp} " +
            ") ")
    Long insertSmsAuth(String smsNo, String authCode, String regId, String processTp);

    @Select("SELECT IFNULL(TIMESTAMPDIFF(SECOND,  " +
            "(SELECT UPDATE_DT FROM SMS_AUTH WHERE SMS_NO = #{smsNo} ORDER BY UPDATE_DT DESC LIMIT 1), " +
            "NOW()), '-1') AS TIMEDIFF ")
    @Results({
            @Result(property = "timeDiff" , column = "TIMEDIFF")
    })
    String diffSmsAuthCheck(String smsNo);

    @Select("SELECT AUTH_CODE FROM SMS_AUTH WHERE SMS_NO = #{smsNo} ORDER BY UPDATE_DT DESC LIMIT 1 ")
    @Results({
            @Result(property = "authCode" , column = "AUTH_CODE")
    })
    String diffSmsAuthCode(String smsNo);

}
