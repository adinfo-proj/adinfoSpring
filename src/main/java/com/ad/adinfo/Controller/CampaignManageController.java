package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.TB_CAMPAIGN_MASTER;
import com.ad.adinfo.Domain.TB_CAMPAIGN_LANDING_FORM;
import com.ad.adinfo.Mapper.*;
import com.ad.adinfo.Service.AdInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class CampaignManageController {
    private final CampaignMasterMapper campaignMasterMapper;
    private final AdInfoUtil        adInfoUtil;
    private final DataCenterMapper  dataCenterMapper;
    private final AdUtilityMapper   adUtilityMapper;
    private final AdOperationHistoryMapper adOperationHistoryMapper;
    private final CampaignLandingFormMapper campaignLandingFormMapper;

    @Autowired
    private PlatformTransactionManager trxManager;


    /*------------------------------------------------------------------------------------------------------------------
     * 신규 캠페인 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.13
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] CPA_CAMPAIGN_MASTER
     *         [R] AD_USER_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/newcampaign", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> insCampaignMaster(
            NativeWebRequest nativeWebRequest,
            @RequestHeader Map<String, Object> rHeader,
            @RequestPart (value = "formObj") List<Map<String, Object>>  formRq,
            @RequestPart (value = "dataObj")      Map<String, Object>   params) throws Exception
    {
        System.out.println("\n############################################################################");
        System.out.println("newcampaign Func Start...");
        System.out.println("############################################################################");

        // 트랜잭션 시작
//        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        //---------------------------------------------------------------------------------------------------------
        // 변수 설정 영역 Start
        //---------------------------------------------------------------------------------------------------------
        Long newCaId                            = 0L;
        TB_CAMPAIGN_MASTER cpaCampaignMaster    = new TB_CAMPAIGN_MASTER();
        Map<String, Object> resultMap           = new HashMap<String, Object>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("헤더            : [" + rHeader + "]");
        System.out.println("dataObj 파라메터 : [" + params + "]");
        System.out.println("formObj 파라메터 : [" + formRq + "]");

        //---------------------------------------------------------------------------------------------------------
        // 비지니스 로직 Start
        //---------------------------------------------------------------------------------------------------------
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        String clientIp = request.getHeader("X-Forwarded-For");
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

        try {
            //-------------------------------------------------------------------
            // DB생성을 위해 변수를 대입한다.
            //   - DB마스터는 회원사ID와 대행사ID가 모두 같다.
            //-------------------------------------------------------------------
            cpaCampaignMaster.setMbId(Long.parseLong(params.get("mbId").toString()));
            cpaCampaignMaster.setAdId(Long.parseLong(params.get("adId").toString()));

            // 로그인 아이디로 ID값을 조회한다.
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  adInfoUtil.AdClntIdToAdId Start");
            System.out.println("----------------------------------------------------------------------------");
            cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId(params.get("clntId").toString()));

            // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
            //   - MB_ID와 AD_ID 기준으로 CA_ID번호를 산출한다.
            try {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("  campaignMasterMapper.getCampaignMasterMaxCaId Start");
                System.out.println("----------------------------------------------------------------------------");

                newCaId = campaignMasterMapper.getCampaignMasterMaxCaId(cpaCampaignMaster.getMbId(), cpaCampaignMaster.getAdId());
                System.out.println("Max CA_ID 1 : [" + newCaId + "]");
                newCaId = (newCaId == null) ? 10000L : newCaId + 1L;
            } catch (Exception e) {
                System.out.println("campaignMasterMapper.getCampaignMasterMaxCaId Fail : [" + e + "]");
            }
            cpaCampaignMaster.setCaId(newCaId);

            // 작업자 ID
            cpaCampaignMaster.setOperId(params.get("clntId").toString());

            // 캠페인 시작 일자/시간
            cpaCampaignMaster.setSrtDt(params.get("adSrtDt").toString().replaceAll("-", ""));
            cpaCampaignMaster.setSrtTm(params.get("adSrtTm").toString().replaceAll(":", ""));

            // 캠페인 종료 일자/시간
            cpaCampaignMaster.setEndDt("29991231");
            cpaCampaignMaster.setEndTm("235959");

            // 캠페인 종류(COMMON_CODE:AD_KIND)
            // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
            cpaCampaignMaster.setCampaignKind(params.get("adPurpose").toString());

            // 광고 지역(전국/서울/경기/강원/충남/충북/전북/전남/경북/경남/제주/기타)
            cpaCampaignMaster.setCampaignArea("00");

            // 캠페인 상태(COMMON_CODE:CAMPAIGN_STATUS)
            cpaCampaignMaster.setStatus(params.get("status").toString());

            // 캠페인명
            Long camNameCount = 0L;
            try {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("  campaignMasterMapper.getCampaignMasterByName Start");
                System.out.println("----------------------------------------------------------------------------");

                camNameCount = campaignMasterMapper.getCampaignMasterByName(
                          Long.parseLong(params.get("mbId").toString())
                        , Long.parseLong(params.get("adId").toString())
                        , params.get("adName").toString());

                System.out.println(camNameCount);

                if(camNameCount > 0) {
                    resultMap.put("result", false);
                    resultMap.put("message", "이미 등록한 캠페인명이 있습니다.");

//                    trxManager.rollback(trxStatus);
                    return resultMap;
                }
            } catch (Exception e) {
                System.out.println("campaignMaster.getCampaignMasterByName Fail : [" + e + "]");
            }

            // 광고주명
            cpaCampaignMaster.setAdName(params.get("adNameAd").toString());

            // 캠페인명
            cpaCampaignMaster.setName(params.get("adName").toString());

            // 캠페인 광고구분(COMMON_CODE:CAMPAIGN_TP)
            cpaCampaignMaster.setTp("D");

            // 광고 대분류(COMMON_CODE:CAMPAIGN_TOP_GROUP)
            cpaCampaignMaster.setTopKind(params.get("adTopKind").toString());

            // 광고 중분류(COMMON_CODE:CAMPAIGN_MIDDLE_GROUP)
            cpaCampaignMaster.setMiddleKind(params.get("adMiddleKind").toString());

            // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
            cpaCampaignMaster.setPurpose(params.get("adPurpose").toString());

            // 광고주 단가
            if ((params.get("adPrice") == null) || params.get("adPrice").equals(""))
                cpaCampaignMaster.setPrice(0L);
            else
                cpaCampaignMaster.setPrice(Long.parseLong(params.get("adPrice").toString().replaceAll(",", "")));

            // 마케터 단가
            if ((params.get("adMaketerPrice") == null) || params.get("adMaketerPrice").equals(""))
                cpaCampaignMaster.setMarketerPrice(0L);
            else
                cpaCampaignMaster.setMarketerPrice(Long.parseLong(params.get("adMaketerPrice").toString().replaceAll(",", "")));

            // 등록자 IP
            cpaCampaignMaster.setRegIp(clientIp);

            // 캠페인 상세설명
            cpaCampaignMaster.setComment(params.get("adComment").toString());

            // 고객이 정보입력 항목
            //cpaCampaignMaster.setAskList(askList);

            // 캠페인 DB 등록시 SMS 수신여부
            if ((params.get("smsYn") == null) || params.get("smsYn").equals(""))
                cpaCampaignMaster.setSmsYn("N");
            else {
                cpaCampaignMaster.setSmsYn(params.get("smsYn").toString());

                // SMS 수신받을 휴대폰번호
                cpaCampaignMaster.setSmsNo(params.get("smsNo").toString().replaceAll("-", ""));
            }
        } catch (Exception e) {
            System.out.println("campaignMaster.insCampaignMaster Fail : [" + e + "]");
        }

        System.out.println("Parsing Data : [" + cpaCampaignMaster + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  랜딩페이지 폼 규격 생성.");
        System.out.println("----------------------------------------------------------------------------");
        //-------------------------------------------------------------------
        // 캠페인별 랜딩페이지 폼 규격 생성.
        //-------------------------------------------------------------------

        System.out.println("----------------------------------------------------------------------------");
        System.out.println(formRq.size());
        System.out.println("----------------------------------------------------------------------------");

        TB_CAMPAIGN_LANDING_FORM tbCampaignLandingForm = new TB_CAMPAIGN_LANDING_FORM();

        tbCampaignLandingForm.setMbId            (Long.parseLong(params.get("mbId").toString()));
        tbCampaignLandingForm.setAdId            (Long.parseLong(params.get("adId").toString()));
        tbCampaignLandingForm.setMkId            (Long.parseLong(params.get("mkId").toString()));
        tbCampaignLandingForm.setCaId            (newCaId);
        tbCampaignLandingForm.setRegClntId       (params.get("clntId").toString());
        tbCampaignLandingForm.setRegIp           (clientIp);
        tbCampaignLandingForm.setStipulationTitle(params.get("stipulationTitle").toString());
        tbCampaignLandingForm.setStipulationDesc (params.get("stipulationDesc").toString());

        String sAskList = "";

        for(int i = 0 ; i < formRq.size() ; i++) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(formRq.get(i).toString());
            System.out.println("----------------------------------------------------------------------------");

            if(formRq.get(i).get("value").equals("") || formRq.get(i).get("value") == null) {
                if(i == 9)
                    sAskList += "-";
                else
                    sAskList += "-,";

//                continue;
            }
            else {
                if(i == 9)
                    sAskList += formRq.get(i).get("value").toString();
                else
                    sAskList += formRq.get(i).get("value").toString() + ",";
            }

            switch(i) {
                case 0 :
                    tbCampaignLandingForm.setType01 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue01(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage01 (formRq.get(i).get("desc").toString());
                    break;
                case 1 :
                    tbCampaignLandingForm.setType02 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue02(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage02 (formRq.get(i).get("desc").toString());
                    break;
                case 2 :
                    tbCampaignLandingForm.setType03 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue03(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage03 (formRq.get(i).get("desc").toString());
                    break;
                case 3 :
                    tbCampaignLandingForm.setType04 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue04(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage04 (formRq.get(i).get("desc").toString());
                    break;
                case 4 :
                    tbCampaignLandingForm.setType05 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue05(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage05 (formRq.get(i).get("desc").toString());
                    break;
                case 5 :
                    tbCampaignLandingForm.setType06 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue06(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage06 (formRq.get(i).get("desc").toString());
                    break;
                case 6 :
                    tbCampaignLandingForm.setType07 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue07(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage07 (formRq.get(i).get("desc").toString());
                    break;
                case 7 :
                    tbCampaignLandingForm.setType08 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue08(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage08 (formRq.get(i).get("desc").toString());
                    break;
                case 8 :
                    tbCampaignLandingForm.setType09 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue09(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage09 (formRq.get(i).get("desc").toString());
                    break;
                case 9 :
                    tbCampaignLandingForm.setType10 (formRq.get(i).get("types").toString());
                    tbCampaignLandingForm.setValue10(formRq.get(i).get("value").toString());
                    tbCampaignLandingForm.setPage10 (formRq.get(i).get("desc").toString());
                    break;
            }

            System.out.println("Step 02");

            System.out.println("----------------------------------------------------------------------------");
            System.out.println(tbCampaignLandingForm.toString());
            System.out.println("----------------------------------------------------------------------------");
        }

        System.out.println(sAskList);

        //-------------------------------------------------------------------
        // 캠페인 마스터에 데이터를 생성한다.
        //-------------------------------------------------------------------
        try {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.insCampaignMaster Start");
            System.out.println("----------------------------------------------------------------------------");

            cpaCampaignMaster.setAskList(sAskList);

            campaignMasterMapper.insCampaignMaster(cpaCampaignMaster);
        } catch (Exception e) {
            System.out.println("campaignMasterMapper.insCampaignMaster Fail : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "캠페인 등록이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

//            trxManager.rollback(trxStatus);
            return resultMap;
        }

        //-------------------------------------------------------------------
        // 캠페인 폼 데이터를 생성한다.
        //-------------------------------------------------------------------
        try {
            Long lResultRow = campaignLandingFormMapper.insCampaignLandingForm(tbCampaignLandingForm);
            if(lResultRow > 0) {
                System.out.println("생성 결과 : ["+ lResultRow +"]");
            }
        } catch (Exception e) {
            System.out.println("campaignMasterMapper.insCampaignLandingForm Fail : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "캠페인 등록이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

//            trxManager.rollback(trxStatus);
            return resultMap;
        }

        //-------------------------------------------------------------------
        // SEQ로 생성된 캠페인 아이디를 리턴한다.
        //-------------------------------------------------------------------
        resultMap.put("result", true);
        resultMap.put("message", "캠페인 등록이 정상적으로 처리되었습니다.");

        System.out.println("리턴 메세지 : ["+ resultMap.toString() +"]");

//        trxManager.commit(trxStatus);
        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 정보 변경
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.13
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] CPA_CAMPAIGN_MASTER
     *         [R] AD_USER_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/upcampaign", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> updCampaignMaster(
            NativeWebRequest nativeWebRequest,
            @RequestPart (value = "formObj") List<Map<String, Object>>  formRq,
            @RequestPart (value = "dataObj")      Map<String, Object>   params) throws Exception
    {
        System.out.println("\n\n############################################################################");
        System.out.println("updCampaignMaster Func Start...");
        System.out.println("############################################################################");

        // 트랜잭션 시작
//        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("캠페인정보 : [" + params.toString() + "]");
        System.out.println("폼 설정   : [" + formRq.toString() + "]");

        //---------------------------------------------------------------------------------------------------------
        // 변수 설정 영역 Start
        //---------------------------------------------------------------------------------------------------------
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_CAMPAIGN_MASTER orgCpaCampaignMaster = new TB_CAMPAIGN_MASTER();

        try {
            //---------------------------------------------------------------------------------------------------------
            // 처리자의 아이피를 변경한다.
            //---------------------------------------------------------------------------------------------------------
            HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

            String clientIp = request.getHeader("X-Forwarded-For");
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

            //---------------------------------------------------------------------------------------------------------
            // 이전 정보를 히스토리에 생성한다.
            //---------------------------------------------------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdCa Start");
            System.out.println("----------------------------------------------------------------------------");

            orgCpaCampaignMaster = campaignMasterMapper.getCampaignMasterForMbAdCa(
                      Long.parseLong(params.get("mbId").toString())
                    , Long.parseLong(params.get("adId").toString())
                    , Long.parseLong(params.get("caId").toString())
            );

            orgCpaCampaignMaster.setSrtDt(orgCpaCampaignMaster.getSrtDt().replaceAll("-", ""));
            orgCpaCampaignMaster.setSrtTm(orgCpaCampaignMaster.getSrtTm().replaceAll(":", ""));

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.insCampaignMasterHistory Start");
            System.out.println("----------------------------------------------------------------------------");

            campaignMasterMapper.insCampaignMasterHistory(orgCpaCampaignMaster);

            //---------------------------------------------------------------------------------------------------------
            // 변경된 정보를 갱신 후 업데이트 한다.
            //---------------------------------------------------------------------------------------------------------
            orgCpaCampaignMaster.setRegIp              (clientIp);
            orgCpaCampaignMaster.setCaId(Long.parseLong(params.get("caId").toString()));
            orgCpaCampaignMaster.setCampaignKind       (params.get("campaignKind").toString());
            orgCpaCampaignMaster.setTopKind            (params.get("topKind").toString());
            orgCpaCampaignMaster.setMiddleKind         (params.get("middleKind").toString());
            orgCpaCampaignMaster.setName               (params.get("name").toString());
            orgCpaCampaignMaster.setAdName             (params.get("adName").toString());
            orgCpaCampaignMaster.setStatus             (params.get("status").toString());
            orgCpaCampaignMaster.setComment            (params.get("comment").toString());

            // 광고주 단가
            if ((params.get("price") == null) || params.get("price").equals(""))
                orgCpaCampaignMaster.setPrice(0L);
            else
                orgCpaCampaignMaster.setPrice(Long.parseLong(params.get("price").toString().replaceAll(",", "")));

            // 마케터 단가
            if ((params.get("marketerPrice") == null) || params.get("marketerPrice").equals(""))
                orgCpaCampaignMaster.setMarketerPrice(0L);
            else
                orgCpaCampaignMaster.setMarketerPrice(Long.parseLong(params.get("marketerPrice").toString().replaceAll(",", "")));

            // SMS 수신안함인경우 기존 SMS 번호를 제거한다.
            if(params.get("smsYn").toString().equals("N"))
                orgCpaCampaignMaster.setSmsNo("");
            else
                orgCpaCampaignMaster.setSmsNo(params.get("smsNo").toString().replaceAll("-", ""));

            //---------------------------------------------------------------------------------------------------------
            // 캠페인별 랜딩페이지 폼 규격 갱신.
            //---------------------------------------------------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(formRq.size());
            System.out.println("----------------------------------------------------------------------------");

            TB_CAMPAIGN_LANDING_FORM tbCampaignLandingForm = new TB_CAMPAIGN_LANDING_FORM();

            try{
                tbCampaignLandingForm = campaignLandingFormMapper.getCampaignLandingFormForTB(
                          Long.parseLong(params.get("mbId").toString())
                        , Long.parseLong(params.get("adId").toString())
                        , Long.parseLong(params.get("mbId").toString())
                        , Long.parseLong(params.get("caId").toString())
                );
            } catch(Exception e) {

                System.out.println("tbCampaignLandingForm Fail : [" + e + "]");

                resultMap.put("result", false);
                resultMap.put("message", "캠페인 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

                System.out.println("처리 메세지 : [" + "뭐밍 냥냥냥 ???" + "]");

//                trxManager.rollback(trxStatus);
                return resultMap;
            }

            tbCampaignLandingForm.setCaId(Long.parseLong(params.get("caId").toString()));
            tbCampaignLandingForm.setRegClntId       (orgCpaCampaignMaster.getOperId());
            tbCampaignLandingForm.setRegIp           (clientIp);
            tbCampaignLandingForm.setStipulationTitle(params.get("stipulationTitle").toString());
            tbCampaignLandingForm.setStipulationDesc (params.get("stipulationDesc").toString());

            String sAskList = "";

            System.out.println("Sz : [" + formRq.size() + "]");

            for(int i = 0 ; i < formRq.size() ; i++) {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println(formRq.get(i).toString());
                System.out.println("----------------------------------------------------------------------------");

                if(formRq.get(i).get("value").equals("") || formRq.get(i).get("value") == null) {
                    if(i == 9)
                        sAskList += "-";
                    else
                        sAskList += "-,";
                }
                else {
                    if(i == 9)
                        sAskList += formRq.get(i).get("value").toString();
                    else
                        sAskList += formRq.get(i).get("value").toString() + ",";
                }

                switch(i) {
                    case 0 :
                        tbCampaignLandingForm.setType01 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue01(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage01 (formRq.get(i).get("desc").toString());
                        break;
                    case 1 :
                        tbCampaignLandingForm.setType02 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue02(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage02 (formRq.get(i).get("desc").toString());
                        break;
                    case 2 :
                        tbCampaignLandingForm.setType03 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue03(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage03 (formRq.get(i).get("desc").toString());
                        break;
                    case 3 :
                        tbCampaignLandingForm.setType04 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue04(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage04 (formRq.get(i).get("desc").toString());
                        break;
                    case 4 :
                        tbCampaignLandingForm.setType05 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue05(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage05 (formRq.get(i).get("desc").toString());
                        break;
                    case 5 :
                        tbCampaignLandingForm.setType06 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue06(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage06 (formRq.get(i).get("desc").toString());
                        break;
                    case 6 :
                        tbCampaignLandingForm.setType07 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue07(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage07 (formRq.get(i).get("desc").toString());
                        break;
                    case 7 :
                        tbCampaignLandingForm.setType08 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue08(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage08 (formRq.get(i).get("desc").toString());
                        break;
                    case 8 :
                        tbCampaignLandingForm.setType09 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue09(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage09 (formRq.get(i).get("desc").toString());
                        break;
                    case 9 :
                        tbCampaignLandingForm.setType10 (formRq.get(i).get("types").toString());
                        tbCampaignLandingForm.setValue10(formRq.get(i).get("value").toString());
                        tbCampaignLandingForm.setPage10 (formRq.get(i).get("desc").toString());
                        break;
                }

                System.out.println("----------------------------------------------------------------------------");
                System.out.println(tbCampaignLandingForm.toString());
                System.out.println("----------------------------------------------------------------------------");
            }

            orgCpaCampaignMaster.setAskList(sAskList);

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.upCampaignMaster Start");
            System.out.println("----------------------------------------------------------------------------");



            System.out.println("orgCpaCampaignMaster.toString()");
            System.out.println(orgCpaCampaignMaster.toString());

            Long ret = campaignMasterMapper.upCampaignMaster(orgCpaCampaignMaster);
            if(ret <= 0) {
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step01");
                resultMap.put("result", false);
                resultMap.put("message", "캠페인 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step02");

                System.out.println("처리 campaignMasterMapper.upCampaignMaster : [" + "에러나씀 ㅠㅠ" + "]");
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step03");

//                trxManager.rollback(trxStatus);
                return resultMap;
            }
            else {
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step04");
                System.out.println("campaignMasterMapper.upCampaignMaster : [" + ret + "]");
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step05");

//                adInfoUtil.InsAdOperationHistory("O"
//                                                , orgCpaCampaignMaster.getMbId()
//                                                , orgCpaCampaignMaster.getOperId()
//                                                , "00"
//                                                , "["+ orgCpaCampaignMaster.getName() +"] 캠페인 정보를 변경하였습니다."
//                );
            }

            System.out.println("tbCampaignLandingForm.toString()");
            System.out.println(tbCampaignLandingForm.toString());

            Long rets = 0L;
            try {
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step06");
                System.out.println(tbCampaignLandingForm.toString());
                rets = campaignLandingFormMapper.updCampaignLandingForm(tbCampaignLandingForm);
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step07");
            } catch(Exception e) {
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step08");
                System.out.println("updCampaignLandingForm Fail 0 : [" + e + "]");
                System.out.println("campaignMasterMapper.upCampaignMaster" + ": Step09");

                resultMap.put("result", false);
                resultMap.put("message", "캠페인 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

                System.out.println("처리 메세지 : [" + "뭐밍???" + "]");

//                trxManager.rollback(trxStatus);
                return resultMap;
            }

            System.out.println("campaignLandingFormMapper.updCampaignLandingForm 결과 : [" + rets + "]");

            if(rets <= 0) {
                resultMap.put("result", false);
                resultMap.put("message", "캠페인 랜딩페이지 포멧 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

                System.out.println("처리 메세지 : [" + "ret 값이 0보다 작다" + "]");

//                trxManager.rollback(trxStatus);
                return resultMap;
            }
            else {
                resultMap.put("result", true);
                resultMap.put("message", "캠페인 랜딩페이지 포멧 변경이 정상적으로 처리되었습니다.");

                System.out.println("처리 메세지 : [" + "정상이래..." + "]");

//                adInfoUtil.InsAdOperationHistory("O"
//                        , orgCpaCampaignMaster.getMbId()
//                        , orgCpaCampaignMaster.getOperId()
//                        , "00"
//                        , "["+ orgCpaCampaignMaster.getName() +"] 캠페인 정보를 변경하였습니다."
//                );
            }
        } catch(Exception e) {
            System.out.println("updCampaignLandingForm Fail 1 : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "캠페인 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

            System.out.println("처리 메세지 : [" + "뭐밍???" + "]");

//            trxManager.rollback(trxStatus);
            return resultMap;
        }

        System.out.println("최종 처리 메세지 : [" + resultMap + "]");

//        trxManager.commit(trxStatus);
        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 삭제
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.03
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] CAMPAIGN_MASTER
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "ChangeCampaignStatus", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> ChangeCampaignStatus(HttpServletRequest    rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("ChangeCampaignStatus Func Start...");
        System.out.println("############################################################################");

        Long lReturn = -1L;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("clntId   : [" + rq.getParameter("clntId") + "]");
        System.out.println("campName : [" + rq.getParameter("campName") + "]");
        System.out.println("mbId     : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId     : [" + rq.getParameter("adId") + "]");
        System.out.println("caId     : [" + rq.getParameter("caId") + "]");
        System.out.println("status   : [" + rq.getParameter("status") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.changeStatusCampaignMaster Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            lReturn = campaignMasterMapper.changeStatusCampaignMaster(
                    Long.parseLong(rq.getParameter("mbId")),
                    Long.parseLong(rq.getParameter("adId")),
                    Long.parseLong(rq.getParameter("caId")),
                    rq.getParameter("status")
            );

            System.out.println("lReturn : " + lReturn);

            if( lReturn <= 0) {
                resultMap.put("status", false);
                resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");
            }
            else {
                resultMap.put("status", true);
                resultMap.put("comment", "캠페인의 상태를 변경하였습니다.");

                adInfoUtil.InsAdOperationHistory("O"
                        , Long.parseLong(rq.getParameter("mbId"))
                        , rq.getParameter("clntId").toString()
                        , "00"
                        , "["+ rq.getParameter("campName").toString() +"] 캠페인의 상태를 변경하였습니다."
                );
            }
        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
            resultMap.put("status", false);
            resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");
        }

        System.out.println("리턴 데이터 : ["+ resultMap.toString() +"]");

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 정보 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.11
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetCampInfo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetCampInfo(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n");
        System.out.println("############################################################################");
        System.out.println("GetCampInfo Func Start...");
        System.out.println("############################################################################");

        Map<String, Object>         resultMap             = new HashMap<String, Object>();
        Map<String, Object>         tbCampaignMaster      = new HashMap<String, Object>();
        Map<String, Object>         tbCampaignLandingForm = new HashMap<String, Object>();

        List<Map<String, Object>>   resultObj             = new ArrayList<Map<String, Object>>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId : [" + rq.getParameter("adId") + "]");
        System.out.println("mkId : [" + rq.getParameter("mkId") + "]");
        System.out.println("caId : [" + rq.getParameter("caId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdCaOne Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            tbCampaignMaster = campaignMasterMapper.getCampaignMasterForMbAdCaOne(
                    Long.parseLong(rq.getParameter("mbId")),
                    Long.parseLong(rq.getParameter("adId")),
                    Long.parseLong(rq.getParameter("caId"))
            );

            System.out.println("campaignMaster : [" + tbCampaignMaster + "]");

            if( tbCampaignMaster == null) {
                System.out.println("tbCampaignMaster is null!!");
                resultMap.put("status", false);
                resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");

                resultObj.add(0, resultMap);
                return resultObj;
            }


        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
            resultMap.put("status", false);
            resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");

            resultObj.add(0, resultMap);
            return resultObj;
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignLandingFormMapper.getCampaignLandingForm Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            tbCampaignLandingForm = campaignLandingFormMapper.getCampaignLandingForm(
                    Long.parseLong(rq.getParameter("mbId")),
                    Long.parseLong(rq.getParameter("adId")),
                    Long.parseLong(rq.getParameter("mkId")),
                    Long.parseLong(rq.getParameter("caId"))
            );

            //System.out.println("campaignLandingForm : [" + tbCampaignLandingForm + "]");

            if( tbCampaignLandingForm == null) {
                System.out.println("tbCampaignLandingForm is null!!");
                resultMap.put("status", false);
                resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");

                resultObj.add(0, resultMap);
                return resultObj;
            }


        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
            resultMap.put("status", false);
            resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");
            resultObj.add(0, resultMap);

            return resultObj;
        }

        resultMap.put("status", true);
        resultMap.put("comment", "정상적으로 조회되었습니다.");

        resultObj.add(0, resultMap);
        resultObj.add(1, tbCampaignMaster);
        resultObj.add(2, tbCampaignLandingForm);

        System.out.println("리턴 데이터 : ["+ resultObj.toString() +"]");

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인명 리스트
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetCampaignNameLst", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetCampaignNameLst(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetCampaignNameLst Func Start...");
        System.out.println("############################################################################");

        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId : [" + rq.getParameter("adId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterNameList Start");
        System.out.println("----------------------------------------------------------------------------");
        resultObj = campaignMasterMapper.getCampaignMasterNameList(Long.parseLong(rq.getParameter("mbId")), Long.parseLong(rq.getParameter("adId")));

        System.out.println("리턴 데이터 : ["+ resultObj.toString() +"]");

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인명 입력리스트 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.12
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetCampaignAskList", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetCampaignAskList(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetCampaignAskList Func Start...");
        System.out.println("############################################################################");

        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId : [" + rq.getParameter("adId") + "]");
        System.out.println("caId : [" + rq.getParameter("caId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterAskList Start");
        System.out.println("----------------------------------------------------------------------------");
        resultObj = campaignMasterMapper.getCampaignMasterAskList(
                Long.parseLong(rq.getParameter("mbId")),
                Long.parseLong(rq.getParameter("adId")),
                Long.parseLong(rq.getParameter("caId"))
        );

        System.out.println("리턴 데이터 : ["+ resultObj.toString() +"]");

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 리스트 (상태값으로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetCampaignForMbAdStatus", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ArrayList<List<Map<String, Object>>> GetCampaignMasterForMbAdStatus(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetCampaignMasterForMbAdStatus Func Start...");
        System.out.println("############################################################################");

        String      status = "";
        ArrayList<List<Map<String, Object>>> cpaResult = new ArrayList<>();


        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId     : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId     : [" + rq.getParameter("adId") + "]");

        System.out.println("status   : [" + rq.getParameter("status") + "]");
        System.out.println("curPage  : [" + rq.getParameter("curPage") + "]");
        System.out.println("rowCount : [" + rq.getParameter("rowCount") + "]");

        //---------------------------------------------------------------------------------------------------------
        // 캠페인 목록을 조회한다.
        //---------------------------------------------------------------------------------------------------------
        if(rq.getParameter("status").toString().equals("00")) {
            status = "%%";
        }
        else {
            status = rq.getParameter("status").toString();
        }

        //------------------------------------------------------------------------
        // 조회된 캠페인의 총 건수 (페이지 처리용)
        //------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdStatus_TotalCount Start");
        System.out.println("----------------------------------------------------------------------------");
        List<Map<String, Object>> rowTotalCount = campaignMasterMapper.getCampaignMasterForMbAdStatus_TotalCount(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , status
        );
        cpaResult.add(0, rowTotalCount);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdStatus_ViewCount Start");
        System.out.println("----------------------------------------------------------------------------");
        resultObj = campaignMasterMapper.getCampaignMasterForMbAdStatus_ViewCount(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , status
                , (Long.parseLong(rq.getParameter("curPage").toString()) - 1) * Long.parseLong(rq.getParameter("rowCount").toString())
                , Long.parseLong(rq.getParameter("rowCount").toString())
        );

        cpaResult.add(1, resultObj);

        System.out.println("리턴 데이터 : ["+ cpaResult.toString() +"]");

        return cpaResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 리스트 (캠페인번호로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetCampaignForMbAdCa", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public TB_CAMPAIGN_MASTER GetCampaignForMbAdCa(HttpServletRequest rq) throws Exception {
        TB_CAMPAIGN_MASTER campaignMaster = new TB_CAMPAIGN_MASTER();

        System.out.println("\n\n############################################################################");
        System.out.println("GetCampaignForMbAdCa Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMaster.getCampaignMasterForMbAdCa Start");
        System.out.println("----------------------------------------------------------------------------");

        campaignMaster = campaignMasterMapper.getCampaignMasterForMbAdCa(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
        );

        System.out.println("리턴 메세지 : ["+ campaignMaster.toString() +"]");

        return campaignMaster;
    }
}