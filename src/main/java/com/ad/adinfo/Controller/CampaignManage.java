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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

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
     * http://192.168.0.20:30000/InsCampaign?caName=%ED%85%8C%EC%8A%A4%ED%8A%B8&caKind=%EC%9C%A1%EC%95%84&caPrice=35000&caPromotionPrice=0&caSnsTp=N&caSnsFormTp=N&caPotenYn=Y&caDayLimit=30&caRegIp=127.0.0.1&caSrtDt=20210801&caEndDt=20211231&caComment=Test&caReferId=None&caAskList=%EC%A0%84%ED%99%94%EB%B2%88%ED%98%B8,%EC%9D%B4%EB%A6%84&caExceptMeant=%EC%9A%95%EC%84%A4&caReqMeant=%EC%A0%84%ED%99%94%EB%B2%88%ED%98%B8&caCnclData=%EC%A0%84%ED%99%94%EB%B2%88%ED%98%B8%EC%A4%91%EB%B3%B5&caSmsYn=Y&caSms=01024068222&caRefUrl=null&caAutoConfirm=7
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @GetMapping("/manage/newcampaign")
    @ResponseStatus(value = HttpStatus.OK)
    //public Long insCampaignMaster(HttpServletRequest rq, @RequestParam("upFile") List<MultipartFile> files) throws Exception
    public Long insCampaignMaster(HttpServletRequest rq) throws Exception
    {
        CAMPAIGN_MASTER     cpaCampaignMaster   = new CAMPAIGN_MASTER();
        AD_ADVERT_BALANCE   adAdvertBalance     = new AD_ADVERT_BALANCE();

        UUID uuid                = UUID.randomUUID();
        Long newCaId             = 0L;

//        String srcFullName      = "";
//        //---------------------------------------------------------------------------------------------------------
//        // 업로드한 파일을 서버 폴더에 저장한다.
//        //---------------------------------------------------------------------------------------------------------
//        //for(MultipartFile file : files) {
//        String ImgExtention     = FilenameUtils.getExtension(files.getOriginalFilename());
//
//        //-------------------------------------------------------------------
//        // 업로드되는 파일이 있는 경우 파일을 저장한다.
//        //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
//        //-------------------------------------------------------------------
//        if(!files.isEmpty()) {
//            // 디렉토리 + 임의값 + .확장자
//            srcFullName = "/adinfo/images/ad/" + uuid + "." + ImgExtention;
//            files.transferTo(new File(srcFullName));
//        }
//        //}

        System.out.println("uuid : [" + uuid + "]");

        System.out.println("rq mbId             : [" + rq.getParameter("mbId") + "]");
        System.out.println("rq adKind           : [" + rq.getParameter("adKind") + "]");
        System.out.println("rq adArea           : [" + rq.getParameter("adArea") + "]");
        System.out.println("rq adSrtDt          : [" + rq.getParameter("adSrtDt") + "]");
        System.out.println("rq adSrtTm          : [" + rq.getParameter("adSrtTm") + "]");
        System.out.println("rq adEndDt          : [" + rq.getParameter("adEndDt") + "]");
        System.out.println("rq adEndTm          : [" + rq.getParameter("adEndTm") + "]");
        System.out.println("rq adPurpose        : [" + rq.getParameter("adPurpose") + "]");
        System.out.println("rq adMiddleKind     : [" + rq.getParameter("adMiddleKind") + "]");
        System.out.println("rq adName           : [" + rq.getParameter("adName") + "]");
        System.out.println("rq adComment        : [" + rq.getParameter("adComment") + "]");
        System.out.println("rq adUsp            : [" + rq.getParameter("adUsp") + "]");
        System.out.println("rq smsYn            : [" + rq.getParameter("smsYn") + "]");
        System.out.println("rq smsNo            : [" + rq.getParameter("smsNo") + "]");
        System.out.println("rq adPrice          : [" + rq.getParameter("adPrice") + "]");
        System.out.println("rq adPromotionPrice : [" + rq.getParameter("adPromotionPrice") + "]");
        System.out.println("rq adMinQty         : [" + rq.getParameter("adMinQty") + "]");
        System.out.println("rq dayLimit         : [" + rq.getParameter("dayLimit") + "]");
        System.out.println("rq approval         : [" + rq.getParameter("approval") + "]");
        System.out.println("rq ageTarget        : [" + rq.getParameter("ageTarget") + "]");
        System.out.println("rq ageTargetFrom    : [" + rq.getParameter("ageTargetFrom") + "]");
        System.out.println("rq ageTargetTo      : [" + rq.getParameter("ageTargetTo") + "]");
        System.out.println("rq reqWordCon       : [" + rq.getParameter("reqWordCon") + "]");
        System.out.println("rq banChannel       : [" + rq.getParameter("banChannel") + "]");
        System.out.println("rq banExChannel     : [" + rq.getParameter("banExChannel") + "]");
        System.out.println("rq banImageCond     : [" + rq.getParameter("banImageCond") + "]");
        System.out.println("rq banWordCond      : [" + rq.getParameter("banWordCond") + "]");
        System.out.println("rq nullifyCond      : [" + rq.getParameter("nullifyCond") + "]");
        System.out.println("rq cancelCond       : [" + rq.getParameter("cancelCond") + "]");
        System.out.println("rq autoConfirm      : [" + rq.getParameter("autoConfirm")+ "]");

        //-------------------------------------------------------------------
        // DB생성을 위해 변수를 대입한다.
        //-------------------------------------------------------------------
        // 회원사 ID
        cpaCampaignMaster.setMbId(Long.parseLong(rq.getParameter("mbId")));

        // 로그인 아이디로 광고주ID를 조회한다.
        //Integer adId = adInfoUtil.AdClntIdToAdId(rq.getParameter("clntId"));
        cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId("ad@dbfactory.kr").longValue());
        System.out.println(cpaCampaignMaster.getAdId());

        // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
        //   - MB_ID와 AD_ID 기준으로 CA_ID번호를 산출한다.
        try {
            newCaId = campaignMaster.getCampaignMasterMaxCaId(cpaCampaignMaster.getMbId(), cpaCampaignMaster.getAdId());
            System.out.println("Max CA_ID : [" + newCaId + "]");
            newCaId = (newCaId == null) ? 1000L : newCaId + 1L;
        }
        catch (Exception e) {
            System.out.println("campaignMaster.getCampaignMasterMaxCaId Fail : [" + e + "]");
        }

        cpaCampaignMaster.setCaId(newCaId);

        // 작업자 ID
        cpaCampaignMaster.setOperId(rq.getParameter("operId"));

        // 캠페인 종류(COMMON_CODE:AD_KIND)
        cpaCampaignMaster.setCampaignKind(rq.getParameter("adKind"));

        // 광고 지역(전국/서울/경기/강원/충남/충북/전북/전남/경북/경남/제주/기타)
        //cpaCampaignMaster.setCampaignArea(rq.getParameter("campaignArea"));
        cpaCampaignMaster.setCampaignArea("00");

        // 광고 지역이 기타인 경우 입력값
        cpaCampaignMaster.setCampaignAreaEtc(rq.getParameter("campaignAreaEtc"));

        // 캠페인 상태(COMMON_CODE:CAMPAIGN_STATUS)
        cpaCampaignMaster.setStatus("01");

        // 캠페인명
        cpaCampaignMaster.setName(rq.getParameter("adName"));

        // 캠페인 광고구분(COMMON_CODE:CAMPAIGN_TP)
        cpaCampaignMaster.setTp("A");

        // 광고 대분류(COMMON_CODE:CAMPAIGN_TOP_GROUP)
        cpaCampaignMaster.setTopKind(rq.getParameter("adTopKind"));

        // 광고 중분류(COMMON_CODE:CAMPAIGN_MIDDLE_GROUP)
        cpaCampaignMaster.setMiddleKind(rq.getParameter("adMiddleKind"));

        // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
        cpaCampaignMaster.setPurpose(rq.getParameter("adPurpose"));

        // 광고주 단가
        if((rq.getParameter("adPrice") == null) || (rq.getParameter("adPrice").equals("")))
            cpaCampaignMaster.setPrice(0L);
        else
            cpaCampaignMaster.setPrice(Long.parseLong(rq.getParameter("adPrice").replaceAll(",", "")));

        // 광고주 프로모션 가격
        if((rq.getParameter("adPromotionPrice") == null) || (rq.getParameter("adPromotionPrice").equals("")))
            cpaCampaignMaster.setPromotionPrice(0L);
        else
            cpaCampaignMaster.setPromotionPrice(Long.parseLong(rq.getParameter("adPromotionPrice").replaceAll(",", "")));

        // SNS 광고 가능여부
        if((rq.getParameter("snsTp") == null) || (rq.getParameter("snsTp").equals("")))
            cpaCampaignMaster.setSnsYn("N");
        else
            cpaCampaignMaster.setSnsYn(rq.getParameter("snsTp"));

        // SNS 광고 시 잠재입력 여부
        cpaCampaignMaster.setFormYn(rq.getParameter("formTp"));

        // 잠재고객 처리여부
        cpaCampaignMaster.setPotenYn(rq.getParameter("potenYn"));

        // 외부입력허용
        cpaCampaignMaster.setExternDataYn(rq.getParameter("externDataYn"));

        // 파트너별 일별 DB 접수 제한 건수
        if((rq.getParameter("dayLimit") == null) || (rq.getParameter("dayLimit").equals("")))
            cpaCampaignMaster.setDayLimit(0L);
        else
            cpaCampaignMaster.setDayLimit(Long.parseLong(rq.getParameter("dayLimit")));

        // 등록자 IP
        cpaCampaignMaster.setRegIp(rq.getParameter("regIp"));

        // 캠페인 시작일자
        cpaCampaignMaster.setSrtDt(rq.getParameter("adSrtDt").replaceAll("-", ""));

        // 캠페인 시작시간
        cpaCampaignMaster.setSrtTm(rq.getParameter("adSrtTm").replaceAll(":", ""));

        // 캠페인 종료일자
        cpaCampaignMaster.setEndDt(rq.getParameter("adEndDt").replaceAll("-", ""));

        // 캠페인 종료시간
        cpaCampaignMaster.setEndTm(rq.getParameter("adEndTm").replaceAll(":", ""));

        // 캠페인 상세설명
        cpaCampaignMaster.setComment(rq.getParameter("adComment"));

        // 캠페인 특징
        cpaCampaignMaster.setUsp(rq.getParameter("adUsp"));

        // 대행사의 경우 원광고주 사용자ID(실광고주가 캠페인 현황을 보기 위한 참조ID)
        cpaCampaignMaster.setReferId(rq.getParameter("referId"));

        // 고객이 정보입력 항목
        cpaCampaignMaster.setAskList(rq.getParameter("askList"));

        // 캠페인 필수 문구
        cpaCampaignMaster.setReqWordCond(rq.getParameter("reqWordCond"));

        // 캠페인 제외 문구 요청
        cpaCampaignMaster.setExceptMeant(rq.getParameter("exceptMeant"));

        // 캠페인 DB 취소 조건
        cpaCampaignMaster.setCnclData(rq.getParameter("cancelCond"));

        // 캠페인 DB 등록시 SMS 수신여부
        if((rq.getParameter("smsYn") == null) || (rq.getParameter("smsYn").equals("")))
            cpaCampaignMaster.setSmsYn("N");
        else
            cpaCampaignMaster.setSmsYn(rq.getParameter("smsYn"));

        // SMS 수신받을 휴대폰번호
        cpaCampaignMaster.setSmsNo(rq.getParameter("smsNo"));

        // 랜딩페이지 상단 창 제목
        cpaCampaignMaster.setLandingPageTitle(rq.getParameter("landingPageTitle"));

        // 광고 의뢰시 참조할 자사 랜딩페이지
        cpaCampaignMaster.setLandingUrl(rq.getParameter("landingUrl"));
