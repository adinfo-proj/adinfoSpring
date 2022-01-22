package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import com.ad.adinfo.Domain.NOTIFY_BOARD;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotifyBoardMapper {
    @Select("SELECT " +
            "         SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , CLNT_ID" +
            "       , HEAD" +
            "       , TITLE" +
            "       , READ_COUNT" +
            " FROM " +
            "       NOTIFY_BOARD" +
            " WHERE " +
            "       SEQ_NO <= ${seqNo}" +
            " AND   USE_TP = '0' " +
            " ORDER BY SEQ_NO DESC")
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "readCount", column = "READ_COUNT"),
    })
    List<Map<String, Object>> getNotifyTitleList(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , HEAD" +
            "       , TITLE" +
            " FROM " +
            "       NOTIFY_BOARD" +
            " WHERE " +
            "       SEQ_NO < ${seqNo}" +
            " AND   USE_TP = '0' " +
            " ORDER BY SEQ_NO DESC " +
            " LIMIT 1")
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE")
    })
    List<Map<String, Object>> getNotifyTitleListBefore(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , HEAD" +
            "       , TITLE" +
            " FROM " +
            "       NOTIFY_BOARD" +
            " WHERE " +
            "       SEQ_NO > ${seqNo}" +
            " AND   USE_TP = '0' " +
            " LIMIT 1")
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE")
    })
    List<Map<String, Object>> getNotifyTitleListAfter(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO" +
            "       , CREATE_DT" +
            "       , UPDATE_DT" +
            "       , CLNT_ID" +
            "       , USE_TP" +
            "       , HEAD" +
            "       , TITLE" +
            "       , CONTENTS" +
            "       , READ_COUNT" +
            "       , MODIFY_COUNT" +
            " FROM " +
            "       NOTIFY_BOARD" +
            " WHERE " +
            "       SEQ_NO = ${seqNo}" +
            " AND   USE_TP = '0' ")
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "useTp", column = "USE_TP"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "contents", column = "CONTENTS"),
            @Result(property = "readCount", column = "READ_COUNT"),
            @Result(property = "modifyCount", column = "MODIFY_COUNT")
    })
    List<Map<String, Object>> getNotifyContents(Long seqNo);

    @Update("UPDATE " +
            "       NOTIFY_BOARD " +
            "SET" +
            "       READ_COUNT = READ_COUNT + 1" +
            " WHERE " +
            "       SEQ_NO = ${seqNo}" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long updNotifyContentsReadCount(Long seqNo);

    @Update("UPDATE " +
            "       NOTIFY_BOARD " +
            "SET" +
            "       USE_TP = '1'" +
            " WHERE " +
            "       SEQ_NO = ${seqNo}" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long delNotifyContents(Long seqNo);

    @Select("INSERT INTO NOTIFY_BOARD " +
            "(" +
            "       SEQ_NO" +
            "     , CREATE_DT" +
            "     , UPDATE_DT" +
            "     , CLNT_ID" +
            "     , USE_TP" +
            "     , HEAD" +
            "     , TITLE" +
            "     , CONTENTS" +
            "     , READ_COUNT" +
            "     , MODIFY_COUNT" +
            ")" +
            "VALUES(" +
            "       0" +
            "     , NOW()" +
            "     , NOW()" +
            "     , #{notifyBoard.clntId}" +
            "     , #{notifyBoard.useTp}" +
            "     , #{notifyBoard.head}" +
            "     , #{notifyBoard.title}" +
            "     , #{notifyBoard.contents}" +
            "     , 0" +
            "     , 0" +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long insNotifyCreate(@Param("notifyBoard") NOTIFY_BOARD notifyBoard);
}
