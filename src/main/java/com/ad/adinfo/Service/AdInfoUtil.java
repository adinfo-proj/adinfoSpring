package com.ad.adinfo.Service;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import com.ad.adinfo.Mapper.AdUserMaster;
import com.ad.adinfo.Mapper.CpaCampaignData;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    /*------------------------------------------------------------------------------------------------------------------
     * 랜덤한 문자열을 만든다.
     *   - 랜딩페이지가 생성될때 임의 URL을 만들기위해 사용함.
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    public char RandamChar () {
        int rnd = (int)(Math.random() * 52); // or use Random or whatever
        char base = (rnd < 26) ? 'A' : 'a';
        return (char)(base + rnd % 26);
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 서버에서 폴더를 복사
     *   - 랜딩페이지가 생성될때 기본적인 폴더와 파일을 복사.
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.27
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    public static void FolderCopy(File sourceF, File targetF){
        File[] target_file = sourceF.listFiles();
        for (File file : target_file) {
            File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
            if(file.isDirectory()){
                temp.mkdir();
                FolderCopy(file, temp);
            } else {
                FileInputStream fis = null;
                FileOutputStream fos = null;
                try {
                    fis = new FileInputStream(file);
                    fos = new FileOutputStream(temp) ;
                    byte[] b = new byte[4096];
                    int cnt = 0;
                    while((cnt=fis.read(b)) != -1){
                        fos.write(b, 0, cnt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    try {
                        fis.close();
                        fos.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
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