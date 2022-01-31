package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import com.ad.adinfo.Domain.Member.TokenResponse;
import com.ad.adinfo.Mapper.AdUserMaster;
import com.ad.adinfo.Service.AdInfoUtil;
import com.ad.adinfo.Service.DateCalc;
import com.ad.adinfo.Service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdUserMasterAPI {
    private final DateCalc dateCalc;
    private final AdUserMaster adUserMaster;
    private final AdInfoUtil adInfoUtil;

    @Autowired
    private JwtService jwtService;

    @CrossOrigin
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String Test(HttpServletRequest rq) throws Exception {

        String ptId = "";
        //adInfoUtil.sendSms();

        for(int i = 0 ; i < 10; i++) {
            ptId = ptId + Character.toString(adInfoUtil.RandamChar());
        }
        System.out.println(ptId);
        return "";
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 회원 생성
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.14
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] AD_USER_MASTER
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/addmember", method = RequestMethod.POST)
    public Map<String, Object> CreateUser(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        Map<String, Object> resMap = new HashMap<>();

        AD_USER_MASTER creAdUserMaster = new AD_USER_MASTER();

        //-------------------------------------------------------------------
        // 기 생성된 사용자를 확인한다.
        //-------------------------------------------------------------------
        String createdUser = adUserMaster.getAdUserMasterForId(params.get("emailId").toString());
        System.out.println("1. [" + createdUser + "] 아이디가 없습니다.");
        if(createdUser == null || createdUser == "") {
            System.out.println("2. [" + params.get("emailId").toString() + "] 아이디가 없습니다.");

            try {
                creAdUserMaster.setClntId(params.get("emailId").toString());
                creAdUserMaster.setClntNm(params.get("userName").toString());
                creAdUserMaster.setClntPw(params.get("userPass").toString());
                creAdUserMaster.setClntSubsNo(params.get("clntSubsNo").toString().replaceAll("-", ""));
                creAdUserMaster.setActvCd("1");
                creAdUserMaster.setGradeCd(params.get("adGradeCd").toString());
                creAdUserMaster.setRegDt(dateCalc.DateInterval(0));
                creAdUserMaster.setAbnDt("29991231");
                creAdUserMaster.setSrtDt(dateCalc.DateInterval(0));
                creAdUserMaster.setExpDt("29991231");
            } catch(Exception e) {
                System.out.println("[" + e.getMessage() + "]");
                resMap.put("status" , false);
                resMap.put("message", "시스템 오류로 관리자에게 연락바랍니다.");
                return resMap;
            }

            if(params.get("adGradeCd") != null) {
                if(params.get("adGradeCd").equals("")) {
                    resMap.put("status" , false);
                    resMap.put("message", "시스템 오류로 관리자에게 연락바랍니다.");
                    return resMap;
                }
                else {
                    // 대행사
                    if(params.get("adGradeCd").equals("02")) {
                        // AD_ID 값 신규 생성
                        creAdUserMaster.setMbId(Long.parseLong(params.get("mbId").toString()));

                        adUserMaster.getAdUserMasterMaxAdId(creAdUserMaster.getMbId());
                        creAdUserMaster.setAdId(adUserMaster.getAdUserMasterMaxAdId(creAdUserMaster.getMbId()) + 1);
                    }
                    // 광고주
                    else if(params.get("adGradeCd").equals("03")) {

                    }
                    // 마케터
                    else if(params.get("adGradeCd").equals("04")) {
                        // PT_ID 값 신규 생성
                        adUserMaster.getAdUserMasterMaxPtId(creAdUserMaster.getMbId());
                        creAdUserMaster.setMkId(adUserMaster.getAdUserMasterMaxPtId(creAdUserMaster.getMbId()) + 1);

                        // PT_CD 값 신규 생성
                        String ptCd = "";

                        for(int i = 0 ; i < 10; i++) {
                            ptCd = ptCd + Character.toString(adInfoUtil.RandamChar());
                        }
                        System.out.println(ptCd);
                        creAdUserMaster.setMkCd(ptCd);
                    }
                    // DB 마스터
                    else if(params.get("adGradeCd").equals("05")) {
                        // DB마스터의 경우 MB_ID, AD_ID, MK_ID가 모두 동일함.
                        // MK_CD값은 신규로 생성해서 사용함은 동일하다.
                        // MB_ID를 새로 생성한다.
                        Long lNewMbId = adUserMaster.getAdUserMasterMaxMbId() + 1;

                        creAdUserMaster.setMbId(lNewMbId);
                        creAdUserMaster.setAdId(lNewMbId);
                        creAdUserMaster.setMkId(lNewMbId);

                        // PT_CD 값 신규 생성
                        String ptCd = "";

                        for(int i = 0 ; i < 10; i++) {
                            ptCd = ptCd + Character.toString(adInfoUtil.RandamChar());
                        }
                        System.out.println(ptCd);
                        creAdUserMaster.setMkCd(ptCd);
                    }
                    else {
                        // 관리자로 등록이므로 이는 별개로 처리
                    }
                }
            }

            try {
                Long returnMbId = adUserMaster.insAdUserMaster(creAdUserMaster);
                System.out.println("returnMbId : [" + returnMbId + "]");

                TokenResponse loginInfo = new TokenResponse();

                loginInfo.setEmailId(params.get("emailId").toString());

                String token = jwtService.create(loginInfo);

                //System.out.println("token : [" + token + "]");

                resMap.put("status", true);
                resMap.put("emailId", params.get("emailId").toString());
                resMap.put("adGradeCd", params.get("adGradeCd").toString());
                resMap.put("jwtAuthToken", token);
            } catch (Exception e) {
                System.out.println("Exception : [" + e.getMessage() + "]");
                resMap.put("status" , false);
                resMap.put("message", "시스템 오류로 관리자에게 연락바랍니다.");
            }
        }
        else {
            resMap.put("status" , false);
            resMap.put("message", "이미 등록된 사용자가 있습니다.");
            System.out.println("Fail Process : [이미 등록된 사용자가 있습니다]");
        }

        return resMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 아이디 찾기
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.14
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] AD_USER_MASTER
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/findid", method = RequestMethod.POST)
    public Map<String, Object> FindUserId(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        Map<String, Object> resMap = new HashMap<>();

        String  rtString = null;

        try {
            rtString = adUserMaster.getAdUserMasterFindId(20000L, params.get("userName").toString(), params.get("clntSubsNo").toString());
            if( rtString == null) {
                resMap.put("status" , false);
                resMap.put("message", "등록된 사용자 정보가 없습니다.");

                return resMap;
            }
            System.out.println("rtString : [" + rtString + "]");
        } catch (Exception e) {
            resMap.put("status" , false);
            resMap.put("message", "등록된 사용자 정보가 없습니다.");

            return resMap;
        }

        resMap.put("status" , true);
        resMap.put("message", rtString);

        return resMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 비밀번호 찾기
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.14
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] AD_USER_MASTER
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/findpw", method = RequestMethod.POST)
    public Map<String, Object> UpdateUserPw(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        Map<String, Object> resMap = new HashMap<>();

        Long rtString = -1L;

        // 변경할 비밀번호를 난수로 설정
        String nanPw = "";

        for(int i = 0 ; i < 10; i++) {
            nanPw = nanPw + Character.toString(adInfoUtil.RandamChar());
        }
        System.out.println(nanPw);






        // Long mbId, String nanPw, String userId

        try {
            rtString = adUserMaster.setAdUserMasterFindPw(20000L, nanPw, params.get("userId").toString());
            if( rtString <= 0) {
                resMap.put("status" , false);
                resMap.put("message", "비밀번호 초기화에 실패하였습니다.");

                return resMap;
            }
            System.out.println("rtString : [" + rtString + "]");
        } catch (Exception e) {
            resMap.put("status" , false);
            resMap.put("message", "비밀번호 초기화에 실패하였습니다.");

            return resMap;
        }

        resMap.put("status" , true);
        resMap.put("message", "");

        return resMap;
    }
}
