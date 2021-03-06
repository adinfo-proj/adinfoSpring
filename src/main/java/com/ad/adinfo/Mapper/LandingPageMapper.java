package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.TB_LANDING_PAGE;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface LandingPageMapper {
    @Insert("INSERT INTO LANDING_PAGE " +
            "( " +
            "       SEQ_NO " +
            "     , UPDATE_DT " +
            "     , MB_ID " +
            "     , AD_ID " +
            "     , MK_ID " +
            "     , CA_ID " +
            "     , PG_ID " +
            "     , USE_TP " +
            "     , NAME " +
            "     , AD_NAME " +
            "     , TITLE_NAME " +
            "     , URL " +
            "     , ASK_LIST " +
            "     , MEMO " +
            "     , REG_CLNT_ID " +
            "     , REG_IP " +
            "     , TYPE01 " +
            "     , TYPE02 " +
            "     , TYPE03 " +
            "     , TYPE04 " +
            "     , TYPE05 " +
            "     , TYPE06 " +
            "     , TYPE07 " +
            "     , TYPE08 " +
            "     , TYPE09 " +
            "     , TYPE10 " +
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
            "     , PAGE01 " +
            "     , PAGE02 " +
            "     , PAGE03 " +
            "     , PAGE04 " +
            "     , PAGE05 " +
            "     , PAGE06 " +
            "     , PAGE07 " +
            "     , PAGE08 " +
            "     , PAGE09 " +
            "     , PAGE10 " +
            ") " +
            "VALUES( " +
            "       0 " +
            "     , NOW() " +
            "     , #{landingPage.mbId} " +
            "     , #{landingPage.adId} " +
            "     , #{landingPage.mkId} " +
            "     , #{landingPage.caId} " +
            "     , #{landingPage.pgId} " +
            "     , #{landingPage.useTp} " +
            "     , #{landingPage.name} " +
            "     , #{landingPage.adName} " +
            "     , #{landingPage.titleName} " +
            "     , #{landingPage.url} " +
            "     , #{landingPage.askList} " +
            "     , #{landingPage.memo} " +
            "     , #{landingPage.regClntId} " +
            "     , #{landingPage.regIp} " +
            "     , #{landingPage.type01} " +
            "     , #{landingPage.type02} " +
            "     , #{landingPage.type03} " +
            "     , #{landingPage.type04} " +
            "     , #{landingPage.type05} " +
            "     , #{landingPage.type06} " +
            "     , #{landingPage.type07} " +
            "     , #{landingPage.type08} " +
            "     , #{landingPage.type09} " +
            "     , #{landingPage.type10} " +
            "     , #{landingPage.value01} " +
            "     , #{landingPage.value02} " +
            "     , #{landingPage.value03} " +
            "     , #{landingPage.value04} " +
            "     , #{landingPage.value05} " +
            "     , #{landingPage.value06} " +
            "     , #{landingPage.value07} " +
            "     , #{landingPage.value08} " +
            "     , #{landingPage.value09} " +
            "     , #{landingPage.value10} " +
            "     , #{landingPage.page01} " +
            "     , #{landingPage.page02} " +
            "     , #{landingPage.page03} " +
            "     , #{landingPage.page04} " +
            "     , #{landingPage.page05} " +
            "     , #{landingPage.page06} " +
            "     , #{landingPage.page07} " +
            "     , #{landingPage.page08} " +
            "     , #{landingPage.page09} " +
            "     , #{landingPage.page10} " +
            ") ")
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long insLandingPage(@Param("landingPage") TB_LANDING_PAGE landingPage);

    @Update("UPDATE " +
            "       LANDING_PAGE " +
            "SET " +
            "       SEQ_NO      = #{landingPage.seqNo} " +
            "     , UPDATE_DT   = NOW() " +
            "     , MB_ID       = #{landingPage.mbId} " +
            "     , AD_ID       = #{landingPage.adId} " +
            "     , MK_ID       = #{landingPage.mkId} " +
            "     , CA_ID       = #{landingPage.caId} " +
            "     , PG_ID       = #{landingPage.pgId} " +
            "     , USE_TP      = #{landingPage.useTp} " +
            "     , NAME        = #{landingPage.name} " +
            "     , AD_NAME     = #{landingPage.adName} " +
            "     , TITLE_NAME  = #{landingPage.titleName} " +
            "     , URL         = #{landingPage.url} " +
            "     , ASK_LIST    = #{landingPage.askList} " +
            "     , MEMO        = #{landingPage.memo} " +
            "     , REG_CLNT_ID = #{landingPage.regClntId} " +
            "     , REG_IP      = #{landingPage.regIp} " +
            "     , TYPE01      = #{landingPage.type01} " +
            "     , TYPE02      = #{landingPage.type02} " +
            "     , TYPE03      = #{landingPage.type03} " +
            "     , TYPE04      = #{landingPage.type04} " +
            "     , TYPE05      = #{landingPage.type05} " +
            "     , TYPE06      = #{landingPage.type06} " +
            "     , TYPE07      = #{landingPage.type07} " +
            "     , TYPE08      = #{landingPage.type08} " +
            "     , TYPE09      = #{landingPage.type09} " +
            "     , TYPE10      = #{landingPage.type10} " +
            "     , VALUE01     = #{landingPage.value01} " +
            "     , VALUE02     = #{landingPage.value02} " +
            "     , VALUE03     = #{landingPage.value03} " +
            "     , VALUE04     = #{landingPage.value04} " +
            "     , VALUE05     = #{landingPage.value05} " +
            "     , VALUE06     = #{landingPage.value06} " +
            "     , VALUE07     = #{landingPage.value07} " +
            "     , VALUE08     = #{landingPage.value08} " +
            "     , VALUE09     = #{landingPage.value09} " +
            "     , VALUE10     = #{landingPage.value10} " +
            "     , PAGE01      = #{landingPage.page01} " +
            "     , PAGE02      = #{landingPage.page02} " +
            "     , PAGE03      = #{landingPage.page03} " +
            "     , PAGE04      = #{landingPage.page04} " +
            "     , PAGE05      = #{landingPage.page05} " +
            "     , PAGE06      = #{landingPage.page06} " +
            "     , PAGE07      = #{landingPage.page07} " +
            "     , PAGE08      = #{landingPage.page08} " +
            "     , PAGE09      = #{landingPage.page09} " +
            "     , PAGE10      = #{landingPage.page10} " +
            "WHERE " +
            "       SEQ_NO      = #{landingPage.seqNo} " )
            @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long updLandingPage(@Param("landingPage") TB_LANDING_PAGE landingPage);

    @Select("SELECT " +
            "        SEQ_NO " +
            "      , UPDATE_DT " +
            "      , MB_ID " +
            "      , AD_ID " +
            "      , MK_ID " +
            "      , CA_ID " +
            "      , PG_ID " +
            "      , USE_TP " +
            "      , NAME " +
            "      , AD_NAME " +
            "      , TITLE_NAME " +
            "      , URL " +
            "      , ASK_LIST " +
            "      , MEMO " +
            "      , REG_CLNT_ID " +
            "      , REG_IP " +
            "      , TYPE01 " +
            "      , TYPE02 " +
            "      , TYPE03 " +
            "      , TYPE04 " +
            "      , TYPE05 " +
            "      , TYPE06 " +
            "      , TYPE07 " +
            "      , TYPE08 " +
            "      , TYPE09 " +
            "      , TYPE10 " +
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
            "      , PAGE01 " +
            "      , PAGE02 " +
            "      , PAGE03 " +
            "      , PAGE04 " +
            "      , PAGE05 " +
            "      , PAGE06 " +
            "      , PAGE07 " +
            "      , PAGE08 " +
            "      , PAGE09 " +
            "      , PAGE10 " +
            "FROM " +
            "        LANDING_PAGE " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            " AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   PG_ID      = ${pgId} " )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "useTp" , column = "USE_TP"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "adName" , column = "AD_NAME"),
            @Result(property = "titleName" , column = "TITLE_NAME"),
            @Result(property = "url" , column = "URL"),
            @Result(property = "askList" , column = "ASK_LIST"),
            @Result(property = "memo" , column = "MEMO"),
            @Result(property = "regClntId" , column = "REG_CLNT_ID"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "type01" , column = "TYPE01"),
            @Result(property = "type02" , column = "TYPE02"),
            @Result(property = "type03" , column = "TYPE03"),
            @Result(property = "type04" , column = "TYPE04"),
            @Result(property = "type05" , column = "TYPE05"),
            @Result(property = "type06" , column = "TYPE06"),
            @Result(property = "type07" , column = "TYPE07"),
            @Result(property = "type08" , column = "TYPE08"),
            @Result(property = "type09" , column = "TYPE09"),
            @Result(property = "type10" , column = "TYPE10"),
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
            @Result(property = "page01" , column = "PAGE01"),
            @Result(property = "page02" , column = "PAGE02"),
            @Result(property = "page03" , column = "PAGE03"),
            @Result(property = "page04" , column = "PAGE04"),
            @Result(property = "page05" , column = "PAGE05"),
            @Result(property = "page06" , column = "PAGE06"),
            @Result(property = "page07" , column = "PAGE07"),
            @Result(property = "page08" , column = "PAGE08"),
            @Result(property = "page09" , column = "PAGE09"),
            @Result(property = "page10" , column = "PAGE10")
    })
    Map<String, Object> selLandingPage(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        SEQ_NO " +
            "      , UPDATE_DT " +
            "      , MB_ID " +
            "      , AD_ID " +
            "      , MK_ID " +
            "      , CA_ID " +
            "      , PG_ID " +
            "      , USE_TP " +
            "      , NAME " +
            "      , AD_NAME " +
            "      , TITLE_NAME " +
            "      , URL " +
            "      , ASK_LIST " +
            "      , MEMO " +
            "      , REG_CLNT_ID " +
            "      , REG_IP " +
            "      , TYPE01 " +
            "      , TYPE02 " +
            "      , TYPE03 " +
            "      , TYPE04 " +
            "      , TYPE05 " +
            "      , TYPE06 " +
            "      , TYPE07 " +
            "      , TYPE08 " +
            "      , TYPE09 " +
            "      , TYPE10 " +
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
            "      , PAGE01 " +
            "      , PAGE02 " +
            "      , PAGE03 " +
            "      , PAGE04 " +
            "      , PAGE05 " +
            "      , PAGE06 " +
            "      , PAGE07 " +
            "      , PAGE08 " +
            "      , PAGE09 " +
            "      , PAGE10 " +
            "FROM " +
            "        LANDING_PAGE " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   ((-1 = ${pgId}) OR (PG_ID = ${pgId})) " )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "useTp" , column = "USE_TP"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "adName" , column = "AD_NAME"),
            @Result(property = "titleName" , column = "TITLE_NAME"),
            @Result(property = "url" , column = "URL"),
            @Result(property = "askList" , column = "ASK_LIST"),
            @Result(property = "memo" , column = "MEMO"),
            @Result(property = "regClntId" , column = "REG_CLNT_ID"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "type01" , column = "TYPE01"),
            @Result(property = "type02" , column = "TYPE02"),
            @Result(property = "type03" , column = "TYPE03"),
            @Result(property = "type04" , column = "TYPE04"),
            @Result(property = "type05" , column = "TYPE05"),
            @Result(property = "type06" , column = "TYPE06"),
            @Result(property = "type07" , column = "TYPE07"),
            @Result(property = "type08" , column = "TYPE08"),
            @Result(property = "type09" , column = "TYPE09"),
            @Result(property = "type10" , column = "TYPE10"),
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
            @Result(property = "page01" , column = "PAGE01"),
            @Result(property = "page02" , column = "PAGE02"),
            @Result(property = "page03" , column = "PAGE03"),
            @Result(property = "page04" , column = "PAGE04"),
            @Result(property = "page05" , column = "PAGE05"),
            @Result(property = "page06" , column = "PAGE06"),
            @Result(property = "page07" , column = "PAGE07"),
            @Result(property = "page08" , column = "PAGE08"),
            @Result(property = "page09" , column = "PAGE09"),
            @Result(property = "page10" , column = "PAGE10")
    })
    TB_LANDING_PAGE selLandingPageByTableOne(Long mbId, Long adId, Long caId, Long pgId);

    @Select("SELECT " +
            "        IFNULL(MAX(PG_ID), 10000) AS PG_ID " +
            "FROM " +
            "        LANDING_PAGE " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   MK_ID      = ${mkId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " )
    @Results({
            @Result(property = "pgId" , column = "PG_ID")
    })
    Long selLandingPageMaxCaId(Long mbId, Long adId, Long mkId, Long caId);

    @Select("SELECT " +
            "        COUNT(*) AS COUNT " +
            "FROM " +
            "        LANDING_PAGE A, CAMPAIGN_MASTER B " +
            "WHERE " +
            "      A.MB_ID      = ${mbId} " +
            "AND   A.AD_ID      = ${adId} " +
            "AND   A.MK_ID      = ${mkId} " +
            "AND   ((-1 = ${caId}) OR (A.CA_ID = ${caId})) " +
            "AND   A.USE_TP     = '00'    " +
            "AND   A.MB_ID      = B.MB_ID " +
            "AND   A.AD_ID      = B.AD_ID " +
            "AND   A.CA_ID      = B.CA_ID " +
            "AND   B.STATUS     = '01' "
    )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long selLandingMbTotalCount(Long mbId, Long adId, Long mkId, Long caId);

    @Select("SELECT " +
            "        COUNT(*) AS COUNT " +
            "FROM " +
            "        LANDING_PAGE " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   MK_ID      = ${mkId} " +
            "AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            "AND   USE_TP    <> '03' " +
            "AND   NAME       = #{name} " )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long selLandingPageDupName(Long mbId, Long adId, Long mkId, Long caId, String name);

    @Select("SELECT " +
            "      COUNT(*) AS COUNT " +
            "FROM " +
            "      LANDING_PAGE " +
            "WHERE " +
            "      URL = #{url} " )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long selLandingPageDupUrl(String url);

    @Select("SELECT " +
            "         PG_ID " +
            "       , NAME " +
            " FROM " +
            "       LANDING_PAGE" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   MK_ID  = ${mkId}" +
            " AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            " AND   ((99 = ${useTp}) OR (USE_TP = ${useTp})) " +
            " AND   USE_TP <> '03' " +
            " ORDER BY CA_ID ASC")
    @Results({
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "name" , column = "NAME")
    })
    List<Map<String, Object>> GetLandingListForMbAdCaCode(Long mbId, Long adId, Long mkId, Long caId, String useTp);

    @Select("SELECT " +
            "         A.SEQ_NO " +
            "       , A.MB_ID " +
            "       , A.AD_ID " +
            "       , A.MK_ID " +
            "       , A.CA_ID " +
            "       , A.PG_ID " +
            "       , A.USE_TP " +
            "       , A.NAME " +
            "       , (SELECT NAME     FROM CAMPAIGN_MASTER      WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) AD_NAME" +
            "       , A.TITLE_NAME " +
            "       , A.URL " +
            "       , (SELECT ASK_LIST FROM CAMPAIGN_MASTER      WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID) ASK_LIST" +
            "       , A.MEMO " +
            "       , (SELECT COUNT(*) FROM CPA_DATA             WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) CREATE_COUNT" +
//            "       , (SELECT COUNT(*) FROM CPA_PAGE_USING_COUNT WHERE MB_ID = A.MB_ID AND AD_ID = A.AD_ID AND CA_ID = A.CA_ID AND PG_ID = A.PG_ID) VIEW_COUNT" +
            " FROM " +
            "       LANDING_PAGE A" +
            "     , CAMPAIGN_MASTER B " +
            " WHERE " +
            "       A.MB_ID  = ${mbId}" +
            " AND   A.AD_ID  = ${adId}" +
            " AND   A.MK_ID  = ${mkId}" +

            " AND   A.MB_ID  = B.MB_ID" +
            " AND   A.AD_ID  = B.AD_ID" +
            " AND   A.CA_ID  = B.CA_ID" +

            " AND   ((-1 = ${caId}) OR (A.CA_ID = ${caId})) " +
            " AND   ((99 = ${useTp}) OR (A.USE_TP = ${useTp})) " +
            " AND   A.USE_TP <> '03' " +
            " AND   B.STATUS  = '01' " +
            " ORDER BY A.SEQ_NO DESC" +
            " LIMIT #{srtPos}, #{rowCount}"
    )
    @Results({
            @Result(property = "seqNo", column = "SEQ_NO"),
            @Result(property = "updateDt", column = "UPDATE_DT"),
            @Result(property = "mbId", column = "MB_ID"),
            @Result(property = "adId", column = "AD_ID"),
            @Result(property = "mkId", column = "MK_ID"),
            @Result(property = "caId", column = "CA_ID"),
            @Result(property = "pgId", column = "PG_ID"),
            @Result(property = "useTp", column = "USE_TP"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "adName", column = "AD_NAME"),
            @Result(property = "titleName", column = "TITLE_NAME"),
            @Result(property = "url", column = "URL"),
            @Result(property = "askList", column = "ASK_LIST"),
            @Result(property = "memo", column = "MEMO"),
            @Result(property = "regClntId", column = "REG_CLNT_ID"),
            @Result(property = "regIp", column = "REG_IP"),
            @Result(property = "createCount", column = "CREATE_COUNT")
    })
    List<Map<String, Object>> GetLandingListForMbAdCa(Long mbId, Long adId, Long mkId, Long caId, String useTp, Long srtPos, Long rowCount);

    @Select("SELECT " +
            "       COUNT(*) AS COUNT " +
            " FROM " +
            "       LANDING_PAGE A" +
            " WHERE " +
            "       MB_ID  = ${mbId}" +
            " AND   AD_ID  = ${adId}" +
            " AND   MK_ID  = ${mkId}" +
            " AND   ((-1 = ${caId}) OR (CA_ID = ${caId})) " +
            " AND   ((99 = ${useTp}) OR (USE_TP = ${useTp})) " +
            " AND   USE_TP <> '03' " +
            " ORDER BY CA_ID DESC" )
    @Results({
            @Result(property = "count", column = "COUNT")
    })
    List<Map<String, Object>> GetLandingListForMbAdCaRowCount(Long mbId, Long adId, Long mkId, Long caId, String useTp);

    @Update(" UPDATE " +
            "        LANDING_PAGE " +
            " SET" +
            "        UPDATE_DT  = NOW()" +
            "      , USE_TP     = #{status}" +
            " WHERE " +
            "        MB_ID      = #{mbId}" +
            " AND    AD_ID      = #{adId}" +
            " AND    CA_ID      = #{caId}" +
            " AND    PG_ID      = #{pgId}" )
    Long changeStatusLandingPage(Long mbId, Long adId, Long caId, Long pgId, String status);
}