package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.AD_OPERATION_HISTORY;
import com.ad.adinfo.Domain.AD_USER_MASTER;
import com.ad.adinfo.Domain.Member.TokenResponse;
import com.ad.adinfo.Mapper.AdOperationHistoryMapper;
import com.ad.adinfo.Mapper.AdUserMasterMapper;
import com.ad.adinfo.Mapper.AdUtilityMapper;
import com.ad.adinfo.Service.AdInfoUtil;
import com.ad.adinfo.Service.DateCalc;
import com.ad.adinfo.Service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdUserMasterAPI {
    private final DateCalc dateCalc;
    private final AdUserMasterMapper adUserMasterMapper;
    private final AdInfoUtil adInfoUtil;
    private final AdUtilityMapper   adUtilityMapper;
    private final AdOperationHistoryMapper adOperationHistoryMapper;

    @Autowired
    private JwtService jwtService;

    @CrossOrigin
    @RequestMapping(value = "/testGet", method = RequestMethod.GET)
    public String testGet(HttpServletRequest rq) throws Exception {
        System.out.println("Get Request");

        System.out.println(rq.getParameter("page_id"));

        Set<String> keySet = rq.getParameterMap().keySet();
        for(String key: keySet) {
            System.out.println(key + ": " + rq.getParameter(key));
        }

        System.out.println("---------------------------------------------------------------------------------");

        return "testGet Success";
    }

    @CrossOrigin
    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    public String testPost(HttpServletRequest rq) throws Exception {
        System.out.println("Post Request");

        Set<String> keySet = rq.getParameterMap().keySet();
        for(String key: keySet) {
            System.out.println(key + ": " + rq.getParameter(key));
        }

        System.out.println("---------------------------------------------------------------------------------");


        return "testPost Success";
    }
    /*------------------------------------------------------------------------------------------------------------------
     * 회원 정보 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.09
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_USER_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/selectmember", method = RequestMethod.POST)
    public Map<String, Object> selectmember(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("clntId        : [" + params.get("clntId") + "]");

        Map<String, Object> resMap = new HashMap<>();

        AD_USER_MASTER selAdUserMaster = new AD_USER_MASTER();

        selAdUserMaster.setClntId(params.get("clntId").toString());

        //-------------------------------------------------------------------
        // 기 생성된 사용자를 확인한다.
        //-------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUserMasterMapper.getAdUserMaster Start");
        System.out.println("----------------------------------------------------------------------------");

        selAdUserMaster = adUserMasterMapper.getAdUserMaster(params.get("clntId").toString());
        System.out.println("1. [" + params.get("clntId").toString() + "] 아이디가 없습니다.");
        if (selAdUserMaster.getClntId() == null || selAdUserMaster.getClntId() == "") {
            System.out.println("2. [" + params.get("clntId").toString() + "] 아이디가 없습니다.");

            resMap.put("message", "시스템 오류로 관리자에게 연락바랍니다.");
            resMap.put("status", false);
        }
        else {
            resMap.put("message", "정상적으로 회원정보가 조회되었습니다.");

            resMap.put("clntId", selAdUserMaster.getClntId());
            resMap.put("clntNm", selAdUserMaster.getClntNm());
            resMap.put("clntSubsNo", selAdUserMaster.getClntSubsNo());

            resMap.put("status", true);
        }

        System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

        return resMap;
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
        String createdUser = adUserMasterMapper.getAdUserMasterForId(params.get("emailId").toString());
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

                        adUserMasterMapper.getAdUserMasterMaxAdId(creAdUserMaster.getMbId());
                        creAdUserMaster.setAdId(adUserMasterMapper.getAdUserMasterMaxAdId(creAdUserMaster.getMbId()) + 1);
                    }
                    // 광고주
                    else if(params.get("adGradeCd").equals("03")) {

                    }
                    // 마케터
                    else if(params.get("adGradeCd").equals("04")) {
                        // PT_ID 값 신규 생성
                        adUserMasterMapper.getAdUserMasterMaxPtId(creAdUserMaster.getMbId());
                        creAdUserMaster.setMkId(adUserMasterMapper.getAdUserMasterMaxPtId(creAdUserMaster.getMbId()) + 1);

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
                        Long lNewMbId = adUserMasterMapper.getAdUserMasterMaxMbId() + 1;

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
                Long returnMbId = adUserMasterMapper.insAdUserMaster(creAdUserMaster);
                System.out.println("returnMbId : [" + returnMbId + "]");

                TokenResponse loginInfo = new TokenResponse();

                loginInfo.setEmailId(params.get("emailId").toString());

                String token = jwtService.create(loginInfo);

                //System.out.println("token : [" + token + "]");

                resMap.put("status", true);
                resMap.put("emailId", params.get("emailId").toString());
                resMap.put("adGradeCd", params.get("adGradeCd").toString());
                resMap.put("jwtAuthToken", token);

                adInfoUtil.InsAdOperationHistory("O"
                        , creAdUserMaster.getMbId()
                        , params.get("emailId").toString()
                        , "00"
                        , "["+ params.get("emailId").toString() +"] 회원이 신규로 가입신청을 하였습니다."
                );
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
     * 회원 변경
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.09
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] AD_USER_MASTER
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/modifymember", method = RequestMethod.POST)
    public Map<String, Object> modifymember(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("modifymember Func Start...");
        System.out.println("############################################################################");

        Map<String, Object> resMap = new HashMap<>();
        AD_USER_MASTER updAdUserMaster = new AD_USER_MASTER();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("clntId        : [" + params.get("clntId") + "]");
        System.out.println("clntPw        : [" + params.get("clntPw") + "]");
        System.out.println("clntNm        : [" + params.get("clntNm") + "]");
        System.out.println("clntSubsNo    : [" + params.get("clntSubsNo") + "]");

        //-------------------------------------------------------------------
        // 사용자를 확인한다.
        //-------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUserMasterMapper.getAdUserMasterForId Start");
        System.out.println("----------------------------------------------------------------------------");

        updAdUserMaster = adUserMasterMapper.getAdUserMaster(params.get("clntId").toString());
        System.out.println("1. [" + params.get("clntId").toString() + "]");
        if (updAdUserMaster.getClntId() == null || updAdUserMaster.getClntId().equals("")) {
            System.out.println("2. [" + params.get("clntId").toString() + "] 아이디가 없습니다.");

            resMap.put("status", false);
            resMap.put("message", "시스템 오류로 관리자에게 연락바랍니다.");

            return resMap;
        }

        try {
            Long lUpdRow = -1L;
            if (params.get("clntPw") == null || params.get("clntPw").equals("")) {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("  adUserMasterMapper.updAdUserMasterNoPass Start");
                System.out.println("----------------------------------------------------------------------------");

                lUpdRow = adUserMasterMapper.updAdUserMasterNoPass(
                          params.get("clntId").toString()
                        , params.get("clntNm").toString()
                        , params.get("clntSubsNo").toString()
                );
            }
            else
            {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("  adUserMasterMapper.updAdUserMaster Start");
                System.out.println("----------------------------------------------------------------------------");

                lUpdRow = adUserMasterMapper.updAdUserMaster(
                          params.get("clntId").toString()
                        , params.get("clntPw").toString()
                        , params.get("clntNm").toString()
                        , params.get("clntSubsNo").toString()
                );
            }

            if( lUpdRow <= 0) {
                resMap.put("status" , false);
                resMap.put("message", "회원정보 변경이 실패하였습니다.\n\n고객센터 [1533-3757] 번호로 문의주세요.");

                System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

                return resMap;
            }

            System.out.println("rtString : [" + lUpdRow + "]");
        } catch (Exception e) {
            System.out.println("에러 메세지 : ["+ e.toString() +"]");

            resMap.put("status" , false);
            resMap.put("message", "회원정보 변경이 실패하였습니다.\n\n고객센터 [1533-3757] 번호로 문의주세요.");

            System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

            return resMap;
        }

        resMap.put("status" , true);
        resMap.put("message", "정상적으로 회원정보가 변경되었습니다.");

        System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

        adInfoUtil.InsAdOperationHistory("O"
                , updAdUserMaster.getMbId()
                , params.get("clntId").toString()
                , "00"
                , "["+ params.get("clntId").toString() +"] 회원 정보를 변경하였습니다."
        );

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
    @RequestMapping(value = "/FindUserId", method = RequestMethod.POST)
    public Map<String, Object> FindUserId(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("FindUserId Func Start...");
        System.out.println("############################################################################");

        String  rtString = null;
        Map<String, Object> resMap = new HashMap<>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("userName     : [" + params.get("userName") + "]");
        System.out.println("clntSubsNo   : [" + params.get("clntSubsNo") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUserMasterMapper.getAdUserMasterFindId Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            rtString = adUserMasterMapper.getAdUserMasterFindId(params.get("userName").toString(), params.get("clntSubsNo").toString());
            if( rtString == null) {
                resMap.put("status" , false);
                resMap.put("message", "등록된 사용자 정보가 없습니다.");

                System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

                return resMap;
            }
            System.out.println("rtString : [" + rtString + "]");
        } catch (Exception e) {
            resMap.put("status" , false);
            resMap.put("message", "등록된 사용자 정보가 없습니다.");

            System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

            return resMap;
        }

        resMap.put("status" , true);
        resMap.put("message", rtString);

        System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

        adInfoUtil.InsAdOperationHistory("O"
                , 0L
                , params.get("userName").toString()
                , "00"
                , "["+ params.get("userName").toString() +"] 사용자가 ID 찾기를 요청하였습니다."
        );

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
    @RequestMapping(value = "/UpdateUserPw", method = RequestMethod.POST)
    public Map<String, Object> UpdateUserPw(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("UpdateUserPw Func Start...");
        System.out.println("############################################################################");

        Long rtString = -1L;
        String nanPw = "";
        String sSmsComment = "";
        Map<String, Object> resMap = new HashMap<>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("userId     : [" + params.get("userId") + "]");
        System.out.println("clntSubsNo : [" + params.get("clntSubsNo") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUserMasterMapper.setAdUserMasterFindPw Start");
        System.out.println("----------------------------------------------------------------------------");

        AD_USER_MASTER adUserMaster = new AD_USER_MASTER();
        try {
            adUserMaster = adUserMasterMapper.getAdUserMaster(params.get("userId").toString());

            if(adUserMaster.getClntId().equals("") ||
               adUserMaster.getClntId() == null    )
            {
                resMap.put("status" , false);
                resMap.put("message", "요청하신 회원 ID가 존재하지 않습니다.");

                System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

                return resMap;
            }

            if(!params.get("clntSubsNo").equals(adUserMaster.getClntSubsNo())) {
                resMap.put("status" , false);
                resMap.put("message", "회원님의 아이디 또는 휴대전화번호가 일치하지 않습니다.");

                System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

                return resMap;
            }
        } catch (Exception e) {
            resMap.put("status" , false);
            resMap.put("message", "요청하신 회원 ID가 존재하지 않습니다.");

            System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

            return resMap;
        }

        //---------------------------------------------------------------------------------------------------------
        // 변경할 비밀번호를 난수로 설정
        //---------------------------------------------------------------------------------------------------------
        for(int i = 0 ; i < 10; i++) {
            nanPw = nanPw + Character.toString(adInfoUtil.RandamChar());
        }
        System.out.println(nanPw);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUserMasterMapper.setAdUserMasterFindPw Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            rtString = adUserMasterMapper.setAdUserMasterFindPw(nanPw, params.get("userId").toString());
            if( rtString <= 0) {
                resMap.put("status" , false);
                resMap.put("message", "비밀번호 초기화에 실패하였습니다.");

                System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

                return resMap;
            }
            System.out.println("rtString : [" + rtString + "]");
        } catch (Exception e) {
            resMap.put("status" , false);
            resMap.put("message", "비밀번호 초기화에 실패하였습니다.");

            System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

            return resMap;
        }

        //---------------------------------------------------------------------------------------------------------
        // SOLAPI로 메세지를 전송한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUtilityMapper.insertSms Start");
        System.out.println("----------------------------------------------------------------------------");

        sSmsComment = "[디비마스터] 임시비밀번호는 [" + nanPw + "] 입니다.";

        rtString = -1L;
        rtString = adUtilityMapper.insertSms(
                  adUserMaster.getClntSubsNo()
                , "15333757"
                , sSmsComment
        );

        resMap.put("status" , true);
        resMap.put("message", "");

        System.out.println("리턴 메세지 : ["+ resMap.toString() +"]");

        adInfoUtil.InsAdOperationHistory("O"
                , adUserMaster.getMbId()
                , params.get("userId").toString()
                , "00"
                , "["+ params.get("userId").toString() +"] 사용자가 비밀번호를 재설정 하였습니다."
        );

        return resMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * SMS 인증 처리
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] SMS_AUTH, msg.msg
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "InsertAuthSms", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> InsertAuthSms(NativeWebRequest nativeWebRequest, HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("InsertAuthSms Func Start...");
        System.out.println("############################################################################");

        Long    lResult  = -1L;
        String  sComment = "";
        String  sRandom  = "";
        String  clientIp = "";

        Map<String, Object> resMap = new HashMap<>();
        Random  rd = new Random();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");

        System.out.println("to   : [" + rq.getParameter("to") + "]");
        System.out.println("from : [" + rq.getParameter("from") + "]");

        //---------------------------------------------------------------------------------------------------------
        // 처리자의 아이피를 변경한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  IP 수집 Start");
        System.out.println("----------------------------------------------------------------------------");

        try {
            HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

            clientIp = request.getHeader("X-Forwarded-For");
            if (StringUtils.isEmpty(clientIp)|| "unknown".equalsIgnoreCase(clientIp)) {
                //Proxy 서버인 경우
                clientIp = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                //Weblogic 서버인 경우
                clientIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getRemoteAddr();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        //---------------------------------------------------------------------------------------------------------
        // 인증번호를 생성한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  인증번호를 생성");
        System.out.println("----------------------------------------------------------------------------");

        for(int i = 0 ; i < 6 ; i++)
            sRandom += rd.nextInt(9) + 1;

        System.out.println("생성된 인증번호 : ["+ sRandom +"]"); //로또번호 출력

        //---------------------------------------------------------------------------------------------------------
        // 인증번호 비교 테이블에 데이터를 생성한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUtilityMapper.insertSmsAuth Start");
        System.out.println("----------------------------------------------------------------------------");
        lResult = adUtilityMapper.insertSmsAuth(
                  rq.getParameter("to")
                , sRandom
                , clientIp
                , "D"
        );

        sComment = "[" + sRandom + "] 본인확인 인증번호를 입력하세요! [디비마스터]";
        System.out.println("전송할 메세지 : ["+ sComment +"]"); //로또번호 출력

        //---------------------------------------------------------------------------------------------------------
        // SOLAPI로 메세지를 전송한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUtilityMapper.insertSms Start");
        System.out.println("----------------------------------------------------------------------------");
        lResult = -1L;
        lResult = adUtilityMapper.insertSms(
                  rq.getParameter("to")
                , rq.getParameter("from")
                , sComment
        );

        //---------------------------------------------------------------------------------------------------------
        // 결과를 리턴한다.
        //---------------------------------------------------------------------------------------------------------
        resMap.put("result", 0);
        resMap.put("comment", sComment);
        resMap.put("no", sRandom);

        System.out.println("리턴 메세지 : ["+ resMap.toString() +"]"); //로또번호 출력

        return resMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * SMS 인증 확인
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] msg.msg
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "DiffAuthSms", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> DiffAuthSms(NativeWebRequest nativeWebRequest, HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("DiffAuthSms Func Start...");
        System.out.println("############################################################################");

        Long    lResult  = -1L;
        String  sComment = "";
        String  sResult  = "";

        Map<String, Object> resMap = new HashMap<>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("smsNo   : [" + rq.getParameter("smsNo") + "]");
        System.out.println("smsCode : [" + rq.getParameter("smsCode") + "]");

        //---------------------------------------------------------------------------------------------------------
        // 인증번호를 비교한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUtilityMapper.diffSmsAuthCheck Start");
        System.out.println("----------------------------------------------------------------------------");
        sResult = adUtilityMapper.diffSmsAuthCheck(rq.getParameter("smsNo"));
        System.out.println("sResult : [" + sResult + "]");
        if( sResult.equals("-1")) {
            resMap.put("result", -1);
            resMap.put("comment", "본인 확인을 다시 진행해 주세요.");
            System.out.println("처리 메세지 : [" + "본인 확인을 다시 진행해 주세요" + "]");
        }
        else if( Long.parseLong(sResult) > 120) {
            resMap.put("result", -2);
            resMap.put("comment", "처리 시간이 초과되었습니다.");
            System.out.println("처리 메세지 : [" + "처리 시간이 초과되었습니다" + "]");
        }
        else {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  adUtilityMapper.diffSmsAuthCode Start");
            System.out.println("----------------------------------------------------------------------------");
            sResult = adUtilityMapper.diffSmsAuthCode(rq.getParameter("smsNo"));
            System.out.println("입력코드 : [" + rq.getParameter("smsCode").toString() + "]");
            System.out.println("조회코드 : [" + sResult + "]");
            if(sResult.equals(rq.getParameter("smsCode").toString())) {
                resMap.put("result", 0);
                resMap.put("comment", "정상적으로 처리되었습니다.");
                System.out.println("처리 메세지 : [" + "정상적으로 처리되었습니다" + "]");
            }
            else {
                resMap.put("result", -3);
                resMap.put("comment", "입력하신 인증번호가 잘못되었습니다.");
                System.out.println("처리 메세지 : [" + "입력하신 인증번호가 잘못되었습니다" + "]");
            }
        }

        System.out.println("리턴 메세지 : ["+ resMap.toString() +"]"); //로또번호 출력

        return resMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/GetClntSubsNo", method = RequestMethod.GET)
    public String GetClntSubsNo(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetClntSubsNo Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(" rq : [" + rq.toString() + "]");

        AD_USER_MASTER adUserMaster = new AD_USER_MASTER();
        adUserMaster = adUserMasterMapper.getAdUserMaster(rq.getParameter("clntId").toString());

        System.out.println("리턴 메세지 : ["+ adUserMaster.toString() +"]"); //로또번호 출력

        return adUserMaster.getClntSubsNo();
    }
}
