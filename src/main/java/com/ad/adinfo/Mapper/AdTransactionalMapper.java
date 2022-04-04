package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.TB_AD_TRANSACTIONAL;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdTransactionalMapper {

    @Insert("INSERT INTO AD_TRANSACTIONAL " +
            "(" +
            "       SEQ_NO " +
            "     , UPDATE_DT " +
            "     , CRE_DT " +
            "     , CRE_TM " +
            "     , MB_ID " +
            "     , AD_ID " +
            "     , CLNT_ID " +
            "     , GRADE_CD " +
            "     , SUMMARY_CD " +
            "     , AMOUNT " +
            "     , AMOUNT_VAT " +
            "     , INCOME_TAX " +
            "     , INCOME_CD " +
            "     , INCOME_ACNT_NO " +
            "     , INCOME_ACNT_NM " +
            "     , OUTCOME_CD " +
            "     , OUTCOME_ACNT_NO " +
            "     , OUTCOME_ACNT_NM " +
            "     , COMMENT " +
            ") " +
            "VALUES(" +
            "       0 " +
            "     , NOW() " +
            "     , #{tbAdTransactional.creDt} " +
            "     , #{tbAdTransactional.creTm} " +
            "     , #{tbAdTransactional.mbId} " +
            "     , #{tbAdTransactional.adId} " +
            "     , #{tbAdTransactional.clntId} " +
            "     , #{tbAdTransactional.gradeCd} " +
            "     , #{tbAdTransactional.summaryCd} " +
            "     , #{tbAdTransactional.amount} " +
            "     , #{tbAdTransactional.amountVat} " +
            "     , #{tbAdTransactional.incomeTax} " +
            "     , #{tbAdTransactional.incomeCd} " +
            "     , #{tbAdTransactional.incomeAcntNo} " +
            "     , #{tbAdTransactional.incomeAcntNm} " +
            "     , #{tbAdTransactional.outcomeCd} " +
            "     , #{tbAdTransactional.outcomeAcntNo} " +
            "     , #{tbAdTransactional.outcomeAcntNm} " +
            "     , #{tbAdTransactional.comment} " +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long InsAdTransactional(@Param("tbAdTransactional") TB_AD_TRANSACTIONAL tbAdTransactional);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , UPDATE_DT " +
            "       , CRE_DT " +
            "       , CRE_TM " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CLNT_ID " +
            "       , GRADE_CD " +
            "       , SUMMARY_CD " +
            "       , AMOUNT " +
            "       , AMOUNT_VAT " +
            "       , INCOME_TAX " +
            "       , INCOME_CD " +
            "       , INCOME_ACNT_NO " +
            "       , INCOME_ACNT_NM " +
            "       , OUTCOME_CD " +
            "       , OUTCOME_ACNT_NO " +
            "       , OUTCOME_ACNT_NM " +
            "       , COMMENT " +
            " FROM " +
            "       AD_TRANSACTIONAL A" +
            " WHERE " +
            "       CLNT_ID      = #{clntId} " +
            " ORDER BY SEQ_NO DESC " +
            " LIMIT 1 "
    )
    @Results({
            @Result(property = "updateDt"      , column = "UPDATE_DT"),
            @Result(property = "creDt"         , column = "CRE_DT"),
            @Result(property = "creTm"         , column = "CRE_TM"),
            @Result(property = "mbId"          , column = "MB_ID"),
            @Result(property = "adId"          , column = "AD_ID"),
            @Result(property = "clntId"        , column = "CLNT_ID"),
            @Result(property = "gradeCd"       , column = "GRADE_CD"),
            @Result(property = "summaryCd"     , column = "SUMMARY_CD"),
            @Result(property = "amount"        , column = "AMOUNT"),
            @Result(property = "amountVat"     , column = "AMOUNT_VAT"),
            @Result(property = "incomeTax"     , column = "INCOME_TAX"),
            @Result(property = "incomeCd"      , column = "INCOME_CD"),
            @Result(property = "incomeAcntNo"  , column = "INCOME_ACNT_NO"),
            @Result(property = "incomeAcntNm"  , column = "INCOME_ACNT_NM"),
            @Result(property = "outcomeCd"     , column = "OUTCOME_CD"),
            @Result(property = "outcomeAcntNo" , column = "OUTCOME_ACNT_NO"),
            @Result(property = "outcomeAcntNm" , column = "OUTCOME_ACNT_NM"),
            @Result(property = "comment"       , column = "COMMENT")
    })
    Map<String, Object> GetAdTransactionalLastOne(String clntId);
}
