package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.COMMON_CODE;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonCode {
    @Select("SELECT " +
            "         TP" +
            "       , TP_NM" +
            "       , TP_DESCR" +
            "       , CODE" +
            "       , CODE_NM" +
            "       , CODE_DESCR" +
            "       , CODE_ETC" +
            "       , USE_YN" +
            " FROM " +
            "       COMMON_CODE" +
            " WHERE " +
            "       TP = #{tp}" +
            " AND   USE_YN = 'Y' " +
            " ORDER BY CODE ASC")
    @Results({
            @Result(property = "tp" , column = "TP"),
            @Result(property = "tpNm" , column = "TP_NM"),
            @Result(property = "tpDescr" , column = "TP_DESCR"),
            @Result(property = "code" , column = "CODE"),
            @Result(property = "codeNm" , column = "CODE_NM"),
            @Result(property = "codeDescr" , column = "CODE_DESCR"),
            @Result(property = "codeEtc" , column = "CODE_ETC"),
            @Result(property = "useYn" , column = "USE_YN")
    })
    List<COMMON_CODE> getCommonByTp(String tp);

    @Select("SELECT " +
            "         TP" +
            "       , TP_NM" +
            "       , TP_DESCR" +
            "       , CODE" +
            "       , CODE_NM" +
            "       , CODE_DESCR" +
            "       , CODE_ETC" +
            "       , USE_YN" +
            " FROM " +
            "       COMMON_CODE" +
            " WHERE " +
            "       TP         = #{tp}" +
            " AND   CODE_DESCR = #{codeDescr} " +
            " AND   USE_YN     = 'Y' " +
            " ORDER BY CODE ASC")
    @Results({
            @Result(property = "tp" , column = "TP"),
            @Result(property = "tpNm" , column = "TP_NM"),
            @Result(property = "tpDescr" , column = "TP_DESCR"),
            @Result(property = "code" , column = "CODE"),
            @Result(property = "codeNm" , column = "CODE_NM"),
            @Result(property = "codeDescr" , column = "CODE_DESCR"),
            @Result(property = "codeEtc" , column = "CODE_ETC"),
            @Result(property = "useYn" , column = "USE_YN")
    })
    List<COMMON_CODE> getCommonCodeByDescr(String tp, String codeDescr);
}
