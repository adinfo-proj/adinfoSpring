package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdUserMaster {
    @Select("SELECT " +
            "         AD_UPDATE_DT" +
            "       , AD_CLNT_ID" +
            "       , AD_CLNT_NM" +
            "       , AD_NICK_NM" +
            "       , AD_CLNT_SUBS_NO" +
            "       , AD_COMPANY_NM" +
            "       , AD_COMPANY_NO" +
            "       , AD_COMPANY_UPJONG" +
            "       , AD_COMPANY_KIND" +
            "       , AD_ADDRESS" +
            "       , AD_BANK_CD" +
            "       , AD_ACNT_NM" +
            "       , AD_ACNT_NO" +
            "       , AD_ACTV_CD" +
            "       , AD_GRADE_CD" +
            "       , AD_REG_DT" +
            "       , AD_ABN_DT" +
            "       , AD_SRT_DT" +
            "       , AD_EXP_DT" +
            "       , AD_SPONSER_ID" +
            "       , AD_SPONSER_RATE" +
            "       , AD_AD_ID" +
            "       , AD_PT_CD" +
            "       , AD_PT_ID" +
            "       , AD_COMMENT" +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       AD_CLNT_ID = #{adClntId}")
    @Results({
            @Result(property = "adUpdateDt" , column = "AD_UPDATE_DT"),
            @Result(property = "adClntId" , column = "AD_CLNT_ID"),
            @Result(property = "adClntNm" , column = "AD_CLNT_NM"),
            @Result(property = "adNickNm" , column = "AD_NICK_NM"),
            @Result(property = "adClntSubsNo" , column = "AD_CLNT_SUBS_NO"),
            @Result(property = "adCompanyNm" , column = "AD_COMPANY_NM"),
            @Result(property = "adCompanyNo" , column = "AD_COMPANY_NO"),
            @Result(property = "adCompanyUpjong" , column = "AD_COMPANY_UPJONG"),
            @Result(property = "adCompanyKind" , column = "AD_COMPANY_KIND"),
            @Result(property = "adAddress" , column = "AD_ADDRESS"),
            @Result(property = "adBankCd" , column = "AD_BANK_CD"),
            @Result(property = "adAcntNm" , column = "AD_ACNT_NM"),
            @Result(property = "adAcntNo" , column = "AD_ACNT_NO"),
            @Result(property = "adActvCd" , column = "AD_ACTV_CD"),
            @Result(property = "adGradeCd" , column = "AD_GRADE_CD"),
            @Result(property = "adRegDt" , column = "AD_REG_DT"),
            @Result(property = "adAbnDt" , column = "AD_ABN_DT"),
            @Result(property = "adSrtDt" , column = "AD_SRT_DT"),
            @Result(property = "adExpDt" , column = "AD_EXP_DT"),
            @Result(property = "adSponserId" , column = "AD_SPONSER_ID"),
            @Result(property = "adSponserRate" , column = "AD_SPONSER_RATE"),
            @Result(property = "adAdId" , column = "AD_AD_ID"),
            @Result(property = "adPtCd" , column = "AD_PT_ID"),
            @Result(property = "adPtId" , column = "AD_PT_ID"),
            @Result(property = "adComment" , column = "AD_COMMENT")
    })
    List<AD_USER_MASTER> getAdUserMaster(String adClntId);

    @Select("SELECT " +
            "       AD_GRADE_CD " +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       AD_CLNT_ID = #{adClntId} ")
    @Results({
            @Result(property = "adGradeCd", column = "AD_GRADE_CD")
    })
    String getAdUserMasterForId(String adClntId);

    @Select("SELECT " +
            "       AD_GRADE_CD " +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       AD_CLNT_ID = #{adClntId} " +
            " AND   AES_DECRYPT(UNHEX(AD_CLNT_PW), 'dbfactory') = #{adClntPw}")
    @Results({
            @Result(property = "adGradeCd", column = "AD_GRADE_CD")
    })
    String getAdUserMasterForIdPw(String adClntId, String adClntPw);
}
