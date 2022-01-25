package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.NOTIFY_BOARD;
import com.ad.adinfo.Domain.TB_LANDING_PAGE;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT " +
            "        IFNULL(MAX(PG_ID), 10000) AS PG_ID " +
            "FROM " +
            "        LANDING_PAGE " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   MK_ID      = ${mkId} " +
            "AND   CA_ID      = ${caId} ")
    @Results({
            @Result(property = "pgId" , column = "PG_ID")
    })
    Long selLandingPageMaxCaId(Long mbId, Long adId, Long mkId, Long caId);

    @Select("SELECT " +
            "        COUNT(*) AS COUNT " +
            "FROM " +
            "        LANDING_PAGE " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   MK_ID      = ${mkId} " +
            "AND   CA_ID      = ${caId} " +
            "AND   NAME       = #{name} " )
    @Results({
            @Result(property = "count" , column = "COUNT")
    })
    Long selLandingPageDupName(Long mbId, Long adId, Long mkId, Long caId, String name);
}
