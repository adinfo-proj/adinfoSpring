package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdUtility {
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
}
