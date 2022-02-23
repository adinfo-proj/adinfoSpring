package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.TB_AD_POSTBACK_FORMAT;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdPostbackFormatMapper {
    @Insert("INSERT INTO AD_POSTBACK_FORMAT " +
            "( " +
            "       UPDATE_DT " +
            "     , CLNT_ID " +
            "     , MB_ID " +
            "     , AD_ID " +
            "     , CA_ID " +
            "     , PG_ID " +
            "     , PB_ID " +
            "     , STATUS " +
            "     , POSTBACK_IO " +
            "     , POSTBACK_URL " +
            "     , SEND_TYPE " +
            "     , SSL_YN " +
            "     , USE_TP_ARR " +
            "     , ACCESS_FLAG " +
            "     , HTTP_USER_AGENT " +
            "     , REMOTE_ADDR " +
            "     , HTTP_REFERER " +
            "     , HTTP_HOST " +
            "     , REQUEST_URI " +
            "     , VALUE01 " +
            "     , VALUE02 " +
            "     , VALUE03 " +
            "     , VALUE04 " +
            "     , VALUE05 " +
            "     , VALUE06 " +
            "     , VALUE07 " +
            "     , VALUE08 " +
            "     , VALUE09 " +
            "     , VALUE10 " +
            ") " +
            "VALUES( " +
            "       NOW() " +
            "     , #{tbAdPostbackFormat.clntId} " +
            "     , #{tbAdPostbackFormat.mbId} " +
            "     , #{tbAdPostbackFormat.adId} " +
            "     , #{tbAdPostbackFormat.caId} " +
            "     , #{tbAdPostbackFormat.pgId} " +
            "     , #{tbAdPostbackFormat.pbId} " +
            "     , #{tbAdPostbackFormat.status} " +
            "     , #{tbAdPostbackFormat.postbackIo} " +
            "     , #{tbAdPostbackFormat.postbackUrl} " +
            "     , #{tbAdPostbackFormat.sendType} " +
            "     , #{tbAdPostbackFormat.sslYn} " +
            "     , #{tbAdPostbackFormat.useTpArr} " +
            "     , #{tbAdPostbackFormat.accessFlag} " +
            "     , #{tbAdPostbackFormat.httpUserAgent} " +
            "     , #{tbAdPostbackFormat.remoteAddr} " +
            "     , #{tbAdPostbackFormat.httpReferer} " +
            "     , #{tbAdPostbackFormat.httpHost} " +
            "     , #{tbAdPostbackFormat.requestUri} " +
            "     , #{tbAdPostbackFormat.value01} " +
            "     , #{tbAdPostbackFormat.value02} " +
            "     , #{tbAdPostbackFormat.value03} " +
            "     , #{tbAdPostbackFormat.value04} " +
            "     , #{tbAdPostbackFormat.value05} " +
            "     , #{tbAdPostbackFormat.value06} " +
            "     , #{tbAdPostbackFormat.value07} " +
            "     , #{tbAdPostbackFormat.value08} " +
            "     , #{tbAdPostbackFormat.value09} " +
            "     , #{tbAdPostbackFormat.value10} " +
            ") ")
    Long insPostback(@Param("tbAdPostbackFormat") TB_AD_POSTBACK_FORMAT tbAdPostbackFormat);

    @Update("UPDATE " +
            "       AD_POSTBACK_FORMAT " +
            "SET " +
            "       UPDATE_DT    = NOW() " +
            "     , CLNT_ID      = #{tbAdPostbackFormat.clntId} " +
            "     , MB_ID        = #{tbAdPostbackFormat.mbId} " +
            "     , AD_ID        = #{tbAdPostbackFormat.adId} " +
            "     , CA_ID        = #{tbAdPostbackFormat.caId} " +
            "     , PG_ID        = #{tbAdPostbackFormat.pgId} " +
            "     , PB_ID        = #{tbAdPostbackFormat.pbId} " +
            "     , STATUS       = #{tbAdPostbackFormat.status} " +
            "     , POSTBACK_IO  = #{tbAdPostbackFormat.postbackIo} " +
            "     , POSTBACK_URL = #{tbAdPostbackFormat.postbackUrl} " +
            "     , SEND_TYPE    = #{tbAdPostbackFormat.sendType} " +
            "     , SSL_YN       = #{tbAdPostbackFormat.sslYn} " +
            "     , USE_TP_ARR   = #{tbAdPostbackFormat.useTpArr} " +
            "     , ACCESS_FLAG  = #{tbAdPostbackFormat.accessFlag} " +
            "     , VALUE01      = #{tbAdPostbackFormat.value01} " +
            "     , VALUE02      = #{tbAdPostbackFormat.value02} " +
            "     , VALUE03      = #{tbAdPostbackFormat.value03} " +
            "     , VALUE04      = #{tbAdPostbackFormat.value04} " +
            "     , VALUE05      = #{tbAdPostbackFormat.value05} " +
            "     , VALUE06      = #{tbAdPostbackFormat.value06} " +
            "     , VALUE07      = #{tbAdPostbackFormat.value07} " +
            "     , VALUE08      = #{tbAdPostbackFormat.value08} " +
            "     , VALUE09      = #{tbAdPostbackFormat.value09} " +
            "     , VALUE10      = #{tbAdPostbackFormat.value10} " +
            "WHERE " +
            "       MB_ID        = #{tbAdPostbackFormat.mbId} " +
            "AND    AD_ID        = #{tbAdPostbackFormat.adId} " +
            "AND    CA_ID        = #{tbAdPostbackFormat.caId} " +
            "AND    PG_ID        = #{tbAdPostbackFormat.pgId} " +
            "AND    PB_ID        = #{tbAdPostbackFormat.pbId} " )
    Long updPostback(@Param("tbAdPostbackFormat") TB_AD_POSTBACK_FORMAT tbAdPostbackFormat);

    @Select("SELECT " +
            "        UPDATE_DT " +
            "      , CLNT_ID " +
            "      , MB_ID " +
            "      , AD_ID " +
            "      , CA_ID " +
            "      , PG_ID " +
            "      , STATUS " +
            "      , POSTBACK_IO " +
            "      , POSTBACK_URL " +
            "      , SEND_TYPE " +
            "      , SSL_YN " +
            "      , USE_TP_ARR " +
            "      , ACCESS_FLAG " +
            "      , VALUE01 " +
            "      , VALUE02 " +
            "      , VALUE03 " +
            "      , VALUE04 " +
            "      , VALUE05 " +
            "      , VALUE06 " +
            "      , VALUE07 " +
            "      , VALUE08 " +
            "      , VALUE09 " +
            "      , VALUE10 " +
            "FROM " +
            "        AD_POSTBACK_FORMAT " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   CA_ID      = ${caId} " +
            "AND   STATUS    <> 'ZZ'    ")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "clntId" , column = "CLNT_ID"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "postbackIo" , column = "POSTBACK_IO"),
            @Result(property = "postbackUrl" , column = "POSTBACK_URL"),
            @Result(property = "sendType" , column = "SEND_TYPE"),
            @Result(property = "sslYn" , column = "SSL_YN"),
            @Result(property = "useTpArr" , column = "USE_TP_ARR"),
            @Result(property = "accessFlag" , column = "ACCESS_FLAG"),
            @Result(property = "value01" , column = "VALUE01"),
            @Result(property = "value02" , column = "VALUE02"),
            @Result(property = "value03" , column = "VALUE03"),
            @Result(property = "value04" , column = "VALUE04"),
            @Result(property = "value05" , column = "VALUE05"),
            @Result(property = "value06" , column = "VALUE06"),
            @Result(property = "value07" , column = "VALUE07"),
            @Result(property = "value08" , column = "VALUE08"),
            @Result(property = "value09" , column = "VALUE09"),
            @Result(property = "value10" , column = "VALUE10")
    })
    Map<String, Object> selPostbackFormat(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "       MAX(PB_ID) AS PB_ID " +
            " FROM " +
            "       AD_POSTBACK_FORMAT " +
            " WHERE " +
            "       MB_ID      = #{mbId} " +
            " AND   AD_ID      = #{adId} " +
            " AND   CA_ID      = #{caId} " +
            " AND   Pg_ID      = #{pgId} " )
    @Results({
            @Result(property = "pb" , column = "PB_ID")
    })
    Long getPostbackMaxpbId(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        UPDATE_DT " +
            "      , CLNT_ID " +
            "      , MB_ID " +
            "      , AD_ID " +
            "      , CA_ID " +
            "      , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) CA_NAME" +
            "      , PG_ID " +
            "      , (SELECT NAME FROM LANDING_PAGE    WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) PG_NAME " +
            "      , PB_ID " +
            "      , STATUS " +
            "      , POSTBACK_IO " +
            "      , POSTBACK_URL " +
            "      , SEND_TYPE " +
            "      , SSL_YN " +
            "      , USE_TP_ARR " +
            "      , ACCESS_FLAG " +
            "      , VALUE01 " +
            "      , VALUE02 " +
            "      , VALUE03 " +
            "      , VALUE04 " +
            "      , VALUE05 " +
            "      , VALUE06 " +
            "      , VALUE07 " +
            "      , VALUE08 " +
            "      , VALUE09 " +
            "      , VALUE10 " +
            "FROM " +
            "        AD_POSTBACK_FORMAT A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            "AND   STATUS    <> 'ZZ'                     ")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "clntId" , column = "CLNT_ID"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "pgName" , column = "PG_NAME"),
            @Result(property = "pbId" , column = "PB_ID"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "postbackIo" , column = "POSTBACK_IO"),
            @Result(property = "postbackUrl" , column = "POSTBACK_URL"),
            @Result(property = "sendType" , column = "SEND_TYPE"),
            @Result(property = "sslYn" , column = "SSL_YN"),
            @Result(property = "useTpArr" , column = "USE_TP_ARR"),
            @Result(property = "accessFlag" , column = "ACCESS_FLAG"),
            @Result(property = "value01" , column = "VALUE01"),
            @Result(property = "value02" , column = "VALUE02"),
            @Result(property = "value03" , column = "VALUE03"),
            @Result(property = "value04" , column = "VALUE04"),
            @Result(property = "value05" , column = "VALUE05"),
            @Result(property = "value06" , column = "VALUE06"),
            @Result(property = "value07" , column = "VALUE07"),
            @Result(property = "value08" , column = "VALUE08"),
            @Result(property = "value09" , column = "VALUE09"),
            @Result(property = "value10" , column = "VALUE10")
    })
    List<Map<String, Object>> selPostbackList(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        UPDATE_DT " +
            "      , CLNT_ID " +
            "      , MB_ID " +
            "      , AD_ID " +
            "      , CA_ID " +
            "      , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) CA_NAME" +
            "      , PG_ID " +
            "      , (SELECT NAME FROM LANDING_PAGE    WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) PG_NAME " +
            "      , PB_ID " +
            "      , STATUS " +
            "      , POSTBACK_IO " +
            "      , POSTBACK_URL " +
            "      , SEND_TYPE " +
            "      , SSL_YN " +
            "      , USE_TP_ARR " +
            "      , ACCESS_FLAG " +
            "      , VALUE01 " +
            "      , VALUE02 " +
            "      , VALUE03 " +
            "      , VALUE04 " +
            "      , VALUE05 " +
            "      , VALUE06 " +
            "      , VALUE07 " +
            "      , VALUE08 " +
            "      , VALUE09 " +
            "      , VALUE10 " +
            "FROM " +
            "        AD_POSTBACK_FORMAT A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            "AND   ((-1 = ${pbId}) OR (PB_ID = ${pbId})) " +
            "AND   STATUS    <> 'ZZ'                     ")
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "clntId" , column = "CLNT_ID"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "pgName" , column = "PG_NAME"),
            @Result(property = "pbId" , column = "PB_ID"),
            @Result(property = "status" , column = "STATUS"),
            @Result(property = "postbackIo" , column = "POSTBACK_IO"),
            @Result(property = "postbackUrl" , column = "POSTBACK_URL"),
            @Result(property = "sendType" , column = "SEND_TYPE"),
            @Result(property = "sslYn" , column = "SSL_YN"),
            @Result(property = "useTpArr" , column = "USE_TP_ARR"),
            @Result(property = "accessFlag" , column = "ACCESS_FLAG"),
            @Result(property = "value01" , column = "VALUE01"),
            @Result(property = "value02" , column = "VALUE02"),
            @Result(property = "value03" , column = "VALUE03"),
            @Result(property = "value04" , column = "VALUE04"),
            @Result(property = "value05" , column = "VALUE05"),
            @Result(property = "value06" , column = "VALUE06"),
            @Result(property = "value07" , column = "VALUE07"),
            @Result(property = "value08" , column = "VALUE08"),
            @Result(property = "value09" , column = "VALUE09"),
            @Result(property = "value10" , column = "VALUE10")
    })
    Map<String, Object> selPostbackOne(Long mbId, Long adId, Long caId, Long pgId, Long pbId);

    @Update(" UPDATE " +
            "        AD_POSTBACK_FORMAT " +
            " SET" +
            "          UPDATE_DT           = NOW()" +
            "        , STATUS              = #{status}" +
            " WHERE " +
            "        MB_ID      = #{mbId}" +
            " AND    AD_ID      = #{adId}" +
            " AND    CA_ID      = #{caId}" +
            " AND    PG_ID      = #{pgId}" +
            " AND    PB_ID      = #{pbId}" )
    Long changeStatusPostbackMaster(Long mbId, Long adId, Long caId, Long pgId, Long pbId, String status);

    @Select("SELECT " +
            "        CREATE_DT " +
            "      , PROCESS_DT " +
            "      , PROCESS_TM " +
            "      , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) CA_NAME" +
            "      , (SELECT NAME FROM LANDING_PAGE    WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) PG_NAME " +
            "      , RESULT_CD " +
            "      , RESULT_COMMENT " +
            "      , SEND_URL " +
            "      , SEND_VALUE " +
            "FROM " +
            "        POSTBACK_SEND A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "ORDER BY CREATE_DT DESC "
    )
    @Results({
            @Result(property = "createDt" , column = "CREATE_DT"),
            @Result(property = "processDt" , column = "PROCESS_DT"),
            @Result(property = "processTm" , column = "PROCESS_TM"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "pgName" , column = "PG_NAME"),
            @Result(property = "resultCd" , column = "RESULT_CD"),
            @Result(property = "resultComment" , column = "RESULT_COMMENT"),
            @Result(property = "sendUrl" , column = "SEND_URL"),
            @Result(property = "sendValue" , column = "SEND_VALUE")
    })
    List<Map<String, Object>> selPostbackResultTp0(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        COUNT(*) AS ROW_TOTAL_COUNT " +
            "FROM " +
            "        POSTBACK_SEND A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> selPostbackResultRowCount0(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        CREATE_DT " +
            "      , PROCESS_DT " +
            "      , PROCESS_TM " +
            "      , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) CA_NAME" +
            "      , (SELECT NAME FROM LANDING_PAGE    WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) PG_NAME " +
            "      , RESULT_CD " +
            "      , RESULT_COMMENT " +
            "      , SEND_URL " +
            "      , SEND_VALUE " +
            "FROM " +
            "        POSTBACK_SEND A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            "AND   RESULT_CD = '0' " +
            "ORDER BY CREATE_DT DESC "
    )
    @Results({
            @Result(property = "createDt" , column = "CREATE_DT"),
            @Result(property = "processDt" , column = "PROCESS_DT"),
            @Result(property = "processTm" , column = "PROCESS_TM"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "pgName" , column = "PG_NAME"),
            @Result(property = "resultCd" , column = "RESULT_CD"),
            @Result(property = "resultComment" , column = "RESULT_COMMENT"),
            @Result(property = "sendUrl" , column = "SEND_URL"),
            @Result(property = "sendValue" , column = "SEND_VALUE")
    })
    List<Map<String, Object>> selPostbackResultTp1(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        COUNT(*) AS ROW_TOTAL_COUNT " +
            "FROM " +
            "        POSTBACK_SEND A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            "AND   RESULT_CD = '0' "
    )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> selPostbackResultRowCount1(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        CREATE_DT " +
            "      , PROCESS_DT " +
            "      , PROCESS_TM " +
            "      , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) CA_NAME" +
            "      , (SELECT NAME FROM LANDING_PAGE    WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) PG_NAME " +
            "      , RESULT_CD " +
            "      , RESULT_COMMENT " +
            "      , SEND_URL " +
            "      , SEND_VALUE " +
            "FROM " +
            "        POSTBACK_SEND A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            "AND   RESULT_CD <> '0' " +
            "ORDER BY CREATE_DT DESC "
    )
    @Results({
            @Result(property = "createDt" , column = "CREATE_DT"),
            @Result(property = "processDt" , column = "PROCESS_DT"),
            @Result(property = "processTm" , column = "PROCESS_TM"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "pgName" , column = "PG_NAME"),
            @Result(property = "resultCd" , column = "RESULT_CD"),
            @Result(property = "resultComment" , column = "RESULT_COMMENT"),
            @Result(property = "sendUrl" , column = "SEND_URL"),
            @Result(property = "sendValue" , column = "SEND_VALUE")
    })
    List<Map<String, Object>> selPostbackResultTp2(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        COUNT(*) AS ROW_TOTAL_COUNT " +
            "FROM " +
            "        POSTBACK_SEND A " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            "AND   RESULT_CD <> '0' "
    )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> selPostbackResultRowCount2(Long mbId, Long adId, Long caId, Long pgId);
}
