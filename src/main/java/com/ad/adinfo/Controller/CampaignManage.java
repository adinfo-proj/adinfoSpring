package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.AD_ADVERT_BALANCE;
import com.ad.adinfo.Domain.CAMPAIGN_MASTER;
import com.ad.adinfo.Domain.Member.CampaignCreate;
import com.ad.adinfo.Mapper.AdAdvertBalance;
import com.ad.adinfo.Mapper.CampaignMaster;
import com.ad.adinfo.Service.AdInfoUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    @RequestMapping(value = "/newcampaign", method = RequestMethod.POST)
    //public String insCampaignMaster(@RequestBody Map<String, Object> keyValue) throws Exception {
    public ResponseEntity<?> insCampaignMaster(
            @RequestParam(value = "upFile") MultipartFile upFile,
            @RequestPart(value = "dataObj") Object params) throws Exception {
        CAMPAIGN_MASTER     cpaCampaignMaster   = new CAMPAIGN_MASTER();
        AD_ADVERT_BALANCE   adAdvertBalance     = new AD_ADVERT_BALANCE();

        UUID uuid                = UUID.randomUUID();
        Long newCaId             = 0L;

        System.out.println("res   : [" + params + "]");
        System.out.println("res   : [" + upFile.toString() + "]");

        String srcFullName      = "";

        //---------------------------------------------------------------------------------------------------------
        // 업로드한 파일을 서버 폴더에 저장한다.
        //---------------------------------------------------------------------------------------------------------
        if(!upFile.isEmpty()) {
            String ImgExtention     = FilenameUtils.getExtension(upFile.getOriginalFilename());

            System.out.println("upFile.getSize() : [" + upFile.getSize() + "]");

            //-------------------------------------------------------------------
            // 업로드되는 파일이 있는 경우 파일을 저장한다.
            //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
            //-------------------------------------------------------------------
            // 디렉토리 + 임의값 + .확장자
            srcFullName = "/WebFile/MB_001/banner/" + uuid + "." + ImgExtention;

            upFile.transferTo(new File(srcFullName));
        }
//
        System.out.println("srcFullName   : [" + srcFullName + "]");

//        System.out.println("rq mbId             : [" + keyValue.getMbId() + "]");
//        System.out.println("rq adKind           : [" + keyValue.getTopKind() + "]");
//        System.out.println("rq adKind           : [" + keyValue.getMiddleKind() + "]");
//        System.out.println("rq adArea           : [" + keyValue.getCampaignArea() + "]");
//        System.out.println("rq adSrtDt          : [" + keyValue.getSrtDt() + "]");
//        System.out.println("rq adSrtTm          : [" + keyValue.getSrtTm() + "]");
//        System.out.println("rq adEndDt          : [" + keyValue.getEndDt() + "]");
//        System.out.println("rq adEndTm          : [" + keyValue.getEndTm() + "]");
//        System.out.println("rq adPurpose        : [" + keyValue.getPurpose() + "]");
//        System.out.println("rq adMiddleKind     : [" + keyValue.getMiddleKind() + "]");
//        System.out.println("rq adName           : [" + keyValue.getName() + "]");
//        System.out.println("rq adComment        : [" + keyValue.getComment() + "]");
//        System.out.println("rq adUsp            : [" + keyValue.getUsp() + "]");
//        System.out.println("rq smsYn            : [" + keyValue.getSmsYn() + "]");
//        System.out.println("rq smsNo            : [" + keyValue.getSmsNo() + "]");
//        System.out.println("rq adPrice          : [" + keyValue.getPrice() + "]");
//        System.out.println("rq adPromotionPrice : [" + keyValue.getPromotionPrice() + "]");
//        System.out.println("rq adMinQty         : [" + keyValue.getAdMinQty() + "]");
//        System.out.println("rq dayLimit         : [" + keyValue.getDayLimit() + "]");
//        System.out.println("rq approval         : [" + keyValue.getApproval() + "]");
//        System.out.println("rq ageTarget        : [" + keyValue.getAgeTarget() + "]");
//        System.out.println("rq ageTargetFrom    : [" + keyValue.getAgeTargetFrom() + "]");
//        System.out.println("rq ageTargetTo      : [" + keyValue.getAgeTargetTo() + "]");
//        System.out.println("rq reqWordCond      : [" + keyValue.getReqWordCond() + "]");
//        System.out.println("rq banChannelCond   : [" + keyValue.getBanChannelCond() + "]");
//        System.out.println("rq banExChannel     : [" + keyValue.getBanExChannelCond() + "]");
//        System.out.println("rq banImageCond     : [" + keyValue.getBanImageCond() + "]");
//        System.out.println("rq banWordCond      : [" + keyValue.getBanWordCond() + "]");
//        System.out.println("rq nullifyCond      : [" + keyValue.getNullifyCond() + "]");
//        System.out.println("rq cancelCond       : [" + keyValue.getCancelCond() + "]");
//        System.out.println("rq autoConfirm      : [" + keyValue.getAutoConfirm()+ "]");
//
//        //-------------------------------------------------------------------
//        // DB생성을 위해 변수를 대입한다.
//        //-------------------------------------------------------------------
//        // 회원사 ID
//        cpaCampaignMaster.setMbId(keyValue.getMbId());
//
//        // 로그인 아이디로 광고주ID를 조회한다.
//        //Integer adId = adInfoUtil.AdClntIdToAdId(rq.get("clntId"));
//        cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId(keyValue.getOperId()).longValue());
//        System.out.println(cpaCampaignMaster.getAdId());
//
//        // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
//        //   - MB_ID와 AD_ID 기준으로 CA_ID번호를 산출한다.
//        try {
//            newCaId = campaignMaster.getCampaignMasterMaxCaId(cpaCampaignMaster.getMbId(), cpaCampaignMaster.getAdId());
//            System.out.println("Max CA_ID 1 : [" + newCaId + "]");
//            newCaId = (newCaId == null) ? 1000L : newCaId + 1L;
//        } catch (Exception e) {
//            System.out.println("campaignMaster.getCampaignMasterMaxCaId Fail : [" + e + "]");
//        }
//
//        System.out.println("Max CA_ID 2 : [" + newCaId + "]");
//
//        cpaCampaignMaster.setCaId(newCaId);
//
//        // 작업자 ID
//        cpaCampaignMaster.setOperId(keyValue.getOperId());
//
//        // 캠페인 종류(COMMON_CODE:AD_KIND)
//        cpaCampaignMaster.setCampaignKind(keyValue.getCampaignKind());
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
//        cpaCampaignMaster.setName(keyValue.getName());
//
//        // 캠페인 광고구분(COMMON_CODE:CAMPAIGN_TP)
//        cpaCampaignMaster.setTp("A");
//
//        // 광고 대분류(COMMON_CODE:CAMPAIGN_TOP_GROUP)
//        cpaCampaignMaster.setTopKind(keyValue.getTopKind());
//
//        // 광고 중분류(COMMON_CODE:CAMPAIGN_MIDDLE_GROUP)
//        cpaCampaignMaster.setMiddleKind(keyValue.getMiddleKind());
//
//        // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
//        cpaCampaignMaster.setPurpose(keyValue.getPurpose());
//
//        // 광고주 단가
//        if ((keyValue.getPrice() == null) || keyValue.getPrice().equals(""))
//            cpaCampaignMaster.setPrice(0L);
//        else
//            cpaCampaignMaster.setPrice(Long.parseLong(keyValue.getPrice().replaceAll(",", "")));
//
//        // 광고주 프로모션 가격
//        if ((keyValue.getPromotionPrice() == null) || (keyValue.getPromotionPrice().equals("")))
//            cpaCampaignMaster.setPromotionPrice(0L);
//        else
//            cpaCampaignMaster.setPromotionPrice(Long.parseLong(keyValue.getPromotionPrice().replaceAll(",", "")));
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
//        if ((keyValue.getDayLimit() == null) || (keyValue.getDayLimit().equals("")))
//            cpaCampaignMaster.setDayLimit(0L);
//        else
//            cpaCampaignMaster.setDayLimit(Long.parseLong(keyValue.getDayLimit()));
//
//    // 등    록자 IP
////    cpaCampaignMaster.setRegIp((String)rq.get("regIp"));
//
//        // 캠페인 시작일자
//        cpaCampaignMaster.setSrtDt(keyValue.getSrtDt().replaceAll("-", ""));
//
//        // 캠페인 시작시간
//        cpaCampaignMaster.setSrtTm(keyValue.getSrtTm().replaceAll(":", ""));
//
//        // 캠페인 종료일자
//        cpaCampaignMaster.setEndDt(keyValue.getEndDt().replaceAll("-", ""));
//
//        // 캠페인 종료시간
//        cpaCampaignMaster.setEndTm(keyValue.getEndTm().replaceAll(":", ""));
//
//        // 캠페인 상세설명
//        cpaCampaignMaster.setComment(keyValue.getComment());
//
//        // 캠페인 특징
//        cpaCampaignMaster.setUsp(keyValue.getUsp());
//
//        // 대행사의 경우 원광고주 사용자ID(실광고주가 캠페인 현황을 보기 위한 참조ID)
//    //    cpaCampaignMaster.setReferId(referId);
//
//        // 고객이 정보입력 항목
//    //    cpaCampaignMaster.setAskList(askList);
//
//        // 캠페인 필수 문구
//        cpaCampaignMaster.setReqWordCond(keyValue.getReqWordCond());
//
//        // 캠페인 제외 문구 요청
//    //    cpaCampaignMaster.setExceptMeant(exceptMeant);
//
//        // 캠페인 DB 취소 조건
//        cpaCampaignMaster.setCnclData(keyValue.getCancelCond());
//
//        // 캠페인 DB 등록시 SMS 수신여부
//        if ((keyValue.getSmsYn() == null) || (keyValue.getSmsYn().equals("")))
//            cpaCampaignMaster.setSmsYn("N");
//        else
//            cpaCampaignMaster.setSmsYn(keyValue.getSmsYn());
//
//        // SMS 수신받을 휴대폰번호
//        cpaCampaignMaster.setSmsNo(keyValue.getSmsNo());
//
//        // 랜딩페이지 상단 창 제목
//    //    cpaCampaignMaster.setLandingPageTitle((String)rq.get("landingPageTitle"));
//
//        // 광고 의뢰시 참조할 자사 랜딩페이지
//    //    cpaCampaignMaster.setLandingUrl((String)rq.get("landingUrl"));
//    //
//        // 배너 경로/파일명
////        cpaCampaignMaster.setBannerPath(srcFullName);
//
//    // 자동 확정 일수
////    if ((autoConfirm == null) || (autoConfirm.equals("")))
////        cpaCampaignMaster.setAutoConfirm(7L);
////    else
////        cpaCampaignMaster.setAutoConfirm(Long.parseLong(autoConfirm));
//
//        // 기본승인률
//        if ((keyValue.getApproval() == null) || (keyValue.getApproval().equals("")))
//            cpaCampaignMaster.setApproval(50.00);
//        else
//            cpaCampaignMaster.setApproval(Double.parseDouble(keyValue.getApproval()));
//
//        // 무효 조건
//        cpaCampaignMaster.setNullifyCond(keyValue.getNullifyCond());
//
//        // 취소 조건
//        cpaCampaignMaster.setCancelCond(keyValue.getCancelCond());
//
//        // 선호 채널
//        cpaCampaignMaster.setBanExChannelCond(keyValue.getBanExChannelCond());
//
//        // 금지 채널
//        cpaCampaignMaster.setBanChannelCond(keyValue.getBanChannelCond());
//
//        // 금지 이미지
//        cpaCampaignMaster.setBanImageCond(keyValue.getBanImageCond());
//
//        // 금지 단어
//        cpaCampaignMaster.setBanWordCond(keyValue.getBanWordCond());
//
//        // 연령제한
//        if( (keyValue.getAgeTarget() == null) || (keyValue.getAgeTarget().equals("")) ) {
//            cpaCampaignMaster.setAgeTarget("");
//        }
//        else {
//            if(keyValue.getAgeTarget().equals("N"))
//                cpaCampaignMaster.setAgeTarget("");
//            else
//                cpaCampaignMaster.setAgeTarget(keyValue.getAgeTargetFrom() + "|" + keyValue.getAgeTargetTo());
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
//        if ((keyValue.getSmsYn() == null) || (keyValue.getSmsYn().equals("")))
//            adAdvertBalance.setSmsSendYn("N");
//        else
//            adAdvertBalance.setSmsSendYn(keyValue.getSmsYn());
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
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

