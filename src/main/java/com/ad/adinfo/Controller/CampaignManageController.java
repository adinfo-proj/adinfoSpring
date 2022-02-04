package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import com.ad.adinfo.Mapper.AdUtilityMapper;
import com.ad.adinfo.Mapper.CampaignMasterMapper;
import com.ad.adinfo.Mapper.DataCenterMapper;
import com.ad.adinfo.Service.AdInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam(value = "upFile", required = false) MultipartFile upFile,
            @RequestPart (value = "dataObj") Map<String, Object> params) throws Exception {
        //---------------------------------------------------------------------------------------------------------
        // 변수 설정 영역 Start
        //---------------------------------------------------------------------------------------------------------
        Long newCaId                            = 0L;
        CAMPAIGN_MASTER     cpaCampaignMaster   = new CAMPAIGN_MASTER();
        Map<String, Object> resultMap           = new HashMap<String, Object>();

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

        System.out.println("헤더        : [" + rHeader + "]");
        System.out.println("입력 파라메터 : [" + params + "]");

        try {
            System.out.println("gradeCd        : [" + params.get("gradeCd") + "]");
            System.out.println("mbId           : [" + params.get("mbId") + "]");
            System.out.println("adId           : [" + params.get("adId") + "]");
            System.out.println("clntId         : [" + params.get("clntId") + "]");
            System.out.println("adPurpose      : [" + params.get("adPurpose") + "]");
            System.out.println("adTopKind      : [" + params.get("adTopKind") + "]");
            System.out.println("adMiddleKind   : [" + params.get("adMiddleKind") + "]");
            System.out.println("adName         : [" + params.get("adName") + "]");
            System.out.println("adComment      : [" + params.get("adComment") + "]");
            System.out.println("adPrice        : [" + params.get("adPrice") + "]");
            System.out.println("adMaketerPrice : [" + params.get("adMaketerPrice") + "]");
            System.out.println("smsYn          : [" + params.get("smsYn") + "]");
            System.out.println("smsNo          : [" + params.get("smsNo") + "]");

            //-------------------------------------------------------------------
            // DB생성을 위해 변수를 대입한다.
            //   - DB마스터는 회원사ID와 대행사ID가 모두 같다.
            //-------------------------------------------------------------------
            cpaCampaignMaster.setMbId(Long.parseLong(params.get("mbId").toString()));
            cpaCampaignMaster.setAdId(Long.parseLong(params.get("adId").toString()));

            // 로그인 아이디로 ID값을 조회한다.
            cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId(params.get("clntId").toString()));

            // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
            //   - MB_ID와 AD_ID 기준으로 CA_ID번호를 산출한다.
            try {
                newCaId = campaignMasterMapper.getCampaignMasterMaxCaId(cpaCampaignMaster.getMbId(), cpaCampaignMaster.getAdId());
                System.out.println("Max CA_ID 1 : [" + newCaId + "]");
                newCaId = (newCaId == null) ? 10000L : newCaId + 1L;
            } catch (Exception e) {
                System.out.println("campaignMaster.getCampaignMasterMaxCaId Fail : [" + e + "]");
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
                camNameCount = campaignMasterMapper.getCampaignMasterByName(
                          Long.parseLong(params.get("mbId").toString())
                        , Long.parseLong(params.get("adId").toString())
                        , params.get("adName").toString());

                System.out.println(camNameCount);

                if(camNameCount > 0) {
                    resultMap.put("result", "failure");
                    resultMap.put("resultMessage", "이미 등록한 캠페인명이 있습니다.");

                    return resultMap;
                }
            } catch (Exception e) {
                System.out.println("campaignMaster.getCampaignMasterByName Fail : [" + e + "]");
            }

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

        //-------------------------------------------------------------------
        // 캠페인 마스터에 데이터를 생성한다.
        //-------------------------------------------------------------------
        try {
            campaignMasterMapper.insCampaignMaster(cpaCampaignMaster);
        } catch (Exception e) {
            System.out.println("campaignMaster.insCampaignMaster Fail : [" + e + "]");

            resultMap.put("result", "failure");
            resultMap.put("resultMessage", "캠페인 등록이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

            return resultMap;
        }

        System.out.println("campaignMaster.insCampaignMaster Success");

        //-------------------------------------------------------------------
        // SEQ로 생성된 캠페인 아이디를 리턴한다.
        //-------------------------------------------------------------------
        resultMap.put("result", "success");
        resultMap.put("resultMessage", "캠페인 등록이 정상적으로 처리되었습니다.");

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
    public Map<String, Object> insCampaignMaster(
            NativeWebRequest nativeWebRequest,
            @RequestPart (value = "dataObj") CAMPAIGN_MASTER upCpaCampaignMaster) throws Exception {
        //---------------------------------------------------------------------------------------------------------
        // 변수 설정 영역 Start
        //---------------------------------------------------------------------------------------------------------
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CAMPAIGN_MASTER orgCpaCampaignMaster = new CAMPAIGN_MASTER();

        System.out.println("upCpaCampaignMaster : " + upCpaCampaignMaster);

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
            orgCpaCampaignMaster = campaignMasterMapper.getCampaignMasterForMbAdCa(
                    upCpaCampaignMaster.getMbId()
                    , upCpaCampaignMaster.getAdId()
                    , upCpaCampaignMaster.getCaId() );

            orgCpaCampaignMaster.setSrtDt(orgCpaCampaignMaster.getSrtDt().replaceAll("-", ""));
            orgCpaCampaignMaster.setSrtTm(orgCpaCampaignMaster.getSrtTm().replaceAll(":", ""));

            upCpaCampaignMaster.setSrtDt(upCpaCampaignMaster.getSrtDt().toString().replaceAll("-", ""));
            upCpaCampaignMaster.setEndDt(upCpaCampaignMaster.getEndDt().toString().replaceAll("-", ""));

            upCpaCampaignMaster.setSrtTm(upCpaCampaignMaster.getSrtTm().toString().replaceAll(":", ""));
            upCpaCampaignMaster.setEndTm(upCpaCampaignMaster.getEndTm().toString().replaceAll(":", ""));


            campaignMasterMapper.insCampaignMasterHistory(orgCpaCampaignMaster);

            upCpaCampaignMaster.setRegIp(clientIp);
            Long ret = campaignMasterMapper.upCampaignMaster(upCpaCampaignMaster);
            if(ret <= 0) {
                resultMap.put("result", "failure");
                resultMap.put("resultMessage", "캠페인 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            }
            else {
                resultMap.put("result", "success");
                resultMap.put("resultMessage", "캠페인 변경이 정상적으로 처리되었습니다.");
            }
        } catch(Exception e) {
            System.out.println("upcampaign Fail : [" + e + "]");

            resultMap.put("result", "failure");
            resultMap.put("resultMessage", "캠페인 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
        }

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
        Long lReturn = -1L;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("status : [" + rq.getParameter("status") + "]");

        try {
            lReturn = campaignMasterMapper.changeStatusCampaignMaster(
                    Long.parseLong(rq.getParameter("mbId")),
                    Long.parseLong(rq.getParameter("adId")),
                    Long.parseLong(rq.getParameter("caId")),
                    rq.getParameter("status")
            );

            System.out.println("lReturn : " + lReturn);

            if( lReturn <= 0) {
                resultMap.put("status", "fail");
            }
            else {
                resultMap.put("status", "succ");
            }
        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
            resultMap.put("status", "fail");
        }

        System.out.println("리턴 데이터 : ["+ resultMap.toString() +"]"); //로또번호 출력

        return resultMap;
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
        System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdStatus_ViewCount Start");
        System.out.println("----------------------------------------------------------------------------");
        resultObj = campaignMasterMapper.getCampaignMasterNameList(Long.parseLong(rq.getParameter("mbId")), Long.parseLong(rq.getParameter("adId")));

        System.out.println("리턴 데이터 : ["+ resultObj.toString() +"]"); //로또번호 출력

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
    public List<Map<String, Object>> GetCampaignMasterForMbAdStatus(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetCampaignMasterForMbAdStatus Func Start...");
        System.out.println("############################################################################");

        String      status = "";
        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("status : [" + rq.getParameter("status") + "]");

        //---------------------------------------------------------------------------------------------------------
        // 캠페인 목록을 조회한다.
        //---------------------------------------------------------------------------------------------------------
        if(rq.getParameter("status").toString().equals("00")) {
            status = "%%";
        }
        else {
            status = rq.getParameter("status").toString();
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdStatus_ViewCount Start");
        System.out.println("----------------------------------------------------------------------------");
        resultObj = campaignMasterMapper.getCampaignMasterForMbAdStatus_ViewCount(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , status
        );

        System.out.println("리턴 데이터 : ["+ resultObj.toString() +"]"); //로또번호 출력

        return resultObj;
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
    public CAMPAIGN_MASTER GetCampaignForMbAdCa(HttpServletRequest rq) throws Exception {
        CAMPAIGN_MASTER campaignMaster = new CAMPAIGN_MASTER();

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

        System.out.println("리턴 메세지 : ["+ campaignMaster.toString() +"]"); //로또번호 출력

        return campaignMaster;
    }
}