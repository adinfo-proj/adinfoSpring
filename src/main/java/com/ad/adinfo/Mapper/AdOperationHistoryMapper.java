package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.AD_OPERATION_HISTORY;
import com.ad.adinfo.Domain.AD_USER_MASTER;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdOperationHistoryMapper {
    @Insert("INSERT INTO AD_OPERATION_HISTORY " +
            "( " +
            "         SEQ_NO" +
            "       , CRE_DT" +
            "       , CRE_TM" +
            "       , CLNT_TP" +
            "       , MB_ID" +
            "       , CLNT_ID" +
            "       , COMMON_CD" +
            "       , COMMENT" +
            ") " +
            "VALUES(" +
            "         0" +
            "       , (SELECT DATE_FORMAT(NOW(), '%Y%m%d') FROM DUAL)" +
            "       , (SELECT DATE_FORMAT(NOW(), '%H%i%s') FROM DUAL)" +
            "       , #{adOperationHistory.clntTp}" +
            "       , #{adOperationHistory.mbId}" +
            "       , #{adOperationHistory.clntId}" +
            "       , #{adOperationHistory.commonCd}" +
            "       , #{adOperationHistory.comment}" +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "mbId")
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO")
    })
    Long insAdOperationHistory(@Param("adOperationHistory") AD_OPERATION_HISTORY adOperationHistory);
}
