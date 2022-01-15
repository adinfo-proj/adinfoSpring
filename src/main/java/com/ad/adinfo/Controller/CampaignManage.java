package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.AD_ADVERT_BALANCE;
import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import com.ad.adinfo.Mapper.AdAdvertBalance;
import com.ad.adinfo.Mapper.CampaignMaster;
import com.ad.adinfo.Service.AdInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class CampaignManage {
    private final CampaignMaster    campaignMaster;
    private final AdAdvertBalance   adAdvertBalanceMapper;
    private final AdInfoUtil        adInfoUtil;

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
    //@Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/newcampaign", method = RequestMethod.POST)
    public Map<String, Object> insCampaignMaster(
            @RequestHeader Map<String, Object> rHeader,
            @RequestParam(value = "upFile", required = false) MultipartFile upFile,
            @RequestPart (value = "dataObj") Map<String, Object> params) throws Exception {
        CAMPAIGN_MASTER     cpaCampaignMaster   = new CAMPAIGN_MASTER();
        AD_ADVERT_BALANCE   adAdvertBalance     = new AD_ADVERT_BALANCE();

        Map<String, Object> resultMap = null;

        UUID uuid                = UUID.randomUUID();
        Long newCaId             = 0L;

        System.out.println("rHeader   : [" + rHeader + "]");
        System.out.println("res       : [" + params + "]");

        try {
            System.out.println("res   : [" + upFile.toString() + "]");
        } catch (Exception e) {
            System.out.println("adAdvertBalanceMapper.insAdAdvertBalance Fail : [" + e + "]");
            resultMap.put("result", "fail");
            resultMap.put("resultMessage", "캠페인 배너가 등록되지 않았습니다.");
            return resultMap;
        }

        String srcFullName = "";

        try {
            //---------------------------------------------------------------------------------------------------------
            // 업로드한 파일을 서버 폴더에 저장한다.
            //---------------------------------------------------------------------------------------------------------
            if (!upFile.isEmpty()) {
                String ImgExtention = FilenameUtils.getExtension(upFile.getOriginalFilename());

                //-------------------------------------------------------------------
                // 업로드되는 파일이 있는 경우 파일을 저장한다.
                //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
                //-------------------------------------------------------------------
                // 디렉토리 + 임의값 + .확장자
                srcFullName = "/WebFile/MB_001/banner/" + uuid + "." + ImgExtention;
                upFile.transferTo(new File(srcFullName));
            }

            System.out.println("srcFullName         : [" + srcFullName + "]");

            System.out.println("rq mbId             : [" + params.get("mbId") + "]");
            System.out.println("rq operId           : [" + params.get("operId") + "]");

            System.out.println("rq adSrtDt          : [" + params.get("adSrtDt") + "]");
            System.out.println("rq adSrtTm          : [" + params.get("adSrtTm") + "]");
            System.out.println("rq adEndDt          : [" + params.get("adEndDt") + "]");
            System.out.println("rq adEndTm          : [" + params.get("adEndTm") + "]");

            System.out.println("rq adPurpose        : [" + params.get("adPurpose") + "]");

            System.out.println("rq adTopKind        : [" + params.get("adTopKind") + "]");
            System.out.println("rq adMiddleKind     : [" + params.get("adMiddleKind") + "]");

            System.out.println("rq adName           : [" + params.get("adName") + "]");
            System.out.println("rq adComment        : [" + params.get("adComment") + "]");
            System.out.println("rq adUsp            : [" + params.get("adUsp") + "]");

            System.out.println("rq adPrice          : [" + params.get("adPrice") + "]");
            System.out.println("rq adPromotionPrice : [" + params.get("adPromotionPrice") + "]");
            System.out.println("rq adMinQty         : [" + params.get("adMinQty") + "]");
            System.out.println("rq dayLimit         : [" + params.get("dayLimit") + "]");

            System.out.println("rq approval         : [" + params.get("approval") + "]");
            System.out.println("rq ageTarget        : [" + params.get("ageTarget") + "]");
            System.out.println("rq ageTargetFrom    : [" + params.get("ageTargetFrom") + "]");
            System.out.println("rq ageTargetTo      : [" + params.get("ageTargetTo") + "]");

            System.out.println("rq reqWordCond      : [" + params.get("reqWordCond") + "]");
            System.out.println("rq banChannelCond   : [" + params.get("banChannelCond") + "]");
            System.out.println("rq banExChannel     : [" + params.get("banExChannelCond") + "]");
            System.out.println("rq banImageCond     : [" + params.get("banImageCond") + "]");
            System.out.println("rq banWordCond      : [" + params.get("banWordCond") + "]");
            System.out.println("rq nullifyCond      : [" + params.get("nullifyCond") + "]");
            System.out.println("rq cancelCond       : [" + params.get("cancelCond") + "]");
            System.out.println("rq smsYn            : [" + params.get("smsYn") + "]");
            System.out.println("rq smsNo            : [" + params.get("smsNo") + "]");
            System.out.println("rq autoConfirm      : [" + params.get("autoConfirm") + "]");

            //-------------------------------------------------------------------
            // DB생성을 위해 변수를 대입한다.
            //-------------------------------------------------------------------
            // 회원사 ID
            cpaCampaignMaster.setMbId(Long.parseLong(params.get("mbId").toString()));

            // 로그인 아이디로 광고주ID를 조회한다.
            //Integer adId = adInfoUtil.AdClntIdToAdId(rq.get("clntId"));
            cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId(params.get("operId").toString()));
            System.out.println(cpaCampaignMaster.getAdId());

            // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
            //   - MB_ID와 AD_ID 기준으로 CA_ID번호를 산출한다.
            try {
                newCaId = campaignMaster.getCampaignMasterMaxCaId(cpaCampaignMaster.getMbId(), cpaCampaignMaster.getAdId());
                System.out.println("Max CA_ID 1 : [" + newCaId + "]");
                newCaId = (newCaId == null) ? 1000L : newCaId + 1L;
            } catch (Exception e) {
                System.out.println("campaignMaster.getCampaignMasterMaxCaId Fail : [" + e + "]");
            }

            cpaCampaignMaster.setCaId(newCaId);

            System.out.println("Step 01");
            // 작업자 ID
            cpaCampaignMaster.setOperId(params.get("operId").toString());

            // 캠페인 시작일자
            cpaCampaignMaster.setSrtDt(params.get("adSrtDt").toString().replaceAll("-", ""));

            // 캠페인 시작시간
            cpaCampaignMaster.setSrtTm(params.get("adSrtTm").toString().replaceAll(":", ""));

            // 캠페인 종료일자
            cpaCampaignMaster.setEndDt(params.get("adEndDt").toString().replaceAll("-", ""));
            System.out.println("Step 02");
            // 캠페인 종료시간
            cpaCampaignMaster.setEndTm(params.get("adEndTm").toString().replaceAll(":", ""));

            // 캠페인 종류(COMMON_CODE:AD_KIND)
            // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
            cpaCampaignMaster.setCampaignKind(params.get("adPurpose").toString());

            // 광고 지역(전국/서울/경기/강원/충남/충북/전북/전남/경북/경남/제주/기타)
            cpaCampaignMaster.setCampaignArea("00");
            System.out.println("Step 03");
            // 광고 지역이 기타인 경우 입력값
            //cpaCampaignMaster.setCampaignAreaEtc(campaignAreaEtc);

            // 캠페인 상태(COMMON_CODE:CAMPAIGN_STATUS)
            cpaCampaignMaster.setStatus("01");

            // 캠페인명
            cpaCampaignMaster.setName(params.get("adName").toString());

            // 캠페인 광고구분(COMMON_CODE:CAMPAIGN_TP)
            cpaCampaignMaster.setTp("A");

            // 광고 대분류(COMMON_CODE:CAMPAIGN_TOP_GROUP)
            cpaCampaignMaster.setTopKind(params.get("adTopKind").toString());

            // 광고 중분류(COMMON_CODE:CAMPAIGN_MIDDLE_GROUP)
            cpaCampaignMaster.setMiddleKind(params.get("adMiddleKind").toString());
            System.out.println("Step 04");

            cpaCampaignMaster.setPurpose(params.get("adPurpose").toString());

            // 광고주 단가
            if ((params.get("adPrice") == null) || params.get("adPrice").equals(""))
                cpaCampaignMaster.setPrice(0L);
            else
                cpaCampaignMaster.setPrice(Long.parseLong(params.get("adPrice").toString().replaceAll(",", "")));

            // 광고주 프로모션 가격
            if ((params.get("adPromotionPrice") == null) || params.get("adPromotionPrice").equals(""))
                cpaCampaignMaster.setPromotionPrice(0L);
            else
                cpaCampaignMaster.setPromotionPrice(Long.parseLong(params.get("adPromotionPrice").toString().replaceAll(",", "")));

            // 파트너별 일별 DB 접수 제한 건수
            if ((params.get("dayLimit") == null) || params.get("dayLimit").equals(""))
                cpaCampaignMaster.setDayLimit(0L);
            else
                cpaCampaignMaster.setDayLimit(Long.parseLong(params.get("dayLimit").toString()));

            // 등    록자 IP
//    cpaCampaignMaster.setRegIp((String)rq.get("regIp"));
            System.out.println("Step 05");


            // 캠페인 상세설명
            cpaCampaignMaster.setComment(params.get("adComment").toString());

            // 캠페인 특징
            cpaCampaignMaster.setUsp(params.get("adUsp").toString());

            // 대행사의 경우 원광고주 사용자ID(실광고주가 캠페인 현황을 보기 위한 참조ID)
//            cpaCampaignMaster.setReferId(rHeader.get("referer").toString());

            // 고객이 정보입력 항목
            //cpaCampaignMaster.setAskList(askList);

            // 캠페인 필수 문구
            cpaCampaignMaster.setReqWordCond(params.get("reqWordCond").toString());

            // 캠페인 제외 문구 요청
            //    cpaCampaignMaster.setExceptMeant(exceptMeant);

            // 캠페인 DB 취소 조건
            cpaCampaignMaster.setCnclData(params.get("cancelCond").toString());

            // 캠페인 DB 등록시 SMS 수신여부
            if ((params.get("smsYn") == null) || params.get("smsYn").equals(""))
                cpaCampaignMaster.setSmsYn("N");
            else
                cpaCampaignMaster.setSmsYn(params.get("smsYn").toString());

            // SMS 수신받을 휴대폰번호
            cpaCampaignMaster.setSmsNo(params.get("smsNo").toString().replaceAll("-", ""));
            System.out.println("Step 06");
            // 랜딩페이지 상단 창 제목
            //    cpaCampaignMaster.setLandingPageTitle((String)rq.get("landingPageTitle"));

            // 광고 의뢰시 참조할 자사 랜딩페이지
            //    cpaCampaignMaster.setLandingUrl((String)rq.get("landingUrl"));
            //
            // 배너 경로/파일명
            cpaCampaignMaster.setBannerPath(srcFullName);
            System.out.println("Step 06-1");
            // 자동 확정 일수
            if ((params.get("autoConfirm") == null) || params.get("autoConfirm").equals(""))
                cpaCampaignMaster.setAutoConfirm(7L);
            else
                cpaCampaignMaster.setAutoConfirm(Long.parseLong(params.get("autoConfirm").toString()));


            System.out.println("Step 06-2");

            // 기본승인률
            if ((params.get("approval") == null) || params.get("approval").equals(""))
                cpaCampaignMaster.setApproval(50.00);
            else
                cpaCampaignMaster.setApproval(Double.parseDouble(params.get("approval").toString()));

            System.out.println("Step 07");

            // 무효 조건
            if (params.get("nullifyCond") != null)
                cpaCampaignMaster.setNullifyCond(params.get("nullifyCond").toString());

            // 취소 조건
            if (params.get("cancelCond") != null)
                cpaCampaignMaster.setCancelCond(params.get("cancelCond").toString());

            // 선호 채널
            if (params.get("banExChannelCond") != null)
                cpaCampaignMaster.setBanExChannelCond(params.get("banExChannelCond").toString());

            // 금지 채널
            if (params.get("banChannelCond") != null)
                cpaCampaignMaster.setBanChannelCond(params.get("banChannelCond").toString());

            // 금지 이미지
            if (params.get("banImageCond") != null)
                cpaCampaignMaster.setBanImageCond(params.get("banImageCond").toString());

            // 금지 단어
            if (params.get("banWordCond") != null)
                cpaCampaignMaster.setBanWordCond(params.get("banWordCond").toString());

            // 연령제한
            if ((params.get("ageTarget") == null) || params.get("ageTarget").equals("")) {
                cpaCampaignMaster.setAgeTarget("");
            } else {
                if (params.get("ageTarget").equals("N"))
                    cpaCampaignMaster.setAgeTarget("");
                else
                    cpaCampaignMaster.setAgeTarget(params.get("ageTargetFrom").toString() + "|" + params.get("ageTargetTo").toString());
            }
        } catch (Exception e) {
            System.out.println("campaignMaster.insCampaignMaster Fail : [" + e + "]");
        }

        System.out.println("Parsing Data : [" + cpaCampaignMaster + "]");
System.out.println("Step 08");
        //-------------------------------------------------------------------
        // 캠페인 마스터에 데이터를 생성한다.
        //-------------------------------------------------------------------
        try {
            campaignMaster.insCampaignMaster(cpaCampaignMaster);
        } catch (Exception e) {
            System.out.println("campaignMaster.insCampaignMaster Fail : [" + e + "]");
        }

        //-------------------------------------------------------------------
        // 광고주 충전금 마스터 (AD_ADVERT_BALANCE) 테이블에 해당 캠페인의 충전금액 항목을 생성한다.
        //-------------------------------------------------------------------
        adAdvertBalance.setMbId(cpaCampaignMaster.getMbId());
        adAdvertBalance.setAdId(cpaCampaignMaster.getAdId());
        adAdvertBalance.setCaId(cpaCampaignMaster.getCaId());
        adAdvertBalance.setAdvtMedia("A");
        adAdvertBalance.setChargeAmt(0.00);
        adAdvertBalance.setBonusAmt(0.00);
        adAdvertBalance.setSupportAmt(0.00);
        adAdvertBalance.setBeforeChargeAmt(0.00);
        adAdvertBalance.setZeroAmtSmsYn("N");

        System.out.println("getSmsYn : [" + cpaCampaignMaster.getSmsYn() + "]");

        if ((params.get("smsYn") == null) || (params.get("smsYn").equals("")))
            adAdvertBalance.setSmsSendYn("N");
        else
            adAdvertBalance.setSmsSendYn(params.get("smsYn").toString());

        adAdvertBalance.setZeroAmtSmsYn("N");

        try {
            adAdvertBalanceMapper.insAdAdvertBalance(adAdvertBalance);
        } catch (Exception e) {
            System.out.println("adAdvertBalanceMapper.insAdAdvertBalance Fail : [" + e + "]");
        }

        //-------------------------------------------------------------------
        // SEQ로 생성된 캠페인 아이디를 리턴한다.
        //-------------------------------------------------------------------

        //return cpaCampaignMaster.getCaId();
        resultMap.put("result", "success");
        resultMap.put("resultMessage", "캠페인 등록이 정상적으로 처리되었습니다.");

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
//        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("mbId : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId : [" + rq.getParameter("adId") + "]");

        return campaignMaster.getCampaignMasterNameList(Long.parseLong(rq.getParameter("mbId")), Long.parseLong(rq.getParameter("adId")));
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 랜딩페이지 리스트
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
    @RequestMapping(value = "GetLandingNameLst", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetLandingNameLst(HttpServletRequest rq) throws Exception {
        System.out.println("GetLandingNameLst----------------------");
        System.out.println("mbId : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId : [" + rq.getParameter("adId") + "]");
        System.out.println("caId : [" + rq.getParameter("caId") + "]");

        return campaignMaster.getLandingNameList(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
        );
    }
}