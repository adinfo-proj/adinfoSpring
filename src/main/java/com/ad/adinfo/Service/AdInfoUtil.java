package com.ad.adinfo.Service;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import com.ad.adinfo.Mapper.AdUserMaster;
import com.ad.adinfo.Mapper.CpaCampaignData;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdInfoUtil {

    @Autowired
    AdUserMaster adUserMaster;

    @Autowired
    CpaCampaignData cpaCampaignData;

    /*------------------------------------------------------------------------------------------------------------------
     * 로그인 아이디로 광고주ID를 조회한다.
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] COMMON_CODE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    public Long AdClntIdToAdId(String adClntId) {
        AD_USER_MASTER adUserMasterSql;
        adUserMasterSql = adUserMaster.getAdUserMaster(adClntId);
        return adUserMasterSql.getAdId();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 로그인 아이디로 파트너ID를 조회한다.
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] COMMON_CODE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    public Long AdClntIdToPtId(String adClntId) {
        AD_USER_MASTER adUserMasterSql;
        adUserMasterSql = adUserMaster.getAdUserMaster(adClntId);
        return adUserMasterSql.getMkId();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 파트너별 당일 접수된 DB 합계
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_CAMPAIGN_DATA
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음. getCpaCampaignDataTodayInCount
     -----------------------------------------------------------------------------------------------------------------*/
    public Long GetCpaCampaignDataDateInCount(Long ptId, String date) {
        return cpaCampaignData.getCpaCampaignDataTodayInCount(ptId, date);
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 로그인정보를 확인한다.
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_CAMPAIGN_DATA
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음. getCpaCampaignDataTodayInCount
     -----------------------------------------------------------------------------------------------------------------*/
//    public Long GetCpaCampaignDataDateInCount(Long ptId, String date) {
//        return cpaCampaignData.getCpaCampaignDataTodayInCount(ptId, date);
//    }

    public char rndChar () {
        int rnd = (int) (Math.random() * 52); // or use Random or whatever
        char base = (rnd < 26) ? 'A' : 'a';
        return (char) (base + rnd % 26);

    }

//    public void sendSms() {
//        String api_key = "NCS540687B324ADE";
//        String api_secret = "57F643F41F2521E3ADD85FF2B891DE7E";
//        Message coolsms = new Message(api_key, api_secret);
//        HashMap<String, String> params = new HashMap<String, String>();
//
//        params.put("to", "0226680020");
//        params.put("from", "01024068222");
//        params.put("type", "SMS");
//        params.put("text", "인증문자 테스트");
//        params.put("app_version", "test app 1.2");
//
//        try {
//            JSONObject obj = (JSONObject) coolsms.send(params);
//            System.out.println(obj.toString());
//        } catch (CoolsmsException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCode());
//        }
//    }
}