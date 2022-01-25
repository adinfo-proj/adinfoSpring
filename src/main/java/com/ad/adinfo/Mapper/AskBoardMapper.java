package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.ASK_BOARD;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AskBoardMapper {
    @Select("SELECT " +
            "         SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , CLNT_ID " +
            "       , (SELECT CLNT_NM FROM AD_USER_MASTER WHERE CLNT_ID = A.CLNT_ID) CLNT_NM " +
            "       , HEAD" +
            "       , TITLE" +
            "       , READ_COUNT" +
            " FROM " +
            "       ASK_BOARD A" +
            " WHERE " +
            "       SEQ_NO <= ${seqNo}" +
            " AND   USE_TP = '0' " +
            " ORDER BY SEQ_NO DESC" +
            " LIMIT #{srtPos}, #{rowCount}")
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "clntNm", column = "CLNT_NM"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "readCount", column = "READ_COUNT"),
    })
    List<Map<String, Object>> getAskTitleList(Long seqNo, Long srtPos, Long rowCount);

    @Select("SELECT " +
            "         SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , HEAD" +
            "       , TITLE" +
            " FROM " +
            "       ASK_BOARD" +
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
    List<Map<String, Object>> getAskTitleListBefore(Long seqNo);

    @Select("SELECT " +
            "         SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , HEAD" +
            "       , TITLE" +
            " FROM " +
            "       ASK_BOARD" +
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
    List<Map<String, Object>> getAskTitleListAfter(Long seqNo);

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
            "       ASK_BOARD" +
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
    List<Map<String, Object>> getAskContents(Long seqNo);

    @Update("UPDATE " +
            "       ASK_BOARD " +
            "SET" +
            "       READ_COUNT = READ_COUNT + 1" +
            " WHERE " +
            "       SEQ_NO = ${seqNo}" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long updAskContentsReadCount(Long seqNo);

    @Update("UPDATE " +
            "       ASK_BOARD " +
            "SET" +
            "       USE_TP = '1'" +
            " WHERE " +
            "       SEQ_NO = ${seqNo}" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long delAskContents(Long seqNo);

    @Insert("INSERT INTO ASK_BOARD " +
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
    Long insAskCreate(@Param("notifyBoard") ASK_BOARD notifyBoard);

    @Select("SELECT " +
            "       COUNT(*) AS ROW_TOTAL_COUNT " +
            " FROM " +
            "       ASK_BOARD " +
            " WHERE " +
            "       USE_TP = ${useTp} " )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    Long getAskRowTotalCount(String useTp);
}
