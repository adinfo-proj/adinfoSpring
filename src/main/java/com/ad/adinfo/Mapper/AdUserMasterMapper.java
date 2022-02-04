package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdUserMasterMapper {
    @Select("SELECT " +
            "         UPDATE_DT" +
            "       , MB_ID" +
            "       , AD_ID" +
            "       , MK_ID" +
            "       , MK_CD" +
            "       , CLNT_ID" +
            "       , CLNT_NM" +
            "       , NICK_NM" +
            "       , CLNT_PW" +
            "       , CLNT_SUBS_NO" +
            "       , COMPANY_NM" +
            "       , COMPANY_NO" +
            "       , COMPANY_UPJONG" +
            "       , COMPANY_KIND" +
            "       , ADDRESS" +
            "       , BANK_CD" +
            "       , ACNT_NM" +
            "       , ACNT_NO" +
            "       , ACTV_CD" +
            "       , GRADE_CD" +
            "       , REG_DT" +
            "       , ABN_DT" +
            "       , SRT_DT" +
            "       , EXP_DT" +
            "       , SPONSER_ID" +
            "       , SPONSER_RATE" +
            "       , COMMENT" +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       CLNT_ID = #{clntId}")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "mkCd" , column = "MK_CD"),
            @Result(property = "clntId" , column = "CLNT_ID"),
            @Result(property = "clntNm" , column = "CLNT_NM"),
            @Result(property = "nickNm" , column = "NICK_NM"),
            @Result(property = "clntPw" , column = "CLNT_PW"),
            @Result(property = "clntSubsNo" , column = "CLNT_SUBS_NO"),
            @Result(property = "companyNm" , column = "COMPANY_NM"),
            @Result(property = "companyNo" , column = "COMPANY_NO"),
            @Result(property = "companyUpjong" , column = "COMPANY_UPJONG"),
            @Result(property = "companyKind" , column = "COMPANY_KIND"),
            @Result(property = "address" , column = "ADDRESS"),
            @Result(property = "bankCd" , column = "BANK_CD"),
            @Result(property = "acntNm" , column = "ACNT_NM"),
            @Result(property = "acntNo" , column = "ACNT_NO"),
            @Result(property = "actvCd" , column = "ACTV_CD"),
            @Result(property = "gradeCd" , column = "GRADE_CD"),
            @Result(property = "regDt" , column = "REG_DT"),
            @Result(property = "abnDt" , column = "ABN_DT"),
            @Result(property = "srtDt" , column = "SRT_DT"),
            @Result(property = "expDt" , column = "EXP_DT"),
            @Result(property = "sponserId" , column = "SPONSER_ID"),
            @Result(property = "sponserRate" , column = "SPONSER_RATE"),
            @Result(property = "comment" , column = "COMMENT")
    })
    AD_USER_MASTER getAdUserMaster(String adClntId);

    @Insert("INSERT INTO AD_USER_MASTER " +
            "( " +
            "         UPDATE_DT" +
            "       , MB_ID" +
            "       , AD_ID" +
            "       , MK_ID" +
            "       , MK_CD" +
            "       , CLNT_ID" +
            "       , CLNT_NM" +
            "       , NICK_NM" +
            "       , CLNT_PW" +
            "       , CLNT_SUBS_NO" +
            "       , COMPANY_NM" +
            "       , COMPANY_NO" +
            "       , COMPANY_UPJONG" +
            "       , COMPANY_KIND" +
            "       , ADDRESS" +
            "       , BANK_CD" +
            "       , ACNT_NM" +
            "       , ACNT_NO" +
            "       , ACTV_CD" +
            "       , GRADE_CD" +
            "       , REG_DT" +
            "       , ABN_DT" +
            "       , SRT_DT" +
            "       , EXP_DT" +
            "       , SPONSER_ID" +
            "       , SPONSER_RATE" +
            "       , COMMENT" +
            ") " +
            "VALUES(" +
            "         NOW()" +
            "       , #{adUserMaster.mbId}" +
            "       , #{adUserMaster.adId}" +
            "       , #{adUserMaster.mkId}" +
            "       , #{adUserMaster.mkCd}" +
            "       , #{adUserMaster.clntId}" +
            "       , #{adUserMaster.clntNm}" +
            "       , #{adUserMaster.nickNm}" +
            "       , HEX(AES_ENCRYPT(#{adUserMaster.clntPw}, 'dbfactory'))" +
            "       , #{adUserMaster.clntSubsNo}" +
            "       , #{adUserMaster.companyNm}" +
            "       , #{adUserMaster.companyNo}" +
            "       , #{adUserMaster.companyUpjong}" +
            "       , #{adUserMaster.companyKind}" +
            "       , #{adUserMaster.address}" +
            "       , #{adUserMaster.bankCd}" +
            "       , #{adUserMaster.acntNm}" +
            "       , #{adUserMaster.acntNo}" +
            "       , #{adUserMaster.actvCd}" +
            "       , #{adUserMaster.gradeCd}" +
            "       , #{adUserMaster.regDt}" +
            "       , #{adUserMaster.abnDt}" +
            "       , #{adUserMaster.srtDt}" +
            "       , #{adUserMaster.expDt}" +
            "       , #{adUserMaster.sponserId}" +
            "       , #{adUserMaster.sponserRate}" +
            "       , #{adUserMaster.comment}" +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "mbId")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "mkCd" , column = "MK_CD"),
            @Result(property = "clntId" , column = "CLNT_ID"),
            @Result(property = "clntNm" , column = "CLNT_NM"),
            @Result(property = "nickNm" , column = "NICK_NM"),
            @Result(property = "clntPw" , column = "CLNT_PW"),
            @Result(property = "clntSubsNo" , column = "CLNT_SUBS_NO"),
            @Result(property = "companyNm" , column = "COMPANY_NM"),
            @Result(property = "companyNo" , column = "COMPANY_NO"),
            @Result(property = "companyUpjong" , column = "COMPANY_UPJONG"),
            @Result(property = "companyKind" , column = "COMPANY_KIND"),
            @Result(property = "address" , column = "ADDRESS"),
            @Result(property = "bankCd" , column = "BANK_CD"),
            @Result(property = "acntNm" , column = "ACNT_NM"),
            @Result(property = "acntNo" , column = "ACNT_NO"),
            @Result(property = "actvCd" , column = "ACTV_CD"),
            @Result(property = "gradeCd" , column = "GRADE_CD"),
            @Result(property = "regDt" , column = "REG_DT"),
            @Result(property = "abnDt" , column = "ABN_DT"),
            @Result(property = "srtDt" , column = "SRT_DT"),
            @Result(property = "expDt" , column = "EXP_DT"),
            @Result(property = "sponserId" , column = "SPONSER_ID"),
            @Result(property = "sponserRate" , column = "SPONSER_RATE"),
            @Result(property = "comment" , column = "COMMENT")
    })
    Long insAdUserMaster(@Param("adUserMaster") AD_USER_MASTER adUserMaster);

    @Select("SELECT " +
            "       GRADE_CD " +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       CLNT_ID = #{clntId} ")
    @Results({
            @Result(property = "gradeCd", column = "GRADE_CD")
    })
    String getAdUserMasterForId(String clntId);

    @Select("SELECT " +
            "       GRADE_CD " +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       CLNT_ID = #{clntId} " +
            " AND   AES_DECRYPT(UNHEX(CLNT_PW), 'dbfactory') = #{clntPw}")
    @Results({
            @Result(property = "gradeCd", column = "GRADE_CD")
    })
    String getAdUserMasterForIdPw(String clntId, String clntPw);

    @Select("SELECT " +
            "       IFNULL(MAX(MB_ID), 10000) AS MB_ID" +
            " FROM " +
            "       AD_USER_MASTER" )
    @Results({
            @Result(property = "mbId" , column = "MB_ID")
    })
    Long getAdUserMasterMaxMbId();

    @Select("SELECT " +
            "       IFNULL(MAX(AD_ID), 10000) AS AD_ID" +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       MB_ID      = #{mbId}" )
    @Results({
            @Result(property = "adId" , column = "AD_ID")
    })
    Long getAdUserMasterMaxAdId(Long mbId);

    @Select("SELECT " +
            "       IFNULL(MAX(MK_ID), 10000) AS MK_ID" +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       MB_ID      = #{mbId}" )
    @Results({
            @Result(property = "mkId" , column = "MK_ID")
    })
    Long getAdUserMasterMaxPtId(Long mbId);

    @Select("SELECT " +
            "       CLNT_ID " +
            " FROM " +
            "       AD_USER_MASTER" +
            " WHERE " +
            "       CLNT_NM      = #{userName} " +
            " AND   CLNT_SUBS_NO = #{clntSubsNo} ")
    @Results({
            @Result(property = "clntId" , column = "CLNT_ID")
    })
    String getAdUserMasterFindId(String userName, String clntSubsNo);

    @Update(" UPDATE AD_USER_MASTER " +
            " SET    CLNT_PW = HEX(AES_ENCRYPT(#{nanPw}, 'dbfactory'))" +
            " WHERE " +
            "       CLNT_ID      = #{userId} " )
    Long setAdUserMasterFindPw(String nanPw, String userId);
}
