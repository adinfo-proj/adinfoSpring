package com.ad.adinfo.Mapper;

import com.ad.adinfo.Domain.TB_CAMPAIGN_LANDING_FORM;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CampaignLandingFormMapper {
    @Insert("INSERT INTO CAMPAIGN_LANDING_FORM " +
            "( " +
            "       SEQ_NO " +
            "     , CREATE_DT " +
            "     , UPDATE_DT " +
            "     , MB_ID " +
            "     , AD_ID " +
            "     , MK_ID " +
            "     , CA_ID " +
            "     , REG_CLNT_ID " +
            "     , REG_IP " +
            "     , STIPULATION_TITLE " +
            "     , STIPULATION_DESC " +
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
            "     , NOW() " +

            "     , #{tbCampaignLandingForm.mbId} " +
            "     , #{tbCampaignLandingForm.adId} " +
            "     , #{tbCampaignLandingForm.mkId} " +
            "     , #{tbCampaignLandingForm.caId} " +

            "     , #{tbCampaignLandingForm.regClntId} " +
            "     , #{tbCampaignLandingForm.regIp} " +
            "     , #{tbCampaignLandingForm.stipulationTitle} " +
            "     , #{tbCampaignLandingForm.stipulationDesc} " +

            "     , #{tbCampaignLandingForm.type01} " +
            "     , #{tbCampaignLandingForm.type02} " +
            "     , #{tbCampaignLandingForm.type03} " +
            "     , #{tbCampaignLandingForm.type04} " +
            "     , #{tbCampaignLandingForm.type05} " +
            "     , #{tbCampaignLandingForm.type06} " +
            "     , #{tbCampaignLandingForm.type07} " +
            "     , #{tbCampaignLandingForm.type08} " +
            "     , #{tbCampaignLandingForm.type09} " +
            "     , #{tbCampaignLandingForm.type10} " +

            "     , #{tbCampaignLandingForm.value01} " +
            "     , #{tbCampaignLandingForm.value02} " +
            "     , #{tbCampaignLandingForm.value03} " +
            "     , #{tbCampaignLandingForm.value04} " +
            "     , #{tbCampaignLandingForm.value05} " +
            "     , #{tbCampaignLandingForm.value06} " +
            "     , #{tbCampaignLandingForm.value07} " +
            "     , #{tbCampaignLandingForm.value08} " +
            "     , #{tbCampaignLandingForm.value09} " +
            "     , #{tbCampaignLandingForm.value10} " +

            "     , #{tbCampaignLandingForm.page01} " +
            "     , #{tbCampaignLandingForm.page02} " +
            "     , #{tbCampaignLandingForm.page03} " +
            "     , #{tbCampaignLandingForm.page04} " +
            "     , #{tbCampaignLandingForm.page05} " +
            "     , #{tbCampaignLandingForm.page06} " +
            "     , #{tbCampaignLandingForm.page07} " +
            "     , #{tbCampaignLandingForm.page08} " +
            "     , #{tbCampaignLandingForm.page09} " +
            "     , #{tbCampaignLandingForm.page10} " +
            ") ")
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long insCampaignLandingForm(@Param("tbCampaignLandingForm") TB_CAMPAIGN_LANDING_FORM tbCampaignLandingForm);

    @Update(" UPDATE " +
            "        CAMPAIGN_LANDING_FORM " +
            " SET" +
            "       UPDATE_DT          = NOW() " +
            "     , MB_ID              = #{tbCampaignLandingForm.mbId} " +
            "     , AD_ID              = #{tbCampaignLandingForm.adId} " +
            "     , MK_ID              = #{tbCampaignLandingForm.mkId} " +
            "     , CA_ID              = #{tbCampaignLandingForm.caId} " +
            "     , REG_CLNT_ID        = #{tbCampaignLandingForm.regClntId} " +
            "     , REG_IP             = #{tbCampaignLandingForm.regIp} " +
            "     , STIPULATION_TITLE  = #{tbCampaignLandingForm.stipulationTitle} " +
            "     , STIPULATION_DESC   = #{tbCampaignLandingForm.stipulationDesc} " +
            "     , TYPE01             = #{tbCampaignLandingForm.type01} " +
            "     , TYPE02             = #{tbCampaignLandingForm.type02} " +
            "     , TYPE03             = #{tbCampaignLandingForm.type03} " +
            "     , TYPE04             = #{tbCampaignLandingForm.type04} " +
            "     , TYPE05             = #{tbCampaignLandingForm.type05} " +
            "     , TYPE06             = #{tbCampaignLandingForm.type06} " +
            "     , TYPE07             = #{tbCampaignLandingForm.type07} " +
            "     , TYPE08             = #{tbCampaignLandingForm.type08} " +
            "     , TYPE09             = #{tbCampaignLandingForm.type09} " +
            "     , TYPE10             = #{tbCampaignLandingForm.type10} " +
            "     , VALUE01            = #{tbCampaignLandingForm.value01} " +
            "     , VALUE02            = #{tbCampaignLandingForm.value02} " +
            "     , VALUE03            = #{tbCampaignLandingForm.value03} " +
            "     , VALUE04            = #{tbCampaignLandingForm.value04} " +
            "     , VALUE05            = #{tbCampaignLandingForm.value05} " +
            "     , VALUE06            = #{tbCampaignLandingForm.value06} " +
            "     , VALUE07            = #{tbCampaignLandingForm.value07} " +
            "     , VALUE08            = #{tbCampaignLandingForm.value08} " +
            "     , VALUE09            = #{tbCampaignLandingForm.value09} " +
            "     , VALUE10            = #{tbCampaignLandingForm.value10} " +
            "     , PAGE01             = #{tbCampaignLandingForm.page01} " +
            "     , PAGE02             = #{tbCampaignLandingForm.page02} " +
            "     , PAGE03             = #{tbCampaignLandingForm.page03} " +
            "     , PAGE04             = #{tbCampaignLandingForm.page04} " +
            "     , PAGE05             = #{tbCampaignLandingForm.page05} " +
            "     , PAGE06             = #{tbCampaignLandingForm.page06} " +
            "     , PAGE07             = #{tbCampaignLandingForm.page07} " +
            "     , PAGE08             = #{tbCampaignLandingForm.page08} " +
            "     , PAGE09             = #{tbCampaignLandingForm.page09} " +
            "     , PAGE10             = #{tbCampaignLandingForm.page10} " +
            " WHERE " +
            "       MB_ID      = #{tbCampaignLandingForm.mbId}" +
            " AND   AD_ID      = #{tbCampaignLandingForm.adId}" +
            " AND   CA_ID      = #{tbCampaignLandingForm.caId}" )
    @Options(useGeneratedKeys = true, keyProperty = "seqNo")
    Long updCampaignLandingForm(@Param("tbCampaignLandingForm") TB_CAMPAIGN_LANDING_FORM tbCampaignLandingForm);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , CREATE_DT " +
            "       , UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , MK_ID " +
            "       , CA_ID " +
            "       , REG_CLNT_ID " +
            "       , REG_IP " +
            "       , STIPULATION_TITLE " +
            "       , STIPULATION_DESC " +
            "       , TYPE01 " +
            "       , TYPE02 " +
            "       , TYPE03 " +
            "       , TYPE04 " +
            "       , TYPE05 " +
            "       , TYPE06 " +
            "       , TYPE07 " +
            "       , TYPE08 " +
            "       , TYPE09 " +
            "       , TYPE10 " +
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
            "       , PAGE01 " +
            "       , PAGE02 " +
            "       , PAGE03 " +
            "       , PAGE04 " +
            "       , PAGE05 " +
            "       , PAGE06 " +
            "       , PAGE07 " +
            "       , PAGE08 " +
            "       , PAGE09 " +
            "       , PAGE10 " +
            " FROM " +
            "       CAMPAIGN_LANDING_FORM " +
            " WHERE " +
            "       MB_ID      = #{mbId} " +
            " AND   AD_ID      = #{adId} " +
            " AND   MK_ID      = #{mkId} " +
            " AND   CA_ID      = #{caId} " )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "createDt" , column = "CREATE_DT"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "regClntId" , column = "REG_CLNT_ID"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "stipulationTitle" , column = "STIPULATION_TITLE"),
            @Result(property = "stipulationDesc" , column = "STIPULATION_DESC"),
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
    Map<String, Object> getCampaignLandingForm(Long mbId, Long adId, Long mkId, Long caId);

    @Select("SELECT " +
            "         SEQ_NO " +
            "       , CREATE_DT " +
            "       , UPDATE_DT " +
            "       , MB_ID " +
            "       , AD_ID " +
            "       , MK_ID " +
            "       , CA_ID " +
            "       , REG_CLNT_ID " +
            "       , REG_IP " +
            "       , STIPULATION_TITLE " +
            "       , STIPULATION_DESC " +
            "       , TYPE01 " +
            "       , TYPE02 " +
            "       , TYPE03 " +
            "       , TYPE04 " +
            "       , TYPE05 " +
            "       , TYPE06 " +
            "       , TYPE07 " +
            "       , TYPE08 " +
            "       , TYPE09 " +
            "       , TYPE10 " +
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
            "       , PAGE01 " +
            "       , PAGE02 " +
            "       , PAGE03 " +
            "       , PAGE04 " +
            "       , PAGE05 " +
            "       , PAGE06 " +
            "       , PAGE07 " +
            "       , PAGE08 " +
            "       , PAGE09 " +
            "       , PAGE10 " +
            " FROM " +
            "       CAMPAIGN_LANDING_FORM " +
            " WHERE " +
            "       MB_ID      = #{mbId} " +
            " AND   AD_ID      = #{adId} " +
            " AND   MK_ID      = #{mkId} " +
            " AND   CA_ID      = #{caId} " )
    @Results({
            @Result(property = "seqNo" , column = "SEQ_NO"),
            @Result(property = "createDt" , column = "CREATE_DT"),
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "mkId" , column = "MK_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "regClntId" , column = "REG_CLNT_ID"),
            @Result(property = "regIp" , column = "REG_IP"),
            @Result(property = "stipulationTitle" , column = "STIPULATION_TITLE"),
            @Result(property = "stipulationDesc" , column = "STIPULATION_DESC"),
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
    TB_CAMPAIGN_LANDING_FORM getCampaignLandingFormForTB(Long mbId, Long adId, Long mkId, Long caId);
}