//
//        // 배너 경로/파일명
//        cpaCampaignMaster.setBannerPath(srcFullName);

        // 자동 확정 일수
        if((rq.getParameter("autoConfirm") == null) || (rq.getParameter("autoConfirm").equals("")))
            cpaCampaignMaster.setAutoConfirm(7L);
        else
            cpaCampaignMaster.setAutoConfirm(Long.parseLong(rq.getParameter("autoConfirm")));

        // 기본승인률
        if((rq.getParameter("approval") == null) || (rq.getParameter("approval").equals("")))
            cpaCampaignMaster.setApproval(50.00);
        else
            cpaCampaignMaster.setApproval(Double.parseDouble(rq.getParameter("approval")));

        // 무효 조건
        cpaCampaignMaster.setNullifyCond(rq.getParameter("nullifyCond"));

        // 취소 조건
        cpaCampaignMaster.setCancelCond(rq.getParameter("cancelCond"));

        // 선호 채널
        cpaCampaignMaster.setBanExChannelCond(rq.getParameter("banExChannel"));

        // 금지 채널
        cpaCampaignMaster.setBanChannelCond(rq.getParameter("banChannel"));

        // 금지 이미지
        cpaCampaignMaster.setBanImageCond(rq.getParameter("banImageCond"));

        // 금지 단어
        cpaCampaignMaster.setBanWordCond(rq.getParameter("banWordCond"));

        System.out.println("Parsing Data : [" + cpaCampaignMaster + "]");

        //-------------------------------------------------------------------
        // 캠페인 마스터에 데이터를 생성한다.
        //-------------------------------------------------------------------
        try {
            campaignMaster.insCampaignMaster(cpaCampaignMaster);
        }
        catch (Exception e) {
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

        System.out.println("getSmsYn : [" + cpaCampaignMaster.getSmsYn() + "]");

        adAdvertBalance.setSmsSendYn(cpaCampaignMaster.getSmsYn());
        adAdvertBalance.setZeroAmtSmsYn("N");

        try {
            adAdvertBalanceMapper.insAdAdvertBalance(adAdvertBalance);
        }
        catch (Exception e) {
            System.out.println("adAdvertBalanceMapper.insAdAdvertBalance Fail : [" + e + "]");
        }

        //-------------------------------------------------------------------
        // SEQ로 생성된 캠페인 아이디를 리턴한다.
        //-------------------------------------------------------------------
        return cpaCampaignMaster.getCaId();
    }
}
