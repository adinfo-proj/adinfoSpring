package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.TB_AD_EXTERNAL_URL;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdExternalUrlMapper {
    @Insert("INSERT INTO AD_EXTERNAL_URL " +
            "( " +
            "       SEQ_NO " +
            "     , CREATE_DT " +
            "     , UPDATE_DT " +
            "     , MB_ID " +
            "     , AD_ID " +
            "     , CA_ID " +
            "     , PG_ID " +
            "     , EXTERNAL_URL " +
            "     , STATUS " +
            "     , DESCRIPTION " +
            ") " +
            "VALUES( " +
            "       0 " +
            "     , NOW() " +
            "     , NOW() " +
            "     , #{tbAdExternalUrl.mbId} " +
            "     , #{tbAdExternalUrl.adId} " +
            "     , #{tbAdExternalUrl.caId} " +
            "     , #{tbAdExternalUrl.pgId} " +
            "     , #{tbAdExternalUrl.externalUrl} " +
            "     , #{tbAdExternalUrl.status} " +
            "     , #{tbAdExternalUrl.description} " +
            ") ")
    Long insAdExternalUrl(@Param("tbAdExternalUrl") TB_AD_EXTERNAL_URL tbAdExternalUrl);

    @Insert("UPDATE " +
            "       AD_EXTERNAL_URL " +
            "SET " +
            "       STATUS = #{status} " +
            "WHERE " +
            "       MB_ID = #{mbId} " +
            "AND    AD_ID = #{mbId} " +
            "AND    CA_ID = #{caId} " +
            "AND    PG_ID = #{pgId} " )
    Long updAdExternalUrl(Long mbId, Long adId, Long caId, Long pgId, String status);

    @Select("SELECT " +
            "       COUNT(*) AS ROW_TOTAL_COUNT " +
            " FROM " +
            "       AD_EXTERNAL_URL " +
            " WHERE " +
            "       MB_ID  = ${mbId} " +
            " AND   AD_ID  = ${adId} " +
            " AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            " AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            " AND   STATUS = ${status} " +
            " AND   STATUS <> '99' " +
            " ORDER BY SEQ_NO DESC "
    )
    @Results({
            @Result(property = "rowTotalCount", column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> selAdExternalUrlRowCount(Long mbId, Long adId, Long caId, Long pgId, String status);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , CREATE_DT " +
            "       , UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , (SELECT NAME FROM CAMPAIGN_MASTER WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) AS CA_NM " +
            "       , PG_ID " +
            "       , (SELECT NAME FROM LANDING_PAGE    WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) AS PG_NM " +
            "       , EXTERNAL_URL " +
            "       , STATUS " +
            "       , DESCRIPTION " +
            " FROM " +
            "       AD_EXTERNAL_URL A" +
            " WHERE " +
            "       MB_ID  = #{mbId}" +
            " AND   AD_ID  = #{adId}" +
            " AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            " AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            " AND   STATUS = #{status}" +
            " AND   STATUS <> '99' " +
            " ORDER BY SEQ_NO DESC "
    )
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "mbId", column = "MB_ID"),
            @Result(property = "adId", column = "AD_ID"),
            @Result(property = "caId", column = "CA_ID"),
            @Result(property = "caNm", column = "CA_NM"),
            @Result(property = "pgId", column = "PG_ID"),
            @Result(property = "pgNm", column = "PG_NM"),
            @Result(property = "externalUrl", column = "EXTERNAL_URL"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "description", column = "DESCRIPTION")
    })
    List<Map<String, Object>> selAdExternalUrl(Long mbId, Long adId, Long caId, Long pgId, String status);
}
