package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.NOTICE_BOARD;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeBoardMapper {
    @Select("SELECT " +
            "         BODY_SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , CLNT_ID" +
            "       , (SELECT CLNT_NM FROM AD_USER_MASTER WHERE CLNT_ID = A.CLNT_ID) CLNT_NM " +
            "       , HEAD" +
            "       , TITLE" +
            "       , READ_COUNT" +
            " FROM " +
            "       NOTICE_BOARD A" +
            " WHERE " +
            "       GROUP_TP     = #{groupTp} " +
            " AND   USE_TP       = #{useTp} " +
            " ORDER BY BODY_SEQ_NO DESC" +
            " LIMIT #{srtPos}, #{rowCount}"
    )
    @Results({
            @Result(property = "seqNo", column = "BODY_SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "clntNm", column = "CLNT_NM"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "readCount", column = "READ_COUNT"),
    })
    List<Map<String, Object>> getNoticeTitleList(Long seqNo, String groupTp, String useTp, Long srtPos, Long rowCount);

    @Select("SELECT " +
            "         BODY_SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , HEAD" +
            "       , TITLE" +
            " FROM " +
            "       NOTICE_BOARD" +
            " WHERE " +
            "       BODY_SEQ_NO < ${seqNo}" +
            " AND   GROUP_TP    = #{groupTp} " +
            " AND   USE_TP      = #{useTp} " +
            " ORDER BY BODY_SEQ_NO DESC " +
            " LIMIT 1")
    @Results({
            @Result(property = "bodySeqNo", column = "BODY_SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE")
    })
    List<Map<String, Object>> getNoticeTitleListBefore(Long seqNo, String groupTp, String useTp);

    @Select("SELECT " +
            "         BODY_SEQ_NO" +
            "       , SUBSTR(CREATE_DT, 1, 10) AS CREATE_DT" +
            "       , HEAD" +
            "       , TITLE" +
            " FROM " +
            "       NOTICE_BOARD" +
            " WHERE " +
            "       BODY_SEQ_NO > ${seqNo}" +
            " AND   GROUP_TP    = #{groupTp} " +
            " AND   USE_TP      = #{useTp} " +
            " LIMIT 1")
    @Results({
            @Result(property = "bodySeqNo", column = "BODY_SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE")
    })
    List<Map<String, Object>> getNoticeTitleListAfter(Long seqNo, String groupTp, String useTp);

    @Select("SELECT " +
            "         BODY_SEQ_NO" +
            "       , CREATE_DT" +
            "       , UPDATE_DT" +
            "       , CLNT_ID" +
            "       , (SELECT CLNT_NM FROM AD_USER_MASTER WHERE CLNT_ID = A.CLNT_ID) CLNT_NM " +
            "       , USE_TP" +
            "       , HEAD" +
            "       , TITLE" +
            "       , CONTENTS" +
            "       , READ_COUNT" +
            "       , MODIFY_COUNT" +
            " FROM " +
            "       NOTICE_BOARD A " +
            " WHERE " +
            "       BODY_SEQ_NO = ${seqNo}" +
            " AND   GROUP_TP    = #{groupTp} " +
            " AND   USE_TP      = #{useTp} ")
    @Results({
            @Result(property = "bodySeqNo", column = "BODY_SEQ_NO"),
            @Result(property = "createDt", column = "CREATE_DT"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "clntId", column = "CLNT_ID"),
            @Result(property = "clntNm", column = "CLNT_NM"),
            @Result(property = "useTp", column = "USE_TP"),
            @Result(property = "head", column = "HEAD"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "contents", column = "CONTENTS"),
            @Result(property = "readCount", column = "READ_COUNT"),
            @Result(property = "modifyCount", column = "MODIFY_COUNT")
    })
    List<Map<String, Object>> getNoticeContents(Long seqNo, String groupTp, String useTp);

    @Update(" UPDATE " +
            "        NOTICE_BOARD " +
            " SET" +
            "        READ_COUNT = READ_COUNT + 1" +
            " WHERE " +
            "        BODY_SEQ_NO = ${seqNo}" +
            " AND    GROUP_TP    = #{groupTp}")
    Long updNoticeContentsReadCount(Long seqNo, String groupTp);

    @Update(" UPDATE " +
            "        NOTICE_BOARD " +
            " SET" +
            "        USE_TP = 'H'" +
            " WHERE " +
            "        BODY_SEQ_NO = ${seqNo}" +
            " AND    GROUP_TP    = #{groupTp}")
    Long delNoticeContents(Long seqNo, String groupTp);

    @Insert("INSERT INTO NOTICE_BOARD " +
            "(" +
            "       BODY_SEQ_NO" +
            "     , COMMENT_SEQ_NO" +
            "     , CREATE_DT" +
            "     , UPDATE_DT" +
            "     , CLNT_ID" +
            "     , GROUP_TP" +
            "     , USE_TP" +
            "     , BODY_TP" +
            "     , HEAD" +
            "     , TITLE" +
            "     , CONTENTS" +
            "     , READ_COUNT" +
            "     , MODIFY_COUNT" +
            ") " +
            "VALUES(" +
            "       #{noticeBoard.bodySeqNo}" +
            "     , #{noticeBoard.commentSeqNo}" +
            "     , NOW()" +
            "     , NOW()" +
            "     , #{noticeBoard.clntId}" +
            "     , #{noticeBoard.groupTp} " +
            "     , #{noticeBoard.useTp}" +
            "     , #{noticeBoard.bodyTp}" +
            "     , #{noticeBoard.head}" +
            "     , #{noticeBoard.title}" +
            "     , #{noticeBoard.contents}" +
            "     , 0" +
            "     , 0" +
            ")" )
    @Options(useGeneratedKeys = true, keyProperty = "bodySeqNo")
    Long insNoticeCreate(@Param("noticeBoard") NOTICE_BOARD noticeBoard);

    @Update(" UPDATE " +
            "        NOTICE_BOARD " +
            " SET " +
            "        UPDATE_DT = NOW()" +
            "      , CLNT_ID   = #{noticeBoard.clntId}" +
            "      , USE_TP    = #{noticeBoard.useTp}" +
            "      , HEAD      = #{noticeBoard.head}" +
            "      , TITLE     = #{noticeBoard.title}" +
            "      , CONTENTS  = #{noticeBoard.contents}" +
            " WHERE " +
            "      BODY_SEQ_NO = #{noticeBoard.bodySeqNo}" +
            " AND  GROUP_TP    = #{noticeBoard.groupTp}" )
    @Options(useGeneratedKeys = true, keyProperty = "bodySeqNo")
    Long updNoticeCreate(@Param("noticeBoard") NOTICE_BOARD noticeBoard);

    @Select("SELECT " +
            "       COUNT(*) AS ROW_TOTAL_COUNT " +
            " FROM " +
            "       NOTICE_BOARD " +
            " WHERE " +
            "       GROUP_TP = #{groupTp} " +
            " AND   USE_TP   = #{useTp} " )
    @Results({
            @Result(property = "rowTotalCount" , column = "ROW_TOTAL_COUNT")
    })
    List<Map<String, Object>> getNoticeRowTotalCount(String useTp, String groupTp);

    @Select("SELECT " +
            "       IFNULL(MAX(BODY_SEQ_NO) + 1, 1) AS BODY_SEQ_NO " +
            " FROM " +
            "       NOTICE_BOARD " +
            " WHERE " +
            "       GROUP_TP = #{groupTp} ")
    @Results({
            @Result(property = "bodySeqNo" , column = "BODY_SEQ_NO")
    })
    Long getNoticeMaxBodySeqNo(String groupTp);

    @Select("SELECT " +
            "       IFNULL(MAX(COMMENT_SEQ_NO) + 1, 1) AS COMMENT_SEQ_NO " +
            " FROM " +
            "       NOTICE_BOARD " +
            " WHERE " +
            "       BODY_SEQ_NO = ${bodySeqNo} " +
            " AND   GROUP_TP    = #{groupTp} ")
    @Results({
            @Result(property = "commentSeqNo" , column = "COMMENT_SEQ_NO")
    })
    Long getNoticeMaxCommentSeqNo(Long bodySeqNo, String groupTp);
}