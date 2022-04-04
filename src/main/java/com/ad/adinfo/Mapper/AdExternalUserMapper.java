package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.TB_AD_EXTERNAL_USER;
import com.ad.adinfo.Domain.TB_CAMPAIGN_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdExternalUserMapper {
    @Insert("INSERT INTO AD_EXTERNAL_USER " +
            "( " +
            "       SEQ_NO " +
            "     , CREATE_DT " +
            "     , UPDATE_DT " +
            "     , MB_ID " +
            "     , AD_ID " +
            "     , CA_ID " +
            "     , PG_ID " +
            "     , CLNT_ID " +
            "     , EXTERNAL_CLNT_ID " +
            "     , EXTERNAL_CLNT_PW " +
            "     , STATUS " +
            "     , SRT_DT " +
            "     , END_DT " +
            "     , DESCRIPTION " +
            "     , LOGO_FILE_NM " +
            ") " +
            "VALUES( " +
            "       0 " +
            "     , NOW() " +
            "     , NOW() " +
            "     , #{tbAdExternalUser.mbId} " +
            "     , #{tbAdExternalUser.adId} " +
            "     , #{tbAdExternalUser.caId} " +
            "     , #{tbAdExternalUser.pgId} " +
            "     , #{tbAdExternalUser.clntId} " +
            "     , #{tbAdExternalUser.externalClntId} " +
            "     , HEX(AES_ENCRYPT(#{tbAdExternalUser.externalClntPw}, 'dbfactory')) " +
            "     , #{tbAdExternalUser.status} " +
            "     , #{tbAdExternalUser.srtDt} " +
            "     , #{tbAdExternalUser.endDt} " +
            "     , #{tbAdExternalUser.description} " +
            "     , #{tbAdExternalUser.logoFileNm} " +
            ") ")
    Long insAdExternalUser(@Param("tbAdExternalUser") TB_AD_EXTERNAL_USER tbAdExternalUser);

    @Update(" UPDATE " +
            "        AD_EXTERNAL_USER " +
            " SET" +
            "        SEQ_NO            = #{tbAdPostbackFormat.seqNo} " +
            "      , CREATE_DT         = #{tbAdPostbackFormat.createDt} " +
            "      , UPDATE_DT         = NOW() " +
            "      , EXTERNAL_CLNT_ID  = #{tbAdPostbackFormat.externalClntId} " +
            "      , EXTERNAL_CLNT_PW  = HEX(AES_ENCRYPT(#{tbAdPostbackFormat.externalClntPw}, 'dbfactory')) " +
            "      , MB_ID             = #{tbAdPostbackFormat.mbId} " +
            "      , AD_ID             = #{tbAdPostbackFormat.adId} " +
            "      , CA_ID             = #{tbAdPostbackFormat.caId} " +
            "      , PG_ID             = #{tbAdPostbackFormat.pgId} " +
            "      , STATUS            = #{tbAdPostbackFormat.status} " +
            "      , SRT_DT            = #{tbAdPostbackFormat.srtDt} " +
            "      , END_DT            = #{tbAdPostbackFormat.endDt} " +
            "      , DESCRIPTION       = #{tbAdPostbackFormat.description} " +
            " WHERE " +
            "       SEQ_NO = #{tbAdPostbackFormat.seqNo}")
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long updAdExternalUser(@Param("tbAdExternalUser") TB_AD_EXTERNAL_USER tbAdExternalUser);

    @Select("SELECT " +
            "       COUNT(*) AS ROW_TOTAL_COUNT " +
            " FROM " +
            "       AD_EXTERNAL_USER " +
            " WHERE " +
            "       MB_ID  = ${mbId} " +
            " AND   AD_ID  = ${adId} " +
            " AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            " AND   STATUS = ${status} " +
            " AND   STATUS <> '99' " +
            " ORDER BY SEQ_NO DESC "
    )
    @Results({
            @Result(property = "rowTotalCount", column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> selAdExternalUserRowCount(Long mbId, Long adId, Long caId, String status);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , CREATE_DT " +
            "       , UPDATE_DT " +
            "       , CLNT_ID " +
            "       , EXTERNAL_CLNT_ID " +
            "       , AES_DECRYPT(UNHEX(EXTERNAL_CLNT_PW), 'dbfactory') = #{clntPw} " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , PG_ID " +
            "       , STATUS " +
            "       , SRT_DT " +
            "       , END_DT " +
            "       , DESCRIPTION " +
            " FROM " +
            "       AD_EXTERNAL_USER " +
            " WHERE " +
            "       SEQ_NO = ${seqNo} "
    )
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "externalClntId", column = "EXTERNAL_CLNT_ID"),
            @Result(property = "externalClntPw", column = "EXTERLAL_CLNT_PW"),
            @Result(property = "mbId", column = "MB_ID"),
            @Result(property = "adId", column = "AD_ID"),
            @Result(property = "caId", column = "CA_ID"),
            @Result(property = "pgId", column = "PG_ID"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "srtDt", column = "SRT_DT"),
            @Result(property = "endDt", column = "END_DT"),
            @Result(property = "description", column = "DESCRIPTION")
    })
    TB_AD_EXTERNAL_USER selAdExternalUserOne(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , CREATE_DT " +
            "       , UPDATE_DT " +
            "       , CLNT_ID " +
            "       , EXTERNAL_CLNT_ID " +
//            "       , AES_DECRYPT(UNHEX(EXTERNAL_CLNT_PW), 'dbfactory') = #{clntPw} " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , PG_ID " +
            "       , STATUS " +
            "       , SRT_DT " +
            "       , END_DT " +
            "       , DESCRIPTION " +
            "       , LOGO_FILE_NM " +
            " FROM " +
            "       AD_EXTERNAL_USER " +
            " WHERE " +
            "       EXTERNAL_CLNT_ID = #{clntId} "
    )
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "externalClntId", column = "EXTERNAL_CLNT_ID"),
            @Result(property = "externalClntPw", column = "EXTERNAL_CLNT_PW"),
            @Result(property = "mbId", column = "MB_ID"),
            @Result(property = "adId", column = "AD_ID"),
            @Result(property = "caId", column = "CA_ID"),
            @Result(property = "pgId", column = "PG_ID"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "srtDt", column = "SRT_DT"),
            @Result(property = "endDt", column = "END_DT"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "logoFileNm", column = "LOGO_FILE_NM")
    })
    TB_AD_EXTERNAL_USER selAdExternalUserGet(String clntId);

    @Select("SELECT " +
            "        A.SEQ_NO " +
            "      , A.CREATE_DT " +
            "      , A.UPDATE_DT " +
            "      , A.MB_ID " +
            "      , A.AD_ID " +
            "      , A.CA_ID " +
            "      , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) AS CA_NM " +
            "      , A.PG_ID " +
            "      , ' ' AS PG_NM " +
            "      , A.CLNT_ID " +
            "      , A.EXTERNAL_CLNT_ID " +
            "      , CONVERT(AES_DECRYPT(UNHEX(EXTERNAL_CLNT_PW), 'dbfactory') USING UTF8) EXTERNAL_CLNT_PW " +
            "      , A.STATUS " +
            "      , A.SRT_DT " +
            "      , A.END_DT " +
            "      , A.DESCRIPTION " +
            "FROM " +
            "         AD_EXTERNAL_USER A " +
            "WHERE " +
            "      A.MB_ID  = #{mbId} " +
            "AND   A.AD_ID  = #{adId} " +
            "AND   ((-1 = ${caId}) OR (A.CA_ID = ${caId})) " +
            "AND   A.STATUS = #{status} " +
            "AND   A.STATUS <> '99' " +
            "ORDER BY A.SEQ_NO DESC " +
            " LIMIT #{srtPos}, #{rowCount}" )

    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "mbId", column = "MB_ID"),
            @Result(property = "adId", column = "AD_ID"),
            @Result(property = "caId", column = "CA_ID"),
            @Result(property = "caNm", column = "CA_NM"),
            @Result(property = "pgNm", column = "PG_NM"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "externalClntId", column = "EXTERNAL_CLNT_ID"),
            @Result(property = "externalClntPw", column = "EXTERNAL_CLNT_PW"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "srtDt", column = "SRT_DT"),
            @Result(property = "endDt", column = "END_DT"),
            @Result(property = "description", column = "DESCRIPTION")
    })
    List<Map<String, Object>> selAdExternalUser(Long mbId, Long adId, Long caId, String status, Long srtPos, Long rowCount);

    @Select("SELECT " +
            "       SUBSTR(MAX(EXTERNAL_CLNT_ID), 3, 6) AS EXTERNAL_CLNT_ID " +
            " FROM " +
            "       AD_EXTERNAL_USER " )
    @Results({
            @Result(property = "externalClntId" , column = "EXTERNAL_CLNT_ID")
    })
    String getAdExternalUserNewClntId();

    @Select("SELECT " +
            "       STATUS " +
            " FROM " +
            "       AD_EXTERNAL_USER " +
            " WHERE " +
            "       EXTERNAL_CLNT_ID = #{clntId} ")
    @Results({
            @Result(property = "status", column = "STATUS")
    })
    String getExternalUserMasterForId(String clntId);

    @Select("SELECT " +
            "       STATUS " +
            " FROM " +
            "       AD_EXTERNAL_USER" +
            " WHERE " +
            "       EXTERNAL_CLNT_ID = #{clntId} " +
            " AND   AES_DECRYPT(UNHEX(EXTERNAL_CLNT_PW), 'dbfactory') = #{clntPw}")
    @Results({
            @Result(property = "status", column = "STATUS")
    })
    String getExternalUserMasterForIdPw(String clntId, String clntPw);













}
