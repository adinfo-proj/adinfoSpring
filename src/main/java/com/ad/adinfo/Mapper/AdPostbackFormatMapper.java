package com.ad.adinfo.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdPostbackFormatMapper {
    @Select("SELECT " +
            "        UPDATE_DT " +
            "      , MB_ID " +
            "      , AD_ID " +
            "      , CA_ID " +
            "      , PG_ID " +
            "      , POSTBACK_IO " +
            "      , POSTBACK_URL " +
            "      , SEND_TYPE " +
            "      , SSL_YN " +
            "      , ACCESS_FLAG " +
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
            "FROM " +
            "        AD_POSTBACK_FORMAT " +
            "WHERE " +
            "      MB_ID      = ${mbId} " +
            "AND   AD_ID      = ${adId} " +
            "AND   CA_ID      = ${caId} " )
    @Results({
            @Result(property = "updateDt" , column = "UPDATE_DT"),
            @Result(property = "mbId" , column = "MB_ID"),
            @Result(property = "adId" , column = "AD_ID"),
            @Result(property = "caId" , column = "CA_ID"),
            @Result(property = "pgId" , column = "PG_ID"),
            @Result(property = "postbackIo" , column = "POSTBACK_IO"),
            @Result(property = "postbackUrl" , column = "POSTBACK_URL"),
            @Result(property = "sendType" , column = "SEND_TYPE"),
            @Result(property = "sslYn" , column = "SSL_YN"),
            @Result(property = "accessFlag" , column = "ACCESS_FLAG"),
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
    Map<String, Object> selPostbackFormat(Long mbId, Long adId, Long caId, Long pgId);
}
