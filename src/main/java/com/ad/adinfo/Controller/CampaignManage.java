package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.AD_ADVERT_BALANCE;
import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import com.ad.adinfo.Mapper.AdAdvertBalance;
import com.ad.adinfo.Mapper.CampaignMaster;
import com.ad.adinfo.Service.AdInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @PostMapping(value = "/newcampaign")
    @ResponseBody
    public String insCampaignMaster(@RequestBody Map<String, Object> req,  HttpServletResponse res) {
//    public String insCampaignMaster(
//              @RequestParam(value="upFile"          , required=false) MultipartFile upFile
//            , @RequestParam(value="mbId"            , required=false) String mbId
//            , @RequestParam(value="operId"          , required=false) String operId
//            , @RequestParam(value="adKind"          , required=false) String adKind
//            , @RequestParam(value="adArea"          , required=false) String adArea
//            , @RequestParam(value="adSrtDt"         , required=false) String adSrtDt
//            , @RequestParam(value="adSrtTm"         , required=false) String adSrtTm
//            , @RequestParam(value="adEndDt"         , required=false) String adEndDt
//            , @RequestParam(value="adEndTm"         , required=false) String adEndTm
//            , @RequestParam(value="adPurpose"       , required=false) String adPurpose
//            , @RequestParam(value="adTopKind"       , required=false) String adTopKind
//            , @RequestParam(value="adMiddleKind"    , required=false) String adMiddleKind
//            , @RequestParam(value="adName"          , required=false) String adName
//            , @RequestParam(value="adComment"       , required=false) String adComment
//            , @RequestParam(value="adUsp"           , required=false) String adUsp
//            , @RequestParam(value="smsYn"           , required=false) String smsYn
//            , @RequestParam(value="smsNo"           , required=false) String smsNo
//            , @RequestParam(value="adPrice"         , required=false) String adPrice
//            , @RequestParam(value="adPromotionPrice", required=false) String adPromotionPrice
//            , @RequestParam(value="adMinQty"        , required=false) String adMinQty
//            , @RequestParam(value="dayLimit"        , required=false) String dayLimit
//            , @RequestParam(value="approval"        , required=false) String approval
//            , @RequestParam(value="ageTarget"       , required=false) String ageTarget
//            , @RequestParam(value="ageTargetFrom"   , required=false) String ageTargetFrom
//            , @RequestParam(value="ageTargetTo"     , required=false) String ageTargetTo
//            , @RequestParam(value="reqWordCond"     , required=false) String reqWordCond
//            , @RequestParam(value="banChannel"      , required=false) String banChannel
//            , @RequestParam(value="banExChannel"    , required=false) String banExChannel
//            , @RequestParam(value="banImageCond"    , required=false) String banImageCond
//            , @RequestParam(value="banWordCond"     , required=false) String banWordCond
//            , @RequestParam(value="nullifyCond"     , required=false) String nullifyCond
//            , @RequestParam(value="cancelCond"      , required=false) String cancelCond
//            , @RequestParam(value="autoConfirm"     , required=false) String autoConfirm
//    ) throws Exception
//    {
        CAMPAIGN_MASTER     cpaCampaignMaster   = new CAMPAIGN_MASTER();
        AD_ADVERT_BALANCE   adAdvertBalance     = new AD_ADVERT_BALANCE();

        UUID uuid                = UUID.randomUUID();
        Long newCaId             = 0L;

        System.out.println("res   : [" + req.toString() + "]");
        System.out.println("file   : [" + res.getTrailerFields() + "]");

//        String srcFullName      = "";
//        //---------------------------------------------------------------------------------------------------------
//        // 업로드한 파일을 서버 폴더에 저장한다.
//        //---------------------------------------------------------------------------------------------------------
//        if(!upFile.isEmpty()) {
//            String ImgExtention     = FilenameUtils.getExtension(upFile.getOriginalFilename());
//
//            //-------------------------------------------------------------------
//            // 업로드되는 파일이 있는 경우 파일을 저장한다.
//            //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
//            //-------------------------------------------------------------------
//            // 디렉토리 + 임의값 + .확장자
//            srcFullName = "C:/WebFile/" + uuid + "." + ImgExtention;
//            upFile.transferTo(new File(srcFullName));
//        }
//
//        System.out.println("srcFullName   : [" + srcFullName + "]");
//
//        System.out.println("rq mbId             : [" + rq.get("mbId") + "]");
//        System.out.println("rq adKind           : [" + rq.get("adKind") + "]");
//        System.out.println("rq adArea           : [" + rq.get("adArea") + "]");
//        System.out.println("rq adSrtDt          : [" + rq.get("adSrtDt") + "]");
//        System.out.println("rq adSrtTm          : [" + rq.get("adSrtTm") + "]");
//        System.out.println("rq adEndDt          : [" + rq.get("adEndDt") + "]");
//        System.out.println("rq adEndTm          : [" + rq.get("adEndTm") + "]");
//        System.out.println("rq adPurpose        : [" + rq.get("adPurpose") + "]");
//        System.out.println("rq adMiddleKind     : [" + rq.get("adMiddleKind") + "]");
//        System.out.println("rq adName           : [" + rq.get("adName") + "]");
//        System.out.println("rq adComment        : [" + rq.get("adComment") + "]");
//        System.out.println("rq adUsp            : [" + rq.get("adUsp") + "]");
//        System.out.println("rq smsYn            : [" + rq.get("smsYn") + "]");
//        System.out.println("rq smsNo            : [" + rq.get("smsNo") + "]");
//        System.out.println("rq adPrice          : [" + rq.get("adPrice") + "]");
//        System.out.println("rq adPromotionPrice : [" + rq.get("adPromotionPrice") + "]");
//        System.out.println("rq adMinQty         : [" + rq.get("adMinQty") + "]");
//        System.out.println("rq dayLimit         : [" + rq.get("dayLimit") + "]");
//        System.out.println("rq approval         : [" + rq.get("approval") + "]");
//        System.out.println("rq ageTarget        : [" + rq.get("ageTarget") + "]");
//        System.out.println("rq ageTargetFrom    : [" + rq.get("ageTargetFrom") + "]");
//        System.out.println("rq ageTargetTo      : [" + rq.get("ageTargetTo") + "]");
//        System.out.println("rq reqWordCon       : [" + rq.get("reqWordCon") + "]");
//        System.out.println("rq banChannel       : [" + rq.get("banChannel") + "]");
//        System.out.println("rq banExChannel     : [" + rq.get("banExChannel") + "]");
//        System.out.println("rq banImageCond     : [" + rq.get("banImageCond") + "]");
//        System.out.println("rq banWordCond      : [" + rq.get("banWordCond") + "]");
//        System.out.println("rq nullifyCond      : [" + rq.get("nullifyCond") + "]");
//        System.out.println("rq cancelCond       : [" + rq.get("cancelCond") + "]");
//        System.out.println("rq autoConfirm      : [" + rq.get("autoConfirm")+ "]");

//        //-------------------------------------------------------------------
//        // DB생성을 위해 변수를 대입한다.
//        //-------------------------------------------------------------------
//        // 회원사 ID
//        cpaCampaignMaster.setMbId(Long.parseLong(mbId));
//
//        // 로그인 아이디로 광고주ID를 조회한다.
//        //Integer adId = adInfoUtil.AdClntIdToAdId(rq.get("clntId"));
//        cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId(operId).longValue());
//        System.out.println(cpaCampaignMaster.getAdId());
//
//        // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
//        //   - MB_ID와 AD_ID 기준으로 CA_ID번호를 산출한다.
//        try {
//            newCaId = campaignMaster.getCampaignMasterMaxCaId(cpaCampaignMaster.getMbId(), cpaCampaignMaster.getAdId());
//            System.out.println("Max CA_ID : [" + newCaId + "]");
//            newCaId = (newCaId == null) ? 1000L : newCaId + 1L;
//        } catch (Exception e) {
//            System.out.println("campaignMaster.getCampaignMasterMaxCaId Fail : [" + e + "]");
//        }
//
//        cpaCampaignMaster.setCaId(newCaId);
//
//        // 작업자 ID
//        cpaCampaignMaster.setOperId(operId);
//
//        // 캠페인 종류(COMMON_CODE:AD_KIND)
//        cpaCampaignMaster.setCampaignKind(adKind);
//
//        // 광고 지역(전국/서울/경기/강원/충남/충북/전북/전남/경북/경남/제주/기타)
//        //cpaCampaignMaster.setCampaignArea(rq.get("campaignArea"));
//        cpaCampaignMaster.setCampaignArea("00");
//
//        // 광고 지역이 기타인 경우 입력값
//        //cpaCampaignMaster.setCampaignAreaEtc(campaignAreaEtc);
//
//        // 캠페인 상태(COMMON_CODE:CAMPAIGN_STATUS)
//        cpaCampaignMaster.setStatus("01");
//
//        // 캠페인명
//        cpaCampaignMaster.setName(adName);
//
//        // 캠페인 광고구분(COMMON_CODE:CAMPAIGN_TP)
//        cpaCampaignMaster.setTp("A");
//
//        // 광고 대분류(COMMON_CODE:CAMPAIGN_TOP_GROUP)
//        cpaCampaignMaster.setTopKind(adTopKind);
//
//        // 광고 중분류(COMMON_CODE:CAMPAIGN_MIDDLE_GROUP)
//        cpaCampaignMaster.setMiddleKind(adMiddleKind);
//
//        // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
//        cpaCampaignMaster.setPurpose(adPurpose);
//
//        // 광고주 단가
//        if ((adPrice == null) || (adPrice.equals("")))
//            cpaCampaignMaster.setPrice(0L);
//        else
//            cpaCampaignMaster.setPrice(Long.parseLong(adPrice.replaceAll(",", "")));
//
//        // 광고주 프로모션 가격
//        if ((adPromotionPrice == null) || (adPromotionPrice.equals("")))
//            cpaCampaignMaster.setPromotionPrice(0L);
//        else
//            cpaCampaignMaster.setPromotionPrice(Long.parseLong(adPromotionPrice.replaceAll(",", "")));
//
//    // SNS 광고 가능여부
////    if ((snsTp == null) || (snsTp.equals("")))
////        cpaCampaignMaster.setSnsYn("N");
////    else
////        cpaCampaignMaster.setSnsYn((String)rq.get("snsTp"));
//
//    // SNS 광고 시 잠재입력 여부
////    cpaCampaignMaster.setFormYn((String)rq.get("formTp"));
//
//    // 잠재고객 처리여부
////    cpaCampaignMaster.setPotenYn((String)rq.get("potenYn"));
//
//    // 외부입력허용
////    cpaCampaignMaster.setExternDataYn((String)rq.get("externDataYn"));
//
//        // 파트너별 일별 DB 접수 제한 건수
//        if ((dayLimit == null) || (dayLimit.equals("")))
//            cpaCampaignMaster.setDayLimit(0L);
//        else
//            cpaCampaignMaster.setDayLimit(Long.parseLong(dayLimit));
//
//    // 등    록자 IP
////    cpaCampaignMaster.setRegIp((String)rq.get("regIp"));
//
//        // 캠페인 시작일자
//        cpaCampaignMaster.setSrtDt(adSrtDt.replaceAll("-", ""));
//
//        // 캠페인 시작시간
//        cpaCampaignMaster.setSrtTm(adSrtTm.replaceAll(":", ""));
//
//        // 캠페인 종료일자
//        cpaCampaignMaster.setEndDt(adEndDt.replaceAll("-", ""));
//
//        // 캠페인 종료시간
//        cpaCampaignMaster.setEndTm(adEndTm.replaceAll(":", ""));
//
//        // 캠페인 상세설명
//        cpaCampaignMaster.setComment(adComment);
//
//        // 캠페인 특징
//        cpaCampaignMaster.setUsp(adUsp);
//
//        // 대행사의 경우 원광고주 사용자ID(실광고주가 캠페인 현황을 보기 위한 참조ID)
//    //    cpaCampaignMaster.setReferId(referId);
//
//        // 고객이 정보입력 항목
//    //    cpaCampaignMaster.setAskList(askList);
//
//        // 캠페인 필수 문구
//        cpaCampaignMaster.setReqWordCond(reqWordCond);
//
//        // 캠페인 제외 문구 요청
//    //    cpaCampaignMaster.setExceptMeant(exceptMeant);
//
//        // 캠페인 DB 취소 조건
//        cpaCampaignMaster.setCnclData(cancelCond);
//
//        // 캠페인 DB 등록시 SMS 수신여부
//        if ((smsYn == null) || (smsYn.equals("")))
//            cpaCampaignMaster.setSmsYn("N");
//        else
//            cpaCampaignMaster.setSmsYn(smsYn);
//
//        // SMS 수신받을 휴대폰번호
//        cpaCampaignMaster.setSmsNo(smsNo);
//
//        // 랜딩페이지 상단 창 제목
//    //    cpaCampaignMaster.setLandingPageTitle((String)rq.get("landingPageTitle"));
//
//        // 광고 의뢰시 참조할 자사 랜딩페이지
//    //    cpaCampaignMaster.setLandingUrl((String)rq.get("landingUrl"));
//    //
//        // 배너 경로/파일명
//        cpaCampaignMaster.setBannerPath(srcFullName);
//
//    // 자동 확정 일수
////    if ((autoConfirm == null) || (autoConfirm.equals("")))
////        cpaCampaignMaster.setAutoConfirm(7L);
////    else
////        cpaCampaignMaster.setAutoConfirm(Long.parseLong(autoConfirm));
//
//        // 기본승인률
//        if ((approval == null) || (approval.equals("")))
//            cpaCampaignMaster.setApproval(50.00);
//        else
//            cpaCampaignMaster.setApproval(Double.parseDouble(approval));
//
//        // 무효 조건
//        cpaCampaignMaster.setNullifyCond(nullifyCond);
//
//        // 취소 조건
//        cpaCampaignMaster.setCancelCond(cancelCond);
//
//        // 선호 채널
//        cpaCampaignMaster.setBanExChannelCond(banExChannel);
//
//        // 금지 채널
//        cpaCampaignMaster.setBanChannelCond(banChannel);
//
//        // 금지 이미지
//        cpaCampaignMaster.setBanImageCond(banImageCond);
//
//        // 금지 단어
//        cpaCampaignMaster.setBanWordCond(banWordCond);
//
//        // 연령제한
//        if( (ageTarget == null) || (ageTarget.equals("")) ) {
//            cpaCampaignMaster.setAgeTarget("");
//        }
//        else {
//            if(ageTarget.equals("N"))
//                cpaCampaignMaster.setAgeTarget("");
//            else
//                cpaCampaignMaster.setAgeTarget(ageTargetFrom + "|" + ageTargetTo);
//        }
//
//        System.out.println("Parsing Data : [" + cpaCampaignMaster + "]");
//
//        //-------------------------------------------------------------------
//        // 캠페인 마스터에 데이터를 생성한다.
//        //-------------------------------------------------------------------
//        try {
//            campaignMaster.insCampaignMaster(cpaCampaignMaster);
//        } catch (Exception e) {
//            System.out.println("campaignMaster.insCampaignMaster Fail : [" + e + "]");
//        }
//
//        //-------------------------------------------------------------------
//        // 광고주 충전금 마스터 (AD_ADVERT_BALANCE) 테이블에 해당 캠페인의 충전금액 항목을 생성한다.
//        //-------------------------------------------------------------------
//        adAdvertBalance.setMbId(cpaCampaignMaster.getMbId());
//        adAdvertBalance.setAdId(cpaCampaignMaster.getAdId());
//        adAdvertBalance.setCaId(cpaCampaignMaster.getCaId());
//        adAdvertBalance.setAdvtMedia("A");
//        adAdvertBalance.setChargeAmt(0.00);
//        adAdvertBalance.setBonusAmt(0.00);
//        adAdvertBalance.setSupportAmt(0.00);
//        adAdvertBalance.setBeforeChargeAmt(0.00);
//        adAdvertBalance.setZeroAmtSmsYn("N");
//
//        System.out.println("getSmsYn : [" + cpaCampaignMaster.getSmsYn() + "]");
//
//        if ((smsYn == null) || (smsYn.equals("")))
//            adAdvertBalance.setSmsSendYn("N");
//        else
//            adAdvertBalance.setSmsSendYn(smsYn);
//
//        adAdvertBalance.setZeroAmtSmsYn("N");
//
//        try {
//            adAdvertBalanceMapper.insAdAdvertBalance(adAdvertBalance);
//        } catch (Exception e) {
//            System.out.println("adAdvertBalanceMapper.insAdAdvertBalance Fail : [" + e + "]");
//        }

        //-------------------------------------------------------------------
        // SEQ로 생성된 캠페인 아이디를 리턴한다.
        //-------------------------------------------------------------------

        //return cpaCampaignMaster.getCaId();
        return "Success";
    }
}

