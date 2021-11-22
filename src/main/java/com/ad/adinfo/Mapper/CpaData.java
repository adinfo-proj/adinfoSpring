package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CPA_DATA;
import com.ad.adinfo.Domain.CPA_MASTER;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CpaData {
    @Select("SELECT " +
            "         SEQ_NO " +
            "       , UPDATE_DT " +
            "       , CA_ID " +
            "       , AD_ID " +
            "       , PT_ID " +
            "       , CA_NAME " +
            "       , PRICE " +
            "       , PT_PRICE " +
            "       , SPEC_PRICE " +
            "       , BONUS_SUPPLY " +
            "       , BONUS_PRICE " +
            "       , SPONSER_ID " +
            "       , SPONSER_PRICE " +
            "       , BEFORE_ADVT_AMT " +
            "       , AFTER_ADVT_AMT " +
            "       , INS_DT " +
            "       , INS_TM " +
            "       , CONFIRM_YN " +
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
            "       , VALUE11 " +
            "       , VALUE12 " +
            "       , VALUE13 " +
            "       , VALUE14 " +
            "       , VALUE15 " +
            "       , VALUE16 " +
            "       , VALUE17 " +
            "       , VALUE18 " +
            "       , VALUE19 " +
            "       , VALUE20 " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       SEQ_NO  = #{seqNo}" )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "ptId" , column = "PT_ID"),
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
            @Result(property = "confirmYn" , column = "CONFIRM_YN"),
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
            @Result(property = "value11" , column = "VALUE11"),
            @Result(property = "value12" , column = "VALUE12"),
            @Result(property = "value13" , column = "VALUE13"),
            @Result(property = "value14" , column = "VALUE14"),
            @Result(property = "value15" , column = "VALUE15"),
            @Result(property = "value16" , column = "VALUE16"),
            @Result(property = "value17" , column = "VALUE17"),
            @Result(property = "value18" , column = "VALUE18"),
            @Result(property = "value19" , column = "VALUE19"),
            @Result(property = "value10" , column = "VALUE20")
    })
    CPA_DATA getCpaDataForSeqNo(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , UPDATE_DT " +
            "       , CA_ID " +
            "       , AD_ID " +
            "       , PT_ID " +
            "       , CA_NAME " +
            "       , PRICE " +
            "       , PT_PRICE " +
            "       , SPEC_PRICE " +
            "       , BONUS_SUPPLY " +
            "       , BONUS_PRICE " +
            "       , SPONSER_ID " +
            "       , SPONSER_PRICE " +
            "       , BEFORE_ADVT_AMT " +
            "       , AFTER_ADVT_AMT " +
            "       , CONCAT( SUBSTRING(INS_DT, 1, 4), '-', SUBSTRING(INS_DT, 5, 2), '-', SUBSTRING(INS_DT, 7, 2)) INS_DT " +
            "       , CONCAT( SUBSTRING(INS_TM, 1, 2), ':', SUBSTRING(INS_TM, 3, 2), ':', SUBSTRING(INS_TM, 5, 2)) INS_TM " +
            "       , CONFIRM_YN " +
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
            "       , VALUE11 " +
            "       , VALUE12 " +
            "       , VALUE13 " +
            "       , VALUE14 " +
            "       , VALUE15 " +
            "       , VALUE16 " +
            "       , VALUE17 " +
            "       , VALUE18 " +
            "       , VALUE19 " +
            "       , VALUE20 " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       CA_ID  = #{caId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   PT_ID  = CASE WHEN #{ptId} = 0 THEN PT_ID " +
            "                ELSE #{ptId}          END " +
            " AND   CONFIRM_YN IN (CASE WHEN #{dbKind} = '대기DB' THEN 'N' " +
            "                           WHEN #{dbKind} = '접수DB' THEN 'R' " +
            "                           WHEN #{dbKind} = '확정DB' THEN 'Y' " +
            "                           WHEN #{dbKind} = '취소DB' THEN 'C' " +
            "                           WHEN #{dbKind} = '자동확정DB' THEN 'A' " +
            "                           ELSE CONFIRM_YN " +
            "                      END) " +
            " AND    INS_DT BETWEEN #{startDt} AND #{finishDt} " +
            " ORDER BY SEQ_NO DESC " +
            " LIMIT #{srt}, 10" )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "ptId" , column = "PT_ID"),
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
            @Result(property = "confirmYn" , column = "CONFIRM_YN"),
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
            @Result(property = "value11" , column = "VALUE11"),
            @Result(property = "value12" , column = "VALUE12"),
            @Result(property = "value13" , column = "VALUE13"),
            @Result(property = "value14" , column = "VALUE14"),
            @Result(property = "value15" , column = "VALUE15"),
            @Result(property = "value16" , column = "VALUE16"),
            @Result(property = "value17" , column = "VALUE17"),
            @Result(property = "value18" , column = "VALUE18"),
            @Result(property = "value19" , column = "VALUE19"),
            @Result(property = "value10" , column = "VALUE20")
    })
    List<CPA_DATA> getCpaDataForCaIdAdId(Long caId, Long adId, Long ptId, String dbKind, String startDt, String finishDt, Long srt);

    @Select("SELECT " +
            "       COUNT(*) COUNT " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       CA_ID  = #{caId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   PT_ID  = CASE WHEN #{ptId} = 0 THEN PT_ID " +
            " ELSE                     #{ptId}     END " +
            " AND   CONFIRM_YN IN (CASE WHEN #{dbKind} = '대기DB' THEN 'N' " +
                    "                           WHEN #{dbKind} = '접수DB' THEN 'R' " +
                    "                           WHEN #{dbKind} = '확정DB' THEN 'Y' " +
                    "                           WHEN #{dbKind} = '취소DB' THEN 'C' " +
                    "                           WHEN #{dbKind} = '자동확정DB' THEN 'A' " +
                    "                           ELSE CONFIRM_YN " +
                    "                      END) " +
            "AND    INS_DT BETWEEN #{startDt} AND #{finishDt} " )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long getCpaDataForCaIdAdIdPageCount(Long caId, Long adId, Long ptId, String dbKind, String startDt, String finishDt);

    @Select("SELECT " +
            "         CONFIRM_YN   AS CONFIRM_YN" +
            "       , COUNT(*)     AS COUNT " +
            " FROM " +
            "       CPA_DATA " +
            " WHERE " +
            "       CA_ID  = #{caId} " +
            " AND   AD_ID  = #{adId} " +
            " AND   PT_ID  = CASE WHEN #{ptId} = 0 THEN PT_ID " +
            " ELSE                     #{ptId}     END " +
            " AND   INS_DT BETWEEN #{startDt} AND #{finishDt} " +
            " GROUP BY CONFIRM_YN " )
    @Results({
            @Result(property = "confirmYn" , column = "CONFIRM_YN"),
            @Result(property = "count" , column = "COUNT")
    })
    List<Map<String, Object>> getCpaDataForConfirmGroupCount(Long caId, Long adId, Long ptId, String startDt, String finishDt);

    @Update("UPDATE CPA_DATA " +
            "SET    CONFIRM_YN = '${confirm}' " +
            "WHERE  SEQ_NO     = #{seqNo} " )
    Long setOneCpaData(String   confirm, Long seqNo);

    @Update("UPDATE CPA_DATA " +
            "SET      CONFIRM_YN = '${confirm}' " +
            "       , CONFIRM_DT = DATE_FORMAT(SYSDATE(), '%Y%m%d') " +
            "       , CONFIRM_TM = DATE_FORMAT(SYSDATE(), '%H%i%s') " +
            "WHERE  SEQ_NO     = #{seqNo} ")
    Long setOneCpaDataConfirmDttm(String confirm, Long seqNo);
}
