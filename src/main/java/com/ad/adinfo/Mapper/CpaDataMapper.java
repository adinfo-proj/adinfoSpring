package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CPA_DATA;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CpaDataMapper {
    @Select("SELECT " +
            "         SEQ_NO " +
            "       , UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , MK_ID " +
            "       , CA_NAME " +
            "       , PRICE " +
            "       , MK_PRICE " +
            "       , SPEC_PRICE " +
            "       , BONUS_SUPPLY " +
            "       , BONUS_PRICE " +
            "       , SPONSER_ID " +
            "       , SPONSER_PRICE " +
            "       , BEFORE_ADVT_AMT " +
            "       , AFTER_ADVT_AMT " +
            "       , INS_DT " +
            "       , INS_TM " +
            "       , CONFIRM_TP " +
            "       , CONFIRM_DT " +
            "       , CONFIRM_TM " +
            "       , CNCL_MEMO " +
            "       , DUP_REG_IP_YN " +
            "       , REG_IP " +
            "       , URL " +
            "       , URL_AGENT " +
            "       , URL_REFERER " +
            "       , ALL_MOBILE_DUP_YN " +
            "       , THIS_MOBILE_DUP_YN " +
            "       , POSTBACK_YN " +
            "       , POSTBACK_RETRY_CNT " +
            "       , POSTBACK_DTTM " +
            "       , AD_MEMO " +
            "       , MK_MEMO " +
            "       , VALUE_DATA " +
            "       , CASE WHEN LENGTH(VALUE01) = 10 THEN CONCAT(  SUBSTR(VALUE01, 1, 3), '-' " +
            "                                                    , SUBSTR(VALUE01, 4, 3), '-' " +
            "                                                    , SUBSTR(VALUE01, 7, 4) ) " +
            "              WHEN LENGTH(VALUE01) = 11 THEN CONCAT(  SUBSTR(VALUE01, 1, 3), '-' " +
            "                                                    , SUBSTR(VALUE01, 4, 4), '-' " +
            "                                                    , SUBSTR(VALUE01, 8, 4) ) " +
            "              ELSE VALUE01 " +
            "         END AS VALUE01 " +
            "       , VALUE01 " +
            "       , VALUE02 " +
            "       , VALUE03 " +
            "       , VALUE04 " +
            "       , VALUE05 " +
            "       , VALUE06 " +
            "       , VALUE07 " +
            "       , VALUE08 " +
            "       , VALUE09 " +
            "       , VALUE10 " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       SEQ_NO  = #{seqNo}" )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "ptPrice" , column = "PT_PRICE"),
            @Result(property = "specPrice" , column = "SPEC_PRICE"),
            @Result(property = "bonusSupply" , column = "BONUS_SUPPLY"),
            @Result(property = "bonusPrice" , column = "BONUS_PRICE"),
            @Result(property = "sponserId" , column = "SPONSER_ID"),
            @Result(property = "sponserPrice" , column = "SPONSER_PRICE"),
            @Result(property = "beforeAdvtAmt" , column = "BEFORE_ADVT_AMT"),
            @Result(property = "afterAdvtAmt" , column = "AFTER_ADVT_AMT"),
            @Result(property = "insDt" , column = "INS_DT"),
            @Result(property = "insTm" , column = "INS_TM"),
            @Result(property = "confirmTp" , column = "CONFIRM_TP"),
            @Result(property = "confirmDt" , column = "CONFIRM_DT"),
            @Result(property = "confirmTm" , column = "CONFIRM_TM"),
            @Result(property = "cnclMemo" , column = "CNCL_MEMO"),
            @Result(property = "dupRegIpYn" , column = "DUP_REG_IP_YN"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "url" , column = "URL"),
            @Result(property = "urlAgent" , column = "URL_AGENT"),
            @Result(property = "urlReferer" , column = "URL_REFERER"),
            @Result(property = "allMobileDupYn" , column = "ALL_MOBILE_DUP_YN"),
            @Result(property = "thisMobileDupYn" , column = "THIS_MOBILE_DUP_YN"),
            @Result(property = "postbackYn" , column = "POSTBACK_YN"),
            @Result(property = "postbackRetryCtn" , column = "POSTBACK_RETRY_CNT"),
            @Result(property = "postbackDttm" , column = "POSTBACK_DTTM"),
            @Result(property = "adMemo" , column = "AD_MEMO"),
            @Result(property = "mkMemo" , column = "MK_MEMO"),
            @Result(property = "valueData" , column = "VALUE_DATA"),
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
    CPA_DATA getCpaDataForSeqNo(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , CA_ID " +
            "       , MK_ID " +
            "       , CA_NAME " +
            "       , (SELECT NAME FROM LANDING_PAGE WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) PG_NAME" +
            "       , FORMAT(PRICE, 0) AS PRICE " +
            "       , FORMAT(MK_PRICE, 0) AS MK_PRICE " +
            "       , FORMAT(SPEC_PRICE, 0) AS SPEC_PRICE " +
            "       , BONUS_SUPPLY " +
            "       , FORMAT(BONUS_PRICE, 0) AS BONUS_PRICE " +
            "       , SPONSER_ID " +
            "       , FORMAT(SPONSER_PRICE, 0) AS SPONSER_PRICE " +
            "       , FORMAT(BEFORE_ADVT_AMT, 0) AS BEFORE_ADVT_AMT " +
            "       , FORMAT(AFTER_ADVT_AMT, 0) AS AFTER_ADVT_AMT " +
            "       , CONCAT( SUBSTRING(INS_DT, 1, 4), '-', SUBSTRING(INS_DT, 5, 2), '-', SUBSTRING(INS_DT, 7, 2)) INS_DT " +
            "       , CONCAT( SUBSTRING(INS_TM, 1, 2), ':', SUBSTRING(INS_TM, 3, 2), ':', SUBSTRING(INS_TM, 5, 2)) INS_TM " +
            "       , CONFIRM_TP " +
            "       , CONFIRM_DT " +
            "       , CONFIRM_TM " +
            "       , CNCL_MEMO " +
            "       , DUP_REG_IP_YN " +
            "       , REG_IP " +
            "       , URL " +
            "       , URL_AGENT " +
            "       , URL_REFERER " +
            "       , DEVICE_MACHINE " +
            "       , DEVICE_OS " +
            "       , DEVICE_MODEL " +
            "       , ALL_MOBILE_DUP_YN " +
            "       , THIS_MOBILE_DUP_YN " +
            "       , POSTBACK_YN " +
            "       , POSTBACK_RETRY_CNT " +
            "       , POSTBACK_DTTM " +
            "       , AD_MEMO " +
            "       , MK_MEMO " +
            "       , VALUE_DATA " +
            "       , CASE WHEN LENGTH(VALUE01) = 10 THEN CONCAT(  SUBSTR(VALUE01, 1, 3), '-' " +
            "                                                    , SUBSTR(VALUE01, 4, 3), '-' " +
            "                                                    , SUBSTR(VALUE01, 7, 4) ) " +
            "              WHEN LENGTH(VALUE01) = 11 THEN CONCAT(  SUBSTR(VALUE01, 1, 3), '-' " +
            "                                                    , SUBSTR(VALUE01, 4, 4), '-' " +
            "                                                    , SUBSTR(VALUE01, 8, 4) ) " +
            "              ELSE VALUE01 " +
            "         END AS VALUE01 " +
            "       , VALUE02 " +
            "       , VALUE03 " +
            "       , VALUE04 " +
            "       , VALUE05 " +
            "       , VALUE06 " +
            "       , VALUE07 " +
            "       , VALUE08 " +
            "       , VALUE09 " +
            "       , VALUE10 " +
            " FROM " +
            "       CPA_DATA A " +
            " WHERE " +
            "       MB_ID  = #{mbId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   CA_ID  = #{caId} " +
            " AND   MK_ID  = CASE WHEN #{mkId} = 0 THEN MK_ID " +
            "                ELSE #{mkId}          END " +
            " AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            " AND   CONFIRM_TP IN (CASE WHEN #{dbKind} = '대기DB' THEN 'N' " +
            "                           WHEN #{dbKind} = '접수DB' THEN 'R' " +
            "                           WHEN #{dbKind} = '확정DB' THEN 'Y' " +
            "                           WHEN #{dbKind} = '취소DB' THEN 'C' " +
            "                           WHEN #{dbKind} = '자동확정DB' THEN 'A' " +
            "                           ELSE CONFIRM_TP " +
            "                      END) " +
            " AND   INS_DT BETWEEN #{startDt} AND #{finishDt} " +
            " ORDER BY SEQ_NO DESC " +
            " LIMIT #{srtPos}, #{rowCount}" )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "caName" , column = "CA_NAME"),
            @Result(property = "pgName" , column = "PG_NAME"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "mkPrice" , column = "MK_PRICE"),
            @Result(property = "specPrice" , column = "SPEC_PRICE"),
            @Result(property = "bonusSupply" , column = "BONUS_SUPPLY"),
            @Result(property = "bonusPrice" , column = "BONUS_PRICE"),
            @Result(property = "sponserId" , column = "SPONSER_ID"),
            @Result(property = "sponserPrice" , column = "SPONSER_PRICE"),
            @Result(property = "beforeAdvtAmt" , column = "BEFORE_ADVT_AMT"),
            @Result(property = "afterAdvtAmt" , column = "AFTER_ADVT_AMT"),
            @Result(property = "insDt" , column = "INS_DT"),
            @Result(property = "insTm" , column = "INS_TM"),
            @Result(property = "confirmTp" , column = "CONFIRM_TP"),
            @Result(property = "confirmDt" , column = "CONFIRM_DT"),
            @Result(property = "confirmTm" , column = "CONFIRM_TM"),
            @Result(property = "cnclMemo" , column = "CNCL_MEMO"),
            @Result(property = "dupRegIpYn" , column = "DUP_REG_IP_YN"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "url" , column = "URL"),
            @Result(property = "urlAgent" , column = "URL_AGENT"),
            @Result(property = "urlReferer" , column = "URL_REFERER"),
            @Result(property = "deviceMachine" , column = "DEVICE_MACHINE"),
            @Result(property = "deviceOs" , column = "DEVICE_OS"),
            @Result(property = "deviceModel" , column = "DEVICE_MODEL"),
            @Result(property = "allMobileDupYn" , column = "ALL_MOBILE_DUP_YN"),
            @Result(property = "thisMobileDupYn" , column = "THIS_MOBILE_DUP_YN"),
            @Result(property = "postbackYn" , column = "POSTBACK_YN"),
            @Result(property = "postbackRetryCnt" , column = "POSTBACK_RETRY_CNT"),
            @Result(property = "postbackDttm" , column = "POSTBACK_DTTM"),
            @Result(property = "adMemo" , column = "AD_MEMO"),
            @Result(property = "mkMemo" , column = "MK_MEMO"),
            @Result(property = "valueData" , column = "VALUE_DATA"),
            @Result(property = "value01" , column = "VALUE01"),
            @Result(property = "value02" , column = "VALUE02"),
            @Result(property = "value03" , column = "VALUE03"),
            @Result(property = "value04" , column = "VALUE04"),
            @Result(property = "value05" , column = "VALUE05"),
            @Result(property = "value06" , column = "VALUE06"),
            @Result(property = "value07" , column = "VALUE07"),
            @Result(property = "value08" , column = "VALUE08"),
            @Result(property = "value09" , column = "VALUE09"),
            @Result(property = "value10" , column = "VALUE10"),
    })
    List<Map<String, Object>> getCpaDataForMbIdCaIdAdId(Long mbId, Long adId, Long caId, Long mkId, Long pgId, String dbKind, String startDt, String finishDt, Long srtPos, Long rowCount);

    @Select("SELECT " +
            "         COUNT(*)        AS ROW_TOTAL_COUNT " +
            "       , FORMAT(SUM(PRICE), 0)    AS PRICE " +
            "       , FORMAT(SUM(MK_PRICE), 0) AS MK_PRICE " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       MB_ID  = #{mbId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   CA_ID  = #{caId} " +
            " AND   MK_ID  = CASE WHEN #{mkId} = 0 THEN MK_ID " +
            "                ELSE      #{mkId}     END " +
            " AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            " AND   INS_DT BETWEEN #{startDt} AND #{finishDt}" )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT"),
            @Result(property = "price" , column = "PRICE"),
            @Result(property = "mkPrice" , column = "MK_PRICE")
    })
    List<Map<String, Object>> getCpaDataForMbIdCaIdAdIdCount(Long mbId, Long adId, Long caId, Long mkId, Long pgId, String startDt, String finishDt);

    @Select("SELECT " +
            "       COUNT(*) AS ROW_TOTAL_COUNT " +
            " FROM " +
            "       CPA_PAGE_USING_COUNT " +
            " WHERE " +
            "       MB_ID  = #{mbId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   CA_ID  = #{caId} " +
            " AND   MK_ID  = CASE WHEN #{mkId} = 0 THEN MK_ID " +
            "                ELSE      #{mkId}     END " +
            " AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            " AND   EVENT_CD = 'M' " +
            " AND   CRE_DT BETWEEN #{startDt} AND #{finishDt}" )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    Long getCpaPageUsingCountForMbIdCaIdAdIdRowTotalCount(Long mbId, Long adId, Long caId, Long mkId, Long pgId, String startDt, String finishDt);

    @Select("SELECT " +
            "       COUNT(*) AS ROW_TOTAL_COUNT " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       MB_ID  = #{mbId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   CA_ID  = #{caId} " +
            " AND   MK_ID  = CASE WHEN #{mkId} = 0 THEN MK_ID " +
            "                ELSE      #{mkId}     END " +
            " AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " +
            " AND   CONFIRM_TP IN (CASE WHEN #{dbKind} = '대기DB' THEN 'N' " +
            "                           WHEN #{dbKind} = '접수DB' THEN 'R' " +
            "                           WHEN #{dbKind} = '확정DB' THEN 'Y' " +
            "                           WHEN #{dbKind} = '취소DB' THEN 'C' " +
            "                           WHEN #{dbKind} = '자동확정DB' THEN 'A' " +
            "                           ELSE CONFIRM_TP " +
            "                      END) " +
            " AND   INS_DT BETWEEN #{startDt} AND #{finishDt}" )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> getCpaDataForMbIdCaIdAdIdRowTotalCount(Long mbId, Long adId, Long caId, Long mkId, Long pgId, String dbKind, String startDt, String finishDt, Long srtPos, Long rowCount);

    @Select("SELECT " +
            "       COUNT(*) COUNT " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       MB_ID  = #{mbId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   CA_ID  = #{caId} " +
            " AND   MK_ID  = CASE WHEN #{mkId} = 0 THEN MK_ID " +
            " ELSE                     #{mkId}     END " +
            " AND   CONFIRM_TP IN (CASE WHEN #{dbKind} = '대기DB' THEN 'N' " +
            "                           WHEN #{dbKind} = '접수DB' THEN 'R' " +
            "                           WHEN #{dbKind} = '확정DB' THEN 'Y' " +
            "                           WHEN #{dbKind} = '취소DB' THEN 'C' " +
            "                           WHEN #{dbKind} = '자동확정DB' THEN 'A' " +
            "                           ELSE CONFIRM_TP " +
            "                      END) " +
            "AND    INS_DT BETWEEN #{startDt} AND #{finishDt} " )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long getCpaDataForCaIdAdIdPageCount(Long mbId, Long adId, Long caId, Long ptId, String dbKind, String startDt, String finishDt);

    @Select("SELECT " +
            "         CONFIRM_TP   AS CONFIRM_TP" +
            "       , COUNT(*)     AS COUNT " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       MB_ID  = #{mbId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   CA_ID  = #{caId} " +
            " AND   MK_ID  = CASE WHEN #{mkId} = 0 THEN MK_ID " +
            " ELSE                     #{mkId}     END " +
            " AND   INS_DT BETWEEN #{startDt} AND #{finishDt} " +
            " GROUP BY CONFIRM_TP " )
    @Results({
            @Result(property = "confirmTp" , column = "CONFIRM_TP"),
            @Result(property = "count" , column = "COUNT")
    })
    List<Map<String, Object>> getCpaDataForConfirmGroupCount(Long mbId, Long adId, Long caId, Long ptId, String startDt, String finishDt);

    @Update("UPDATE CPA_DATA " +
            "SET    CONFIRM_TP = '${confirm}' " +
            "WHERE  SEQ_NO     = #{seqNo} " )
    Long setOneCpaData(String   confirm, Long seqNo);

    @Update("UPDATE CPA_DATA " +
            "SET      CONFIRM_TP = '${confirm}' " +
            "       , CONFIRM_DT = DATE_FORMAT(SYSDATE(), '%Y%m%d') " +
            "       , CONFIRM_TM = DATE_FORMAT(SYSDATE(), '%H%i%s') " +
            "WHERE  SEQ_NO     = #{seqNo} ")
    Long setOneCpaDataConfirmDttm(String confirm, Long seqNo);
}
