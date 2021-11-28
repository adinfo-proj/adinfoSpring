/*
*  [2021.11.09]
*  아래 기능 구현 방식
*    - 유지보수를 위해 최대한 복잡한 쿼리를 배제하고 개발한다.
*      쿼리는 단순하게 처리하고 빠른 업무 이해를 위해 로직으로 개발을 진행한다.
*      안타깝지만 개발연수가 적은 개발자의 경우 쿼리의 이해도가 그리 높지 않아 코드로 풀어 개발한다.
*      추후 성능상 문제가 되는곳이 발견되면 쿼리 튜닝으로 업무를 개선하기로 한다.
*/

package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.*;
import com.ad.adinfo.Mapper.*;
import com.ad.adinfo.Service.AdInfoUtil;
import com.ad.adinfo.Service.DateCalc;
import com.ad.adinfo.Service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Result;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdinfoAPIController {

    private final CampaignMaster        campaignMaster;
    private final CpaMaster             cpaCampaign;
    private final CpaData               cpaData;
    private final AdAdvertBalance       adAdvertBalance;
    private final AdUserMaster          adUserMaster;
    private final CommonCode            commonCode;
    private final AdInfoUtil            adInfoUtil;
    private final DateCalc              dateCalc;
    private final Utility               utility;
    private final PtUseCampaign         ptUseCampaign;

    @Autowired
    private JwtService jwtService;

    /*------------------------------------------------------------------------------------------------------------------
     * 이전 일자 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.13
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] DateCalc
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "dateRange", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> dateRange(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> dateResult = new ArrayList<Map<String, Object>>();
        HashMap<String, Object>   allObj     = new HashMap<String, Object>();

        String beforeDt = "";
        String currentDt = utility.getCalcCurDt(rq.getParameter("curDt"));

        System.out.println(rq.getParameter("tp"));
        System.out.println(rq.getParameter("curDt"));
        System.out.println(rq.getParameter("befDt"));

        if( rq.getParameter("tp").equals("0") ) {
            System.out.println("당일");

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            String curDt = format1.format(date);

            beforeDt = curDt;
            currentDt = curDt;
        }
        else if( rq.getParameter("tp").equals("1") ) {
            System.out.println("전일");

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            String curDt = format1.format(date);

            currentDt = curDt;
            beforeDt = utility.getCalcDays(curDt, Long.parseLong(rq.getParameter("befDt")) * -1);
        }

        else if( rq.getParameter("tp").equals("2") ) {
            System.out.println("일주");

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            String curDt = format1.format(date);

            currentDt = curDt;
            beforeDt = utility.getCalcWeeks(curDt, Long.parseLong(rq.getParameter("befDt")) * -1);
        }
        else if( rq.getParameter("tp").equals("2") ) {
            System.out.println("보름");

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            String curDt = format1.format(date);

            currentDt = curDt;
            beforeDt = utility.getCalcWeeks(curDt, Long.parseLong(rq.getParameter("befDt")) * -2);
        }
        else if( rq.getParameter("tp").equals("3") ) {
            System.out.println("한달");

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            String curDt = format1.format(date);

            currentDt = curDt;
            beforeDt = utility.getCalcWeeks(curDt, Long.parseLong(rq.getParameter("befDt")) * -2);
        }

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("currentDt", currentDt);
        result.put("beforeDt" , beforeDt);

        dateResult.add(result);

        System.out.println("currentDt : [" + currentDt + "]");
        System.out.println("beforeDt  : [" + beforeDt + "]");

        return dateResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.13
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/CampaignLst", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<CPA_MASTER> CampaignList() {
        return cpaCampaign.getCpaMasterAll();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 접수DB 페이지 처리용 페이지 건수
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.13
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_DATA
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/CampaignPageCount", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Long CampaignPageCount(HttpServletRequest rq) throws Exception {
        String      dbKind = "";
        String      startDt = "";
        String      finishDt = "";

        System.out.println("CampaignPageCount : [Start...]");

        if( rq.getParameter("dbKind") == null )
            dbKind =  "전체DB";
        else
            dbKind =  rq.getParameter("dbKind");

        if( rq.getParameter("startDt") == null ) {
            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            startDt = format1.format(date);
        }
        else {
            startDt = rq.getParameter("startDt").replaceAll("-", "");
        }

        if( rq.getParameter("finishDt") == null ) {
            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
            Date date = new Date();
            finishDt = format1.format(date);
        }
        else {
            finishDt = rq.getParameter("finishDt").replaceAll("-", "");
        }

        Long count =  cpaData.getCpaDataForCaIdAdIdPageCount( Long.parseLong(rq.getParameter("caId"))
                                                            , Long.parseLong(rq.getParameter("adId"))
                                                            , Long.parseLong(rq.getParameter("ptId"))
                                                            , dbKind
                                                            , startDt
                                                            , finishDt);
//        System.out.println("count 1 : [" + String.valueOf(count)+ "]");


        Double dDownCount = (double)count / 10;
//        System.out.println("count 2 : [" + String.valueOf(dDownCount)+ "]");

        Double dPageCount = Math.ceil(dDownCount);
//        System.out.println("count 3 : [" + String.valueOf(dPageCount)+ "]");

        System.out.println("CampaignPageCount : [Start...]");

        return dPageCount.longValue();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인별 상태 합산정보
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.09.02
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_DATA
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/getCpaDataForConfirmGroupCount", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> getCpaDataForConfirmGroupCount(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();

        System.out.println("getCpaDataForConfirmGroupCount : [Start...]");

        String      startDt = rq.getParameter("startDt").replaceAll("-", "");
        String      finishDt = rq.getParameter("finishDt").replaceAll("-", "");

        cpaResult = cpaData.getCpaDataForConfirmGroupCount( Long.parseLong(rq.getParameter("caId"))
                                                          , Long.parseLong(rq.getParameter("adId"))
                                                          , Long.parseLong(rq.getParameter("ptId"))
                                                          , startDt
                                                          , finishDt);

        long        lTotalCount = 0;
        for(int i = 0 ; i < cpaResult.size(); i++) {
            lTotalCount += Long.parseLong(     cpaResult.get(i).get("count").toString()       );
        }

        Map<String, Object> addResult = new HashMap<String, Object>();
        addResult.put("confirmYn", "Z");
        addResult.put("count"    , String.valueOf(lTotalCount));
        cpaResult.add(addResult);

        System.out.println("getCpaDataForConfirmGroupCount : [Finish...]");

        return cpaResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인별 파트너 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.13
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] PT_USE_CAMPAIGN
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/CampaignPtIdList", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> CampaignPtIdList(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();
        List<PT_USE_CAMPAIGN> ptList = ptUseCampaign.CampaignPtIdList(Long.parseLong(rq.getParameter("caId")));

        Map<String, Object> resulto = new HashMap<String, Object>();
        resulto.put("ptId", "전체");
        resulto.put("ptCd", "전체");
        resulto.put("caId", "전체");
        cpaResult.add(resulto);

        for(int i = 0 ; i < ptList.size(); i++) {
            Map<String, Object> result = new HashMap<String, Object>();

            result.put("ptId", ptList.get(i).getPtId());
            result.put("ptCd", ptList.get(i).getPtCd());
            result.put("caId", ptList.get(i).getCaId());

            cpaResult.add(result);
        }

        return cpaResult;
    }

//    @RequestMapping(value = "/AdUserLst", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.OK)
//    public List<AD_USER_MASTER> AdUserMaster() {
//        return adUserMaster.getAdUserMaster();
//    }

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
    @PostMapping("/InsCampaignMaster")
    @ResponseStatus(value = HttpStatus.OK)
    //public Long insCampaignMaster(HttpServletRequest rq, @RequestParam("upFile") List<MultipartFile> files) throws Exception
    public Long insCampaignMaster(HttpServletRequest rq, @RequestParam("upFile") MultipartFile files) throws Exception
    {
        CAMPAIGN_MASTER     cpaCampaignMaster   = new CAMPAIGN_MASTER();
        UUID                uuid                = UUID.randomUUID();

        String srcFullName      = "";
        //---------------------------------------------------------------------------------------------------------
        // 업로드한 파일을 서버 폴더에 저장한다.
        //---------------------------------------------------------------------------------------------------------
        //for(MultipartFile file : files) {
            String ImgExtention     = FilenameUtils.getExtension(files.getOriginalFilename());

            //-------------------------------------------------------------------
            // 업로드되는 파일이 있는 경우 파일을 저장한다.
            //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
            //-------------------------------------------------------------------
            if(!files.isEmpty()) {
                // 디렉토리 + 임의값 + .확장자
                srcFullName = "/adinfo/images/ad/" + uuid + "." + ImgExtention;
                files.transferTo(new File(srcFullName));
            }
        //}

        System.out.println("rq : [" + rq.toString()  + "]");

        //-------------------------------------------------------------------
        // DB생성을 위해 변수를 대입한다.
        //-------------------------------------------------------------------
        // 회원사 ID
        cpaCampaignMaster.setMbId(Long.parseLong(rq.getParameter("mbId")));

        // 로그인 아이디로 광고주ID를 조회한다.
        //Integer adId = adInfoUtil.AdClntIdToAdId(rq.getParameter("clntId"));
        //Integer adId = adInfoUtil.AdClntIdToAdId("ad@dbfactory.kr");
        cpaCampaignMaster.setAdId(adInfoUtil.AdClntIdToAdId("ad@dbfactory.kr").longValue());

        // 캠페인 아이디는 Seq로 자동 증가한다. (1,000부터 시작)
        cpaCampaignMaster.setCaId(0L);

        // 작업자 ID
        cpaCampaignMaster.setOperId(rq.getParameter("operId"));

        // 캠페인 종류(COMMON_CODE:AD_KIND)
        cpaCampaignMaster.setCampaignKind(rq.getParameter("campaignKind"));

        // 광고 지역(전국/서울/경기/강원/충남/충북/전북/전남/경북/경남/제주/기타)
        cpaCampaignMaster.setCampaignArea(rq.getParameter("campaignArea"));

        // 광고 지역이 기타인 경우 입력값
        cpaCampaignMaster.setCampaignAreaEtc(rq.getParameter("campaignAreaEtc"));

        // 캠페인 상태(COMMON_CODE:CAMPAIGN_STATUS)
        cpaCampaignMaster.setStatus("W");

        // 캠페인명
        cpaCampaignMaster.setName(rq.getParameter("name"));

        // 캠페인 광고구분(COMMON_CODE:CAMPAIGN_TP)
        cpaCampaignMaster.setTp("A");

        // 광고 대분류(COMMON_CODE:CAMPAIGN_TOP_GROUP)
        cpaCampaignMaster.setTopKind(rq.getParameter("topKind"));

        // 광고 중분류(COMMON_CODE:CAMPAIGN_MIDDLE_GROUP)
        cpaCampaignMaster.setMiddleKind(rq.getParameter("middleKind"));

        // 캠페인 목적(COMMON_CODE:CAMPAIGN_PURPOSE)
        cpaCampaignMaster.setPurpose(rq.getParameter("purpose"));

        // 광고주 단가
        cpaCampaignMaster.setPrice(Long.parseLong(rq.getParameter("price").replaceAll(",", "")));

        // 광고주 프로모션 가격
        if(rq.getParameter("promotionPrice") != null)
            cpaCampaignMaster.setPromotionPrice(Long.parseLong(rq.getParameter("promotionPrice").replaceAll(",", "")));
        else
            cpaCampaignMaster.setPromotionPrice(0L);

        // SNS 광고 가능여부
        cpaCampaignMaster.setSnsYn(rq.getParameter("snsTp"));

        // SNS 광고 시 잠재입력 여부
        cpaCampaignMaster.setFormYn(rq.getParameter("formTp"));

        // 잠재고객 처리여부
        cpaCampaignMaster.setPotenYn(rq.getParameter("potenYn"));

        // 외부입력허용
        cpaCampaignMaster.setExternDataYn(rq.getParameter("externDataYn"));

        // 파트너별 일별 DB 접수 제한 건수
        cpaCampaignMaster.setDayLimit(Long.parseLong(rq.getParameter("dayLimit")));

        // 등록자 IP
        cpaCampaignMaster.setRegIp(rq.getParameter("regIp"));

        // 캠페인 시작일자
        cpaCampaignMaster.setSrtDt(rq.getParameter("srtDt").replaceAll("-", ""));

        // 캠페인 시작시간
        cpaCampaignMaster.setSrtTm(rq.getParameter("srtTm").replaceAll(":", ""));

        // 캠페인 종료일자
        cpaCampaignMaster.setEndDt(rq.getParameter("endDt").replaceAll("-", ""));

        // 캠페인 종료시간
        cpaCampaignMaster.setEndTm(rq.getParameter("endTm").replaceAll(":", ""));

        // 캠페인 상세설명
        cpaCampaignMaster.setComment(rq.getParameter("comment"));
        System.out.println("Comment : [" + rq.getParameter("comment") + "]");

        // 캠페인 특징
        cpaCampaignMaster.setUsp(rq.getParameter("usp"));

        // 대행사의 경우 원광고주 사용자ID(실광고주가 캠페인 현황을 보기 위한 참조ID)
        cpaCampaignMaster.setReferId(rq.getParameter("referId"));

        // 고객이 정보입력 항목
        cpaCampaignMaster.setAskList(rq.getParameter("askList"));

        // 캠페인 필수 문구
        cpaCampaignMaster.setReqWordCond(rq.getParameter("reqWordCond"));

        // 캠페인 제외 문구 요청
        cpaCampaignMaster.setExceptMeant(rq.getParameter("exceptMeant"));

        // 캠페인 DB 취소 조건
        cpaCampaignMaster.setCnclData(rq.getParameter("cnclData"));

        // 캠페인 DB 등록시 SMS 수신여부
        cpaCampaignMaster.setSmsYn(rq.getParameter("smsYn"));

        // SMS 수신받을 휴대폰번호
        cpaCampaignMaster.setSmsNo(rq.getParameter("smsNo"));

        // 랜딩페이지 상단 창 제목
        cpaCampaignMaster.setLandingPageTitle(rq.getParameter("landingPageTitle"));

        // 광고 의뢰시 참조할 자사 랜딩페이지
        cpaCampaignMaster.setLandingUrl(rq.getParameter("landingUrl"));

        // 배너 경로/파일명
        cpaCampaignMaster.setBannerPath(srcFullName);

        // 자동 확정 일수
        cpaCampaignMaster.setAutoConfirm(Long.parseLong(rq.getParameter("autoConfirm")));

        // 기본승인률
        cpaCampaignMaster.setApproval(Double.parseDouble(rq.getParameter("approval")));

        // 무효 조건
        cpaCampaignMaster.setNullifyCond(rq.getParameter("nullifyCond"));

        // 취소 조건
        cpaCampaignMaster.setCancelCond(rq.getParameter("cancelCond"));

        // 금지 채널
        cpaCampaignMaster.setBanChannelCond(rq.getParameter("banChannelCond"));

        // 금지 이미지
        cpaCampaignMaster.setBanImageCond(rq.getParameter("banImageCond"));

        // 금지 단어
        cpaCampaignMaster.setBanWordCond(rq.getParameter("banWordCond"));

        // 금지 단어
        cpaCampaignMaster.setAgeTarget(rq.getParameter("ageTarget"));

        //-------------------------------------------------------------------
        // DB에 데이터를 생성한다.
        //-------------------------------------------------------------------

        campaignMaster.insCampaignMaster(cpaCampaignMaster);
        System.out.println(String.valueOf(cpaCampaignMaster.getCaId()));

        //-------------------------------------------------------------------
        // SEQ로 생성된 캠페인 아이디를 리턴한다.
        //-------------------------------------------------------------------
        return cpaCampaignMaster.getCaId();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 공통코드 CODE TP 정보 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.12
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] COMMON_CODE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "getCommonByTp", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<COMMON_CODE> getCommonByTp(HttpServletRequest rq) throws Exception {
        System.out.println("tp : [" + rq.getParameter("tp") + "]");
        return commonCode.getCommonByTp(rq.getParameter("tp"));
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 공통코드 CODE 상세 정보 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.11.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] COMMON_CODE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "getCommonCodeByCode", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<COMMON_CODE> getCommonCodeByCode(HttpServletRequest rq) throws Exception {
        System.out.println("tp : [" + rq.getParameter("tp") + "]");
        System.out.println("code : [" + rq.getParameter("code") + "]");
        return commonCode.getCommonCodeByCode(rq.getParameter("tp"), rq.getParameter("code"));
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인 데이터 조회 (캠페인아이디, 광고아이디)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.08.20
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_DATA
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "getCpaDataForCaIdAdId", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> getCpaDataForCaIdAdId(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> allObj    = new HashMap<String, Object>();

        String      startDt = rq.getParameter("startDt").replaceAll("-", "");
        String      finishDt = rq.getParameter("finishDt").replaceAll("-", "");

/*
        System.out.println("caId : [" + rq.getParameter("caId") + "]");
        System.out.println("adId : [" + rq.getParameter("adId") + "]");
        System.out.println("ptId : [" + rq.getParameter("ptId") + "]");
        System.out.println("page : [" + rq.getParameter("page") + "]");

        System.out.println("startDt : [" + startDt + "]");
        System.out.println("finishDt : [" + finishDt + "]");

        System.out.println("dbKind : [" + rq.getParameter("dbKind") + "]");
*/

        Long    lSrt = Long.parseLong(rq.getParameter("page")) - 1;
        if(lSrt > 0L)
            lSrt = (lSrt * 10);
        else
            lSrt = 0L;

//        System.out.println("lSrt : [" + String.valueOf(lSrt) + "]");

        List<CPA_DATA> cpaDataList = cpaData.getCpaDataForCaIdAdId( Long.parseLong(rq.getParameter("caId")),
                                                                    Long.parseLong(rq.getParameter("adId")),
                                                                    Long.parseLong(rq.getParameter("ptId")),
                                                                    rq.getParameter("dbKind"),
                                                                    startDt,
                                                                    finishDt,
                                                                    lSrt
                                                                    );

//        System.out.println("cpaDataList : [" + cpaDataList.toString() + "]");

        for(int i = 0 ; i < cpaDataList.size(); i++) {
            Map<String, Object> result = new HashMap<String, Object>();

            result.put("seqNo"              , cpaDataList.get(i).getSeqNo());
            result.put("updateDt"           , cpaDataList.get(i).getUpdateDt());
            result.put("caId"               , cpaDataList.get(i).getCaId());
            result.put("adId"               , cpaDataList.get(i).getAdId());
            result.put("ptId"               , cpaDataList.get(i).getPtId());
            result.put("price"              , cpaDataList.get(i).getPrice());
            result.put("ptPrice"            , cpaDataList.get(i).getPtPrice());
            result.put("specPrice"          , cpaDataList.get(i).getSpecPrice());
            result.put("bonusSupply"        , cpaDataList.get(i).getBonusSupply());
            result.put("bonusPrice"         , cpaDataList.get(i).getBonusPrice());
            result.put("sponserId"          , cpaDataList.get(i).getSponserId());
            result.put("sponserPrice"       , cpaDataList.get(i).getSponserPrice());
            result.put("beforeAdvtAmt"      , cpaDataList.get(i).getBeforeAdvtAmt());
            result.put("afterAdvtAmt"       , cpaDataList.get(i).getAfterAdvtAmt());
            result.put("insDt"              , cpaDataList.get(i).getInsDt());
            result.put("insTm"              , cpaDataList.get(i).getInsTm());
            result.put("confirmYn"          , cpaDataList.get(i).getConfirmYn());
            result.put("confirmDt"          , cpaDataList.get(i).getConfirmDt());
            result.put("confirmTm"          , cpaDataList.get(i).getConfirmTm());
            result.put("cnclMemo"           , cpaDataList.get(i).getCnclMemo());
            result.put("dupRegIpYn"         , cpaDataList.get(i).getDupRegIpYn());
            result.put("regIp"              , cpaDataList.get(i).getRegIp());
            result.put("url"                , cpaDataList.get(i).getUrl());
            result.put("urlAgent"           , cpaDataList.get(i).getUrlAgent());
            result.put("urlReferer"         , cpaDataList.get(i).getUrlReferer());
            result.put("allMobileDupYn"     , cpaDataList.get(i).getAllMobileDupYn());
            result.put("thisMobileDupYn"    , cpaDataList.get(i).getThisMobileDupYn());
            result.put("valueData"          , cpaDataList.get(i).getValueData());

            if(cpaDataList.get(i).getConfirmYn().equals("N")) {
                result.put("value01", "⨳⨳⨳-⨳⨳⨳⨳-⨳⨳⨳⨳");
                result.put("value02", "⨳⨳⨳");
                result.put("value03", "⨳⨳⨳⨳⨳");
                result.put("value04", "⨳⨳⨳⨳⨳");
                result.put("value05", "⨳⨳⨳⨳⨳");
                result.put("value06", "⨳⨳⨳⨳⨳");
                result.put("value07", "⨳⨳⨳⨳⨳");
                result.put("value08", "⨳⨳⨳⨳⨳");
                result.put("value09", "⨳⨳⨳⨳⨳");
                result.put("value10", "⨳⨳⨳⨳⨳");
                result.put("value11", "⨳⨳⨳⨳⨳");
                result.put("value12", "⨳⨳⨳⨳⨳");
                result.put("value13", "⨳⨳⨳⨳⨳");
                result.put("value14", "⨳⨳⨳⨳⨳");
                result.put("value15", "⨳⨳⨳⨳⨳");
                result.put("value16", "⨳⨳⨳⨳⨳");
                result.put("value17", "⨳⨳⨳⨳⨳");
                result.put("value18", "⨳⨳⨳⨳⨳");
                result.put("value19", "⨳⨳⨳⨳⨳");
                result.put("value20", "⨳⨳⨳⨳⨳");
            } else {
                result.put("value01", cpaDataList.get(i).getValue01());
                result.put("value02", cpaDataList.get(i).getValue02());
                result.put("value03", cpaDataList.get(i).getValue03());
                result.put("value04", cpaDataList.get(i).getValue04());
                result.put("value05", cpaDataList.get(i).getValue05());
                result.put("value06", cpaDataList.get(i).getValue06());
                result.put("value07", cpaDataList.get(i).getValue07());
                result.put("value08", cpaDataList.get(i).getValue08());
                result.put("value09", cpaDataList.get(i).getValue09());
                result.put("value10", cpaDataList.get(i).getValue10());
                result.put("value11", cpaDataList.get(i).getValue11());
                result.put("value12", cpaDataList.get(i).getValue12());
                result.put("value13", cpaDataList.get(i).getValue13());
                result.put("value14", cpaDataList.get(i).getValue14());
                result.put("value15", cpaDataList.get(i).getValue15());
                result.put("value16", cpaDataList.get(i).getValue16());
                result.put("value17", cpaDataList.get(i).getValue17());
                result.put("value18", cpaDataList.get(i).getValue18());
                result.put("value19", cpaDataList.get(i).getValue19());
                result.put("value20", cpaDataList.get(i).getValue20());
            }

            cpaResult.add(result);
        }

        allObj.put("count", cpaResult.size());
        allObj.put("data", cpaResult);

        return cpaResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 광고주 대시보드
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.12
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] COMMON_CODE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/Dashboard", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Object getPtDashboard(HttpServletResponse response) throws Exception {
        HashMap<String, Object> fullObj   = new HashMap<String, Object>();
        HashMap<String, Object> allObj    = new HashMap<String, Object>();
        HashMap<String, Object> returnObj = new HashMap<String, Object>();
        HashMap<String, Object> fetchObj  = new HashMap<String, Object>();

        //-------------------------------------------------------------------
        // 당일 / 전일 일자를 산출한다.
        //-------------------------------------------------------------------
        String curDt    = dateCalc.DateInterval(0);
        String beforeDt = dateCalc.DateInterval(-1);

//        System.out.println("curDt    : [" + curDt + "]");
//        System.out.println("beforeDt : [" + beforeDt + "]");

        //-------------------------------------------------------------------
        // 파트너별 당일 접수된 DB의 건수
        //-------------------------------------------------------------------
        Long todayDbCount = adInfoUtil.GetCpaCampaignDataDateInCount(adInfoUtil.AdClntIdToPtId("ad@dbfactory.kr").longValue(), curDt);
        returnObj.put("todayDbCount", todayDbCount+158);

        //-------------------------------------------------------------------
        // 파트너별 당일 접수된 DB의 건수
        //-------------------------------------------------------------------
        Long beforeDayDbCount = adInfoUtil.GetCpaCampaignDataDateInCount(adInfoUtil.AdClntIdToPtId("ad@dbfactory.kr").longValue(), beforeDt);
        fetchObj.put("beforeDayDbCount", beforeDayDbCount+258);

        //-------------------------------------------------------------------
        // 당일 수익금
        //  - DB확정된 건만 조회
        //-------------------------------------------------------------------

        //-------------------------------------------------------------------
        // 전일 수익금
        //  - DB확정된 건만 조회
        //-------------------------------------------------------------------

        //-------------------------------------------------------------------
        // 파트너 등록이후 현재까지 발생한 총 누적수익금
        //-------------------------------------------------------------------
        allObj.put("allData1", returnObj);
        allObj.put("allData2", fetchObj);

        fullObj.put("viewS", allObj);

        return fullObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 토큰 생성
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.07.20
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @PostMapping(value = "/AccessLogin")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> AccessLogin(@RequestBody TokenResponse req,  HttpServletResponse res) {
        Map<String, Object> resMap = new HashMap<>();
        HttpStatus status = null;

        //-------------------------------------------------------------------
        // 수신된 아이디와 패스워드로 DB를 조회하여 확인한다.
        //-------------------------------------------------------------------
        String sqlAdGrade = adUserMaster.getAdUserMasterForId(req.getEmailId());

        System.out.println("gradeCd 1 : [" + sqlAdGrade + "]");

        if( sqlAdGrade == null || sqlAdGrade == "" ) {
            log.error("1. 로그인 실패");
            resMap.put("status" , "1");
            resMap.put("message", "등록된 사용자가 없습니다.");
            resMap.put("gradeCd", "ZZ");
            status = HttpStatus.ACCEPTED;
        } else {
            sqlAdGrade = adUserMaster.getAdUserMasterForIdPw(req.getEmailId(), req.getEmailPw());

            System.out.println("gradeCd 2 : [" + sqlAdGrade + "]");

            if( sqlAdGrade == null || sqlAdGrade == "" ) {
                log.error("2. 로그인 실패");
                resMap.put("status", "2");
                resMap.put("message", "비밀번호가 틀렸습니다.");
                resMap.put("gradeCd", "ZZ");
                status = HttpStatus.ACCEPTED;
            }
            else {

                System.out.println("gradeCd 3 : [" + sqlAdGrade + "]");

                resMap.put("gradeCd", sqlAdGrade);

                try {
                    TokenResponse loginInfo = new TokenResponse();

                    loginInfo.setEmailId(req.getEmailId());

                    String token = jwtService.create(loginInfo);

                    //System.out.println("token : [" + token + "]");

                    resMap.put("status", "0");
                    resMap.put("emailId", req.getEmailId());
                    resMap.put("authToken", token);
                    status = HttpStatus.ACCEPTED;
                } catch (RuntimeException e) {
                    log.error("로그인 실패", e);
                    resMap.put("status" , "3");
                    resMap.put("message", e.getMessage());
                    status = HttpStatus.ACCEPTED;
                }
            }
        }

        return new ResponseEntity<Map<String, Object>>(resMap, status);
    }

    @CrossOrigin
    @PostMapping(value = "/AccessInfo")
    public ResponseEntity<Map<String, Object>> AccessInfo( @RequestBody TokenResponse param
                                                         , HttpServletResponse res) {
        Map<String, Object> resMap = new HashMap<>();
        HttpStatus status = null;

//        System.out.println("Id : ["+ param.getEmailId()      +"]");
//        System.out.println("Tk : ["+ param.getJwtAuthToken() +"]");

        //-------------------------------------------------------------------
        // 수신된 아이디와 패스워드로 DB를 조회하여 확인한다.
        //-------------------------------------------------------------------
        String sqlAdUserMaster = adUserMaster.getAdUserMasterForIdPw(param.getEmailId(), param.getJwtAuthToken());
        System.out.println(sqlAdUserMaster);
        //System.out.println(param.getJwtAuthToken());

        try {
            jwtService.readToken(param.getJwtAuthToken());
            resMap.put("status", true);
            status = HttpStatus.ACCEPTED;
            System.out.println("************************************************************************************");
            System.out.println("* AccessInfo Alive...");
            System.out.println("************************************************************************************");
        } catch (RuntimeException e) {
            log.error("정보조회 실패", e);
            resMap.put("status", false);
            resMap.put("message", "토큰 시간이 만료되었습니다.");
            status = HttpStatus.ACCEPTED;
            System.out.println("************************************************************************************");
            System.out.println("* AccessInfo Dead...");
            System.out.println("************************************************************************************");
        }

        return new ResponseEntity<Map<String, Object>>(resMap, status);
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 대기 DB 접수 처리
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.08.20
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] CPA_DATA
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "setOneCpaData", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public CPA_DATA setOneCpaData(HttpServletRequest rq) throws Exception {
        CPA_DATA        returnData = new CPA_DATA();

        Long            updateCount = 0L;

        System.out.println("seqNo : [" + rq.getParameter("seqNo") + "]");
        System.out.println("flag  : [" + rq.getParameter("flag") + "]");

        Long res = cpaData.setOneCpaData( rq.getParameter("flag"), Long.parseLong(rq.getParameter("seqNo")) );
        if(res > 0) {
            System.out.println("res : [" + res.toString() + "]");
            returnData = cpaData.getCpaDataForSeqNo(Long.parseLong(rq.getParameter("seqNo")));

            //--------------------------------------------------------------
            // 확정/취소 처리시 확정일자와 시간을 등록한다.
            //--------------------------------------------------------------
            updateCount = cpaData.setOneCpaDataConfirmDttm(rq.getParameter("flag"), Long.parseLong(rq.getParameter("seqNo")));
            System.out.println("DB 변경 일자/시간 업데이트 건수 : [" + updateCount + "]");

            //--------------------------------------------------------------
            // 취소 처리시 광고주 충전금액을 환원시킨다.
            //--------------------------------------------------------------
            if(rq.getParameter("flag").equals("C")) {
                updateCount = adAdvertBalance.setOneAdAdvertBalance(returnData.getAdId(), returnData.getCaId(), returnData.getPrice());
                System.out.println("취소시 환급금액 업데이트 건수 : [" + updateCount + "]");
            }
        }
        else {
            System.out.println("선택한 CPA_DATA에 정보가 없습니다.");
        }

        return returnData;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 회원사별 캠페인 상태별 수
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.11.04
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
    @RequestMapping(value = "GetCampaignStatusCount", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetCampaignStatusCount(HttpServletRequest rq) throws Exception {
        System.out.println("tp : [" + rq.getParameter("adId") + "]");
        return cpaCampaign.GetCampaignStatusCount(Long.parseLong(rq.getParameter("adId")));
    }

































    /*------------------------------------------------------------------------------------------------------------------
     * 대쉬보드 / 라이브 캠페인 / 금액 및 수량확인
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.11.04
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
    @RequestMapping(value = "GetLiveCampaignStatus", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject GetLiveCampaignStatus(HttpServletRequest rq) throws Exception {
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss.SS");

        long reqTime = System.currentTimeMillis();
        String reqTimeStr = dayTime.format(new Date(reqTime));

        JSONArray liveJArray        = new JSONArray();
        JSONArray reviewJArray      = new JSONArray();
        JSONArray reviewDelayJArray = new JSONArray();
        JSONArray pauseJArray       = new JSONArray();
        JSONArray finishJArray      = new JSONArray();

        JSONObject returnObj = new JSONObject();

        System.out.println("tp : [" + rq.getParameter("adId") + "]");

        //------------------------------------------------------------------------
        // 당일일자와 전일일자를 조회한다. (YYYYMMDD)
        //------------------------------------------------------------------------
        String toDateDate = dateCalc.DateInterval(0);
        String beforeDate = dateCalc.DateInterval(-1);

        //------------------------------------------------------------------------
        // 라이브중인 캠페인
        //------------------------------------------------------------------------
        {
            ArrayList<Map<String, Object>> liveArr = new ArrayList<Map<String, Object>>();

            //------------------------------------------------------------------------
            // 회원사의 모든 캠페인을 조회한다.
            //------------------------------------------------------------------------
            liveArr = cpaCampaign.GetCampaignForStatus(Long.parseLong(rq.getParameter("adId")), "00");
            for (int i = 0; i < liveArr.size(); i++) {
                long    lAdId = Long.parseLong(liveArr.get(i).get("adId").toString());
                long    lCaId = Long.parseLong(liveArr.get(i).get("caId").toString());

                JSONObject resultObj = new JSONObject();

                Long lLiveAmt        = cpaCampaign.GetCampaignForRemainAmt  (lAdId, lCaId);
                Long lTodayCount     = cpaCampaign.GetCampaignForDateCount  (lAdId, lCaId, toDateDate);
                Long lBeforeDayCount = cpaCampaign.GetCampaignForDateCount  (lAdId, lCaId, beforeDate);
                Long lAllDayCount    = cpaCampaign.GetCampaignForAllDayCount(lAdId, lCaId);

                resultObj.put("campaignName", liveArr.get(i).get("name"));
                resultObj.put("remainAmt"   , String.valueOf(lLiveAmt));
                resultObj.put("todayCount"  , String.valueOf(lTodayCount));
                resultObj.put("beforeCount" , String.valueOf(lBeforeDayCount));
                resultObj.put("totalCount"  , String.valueOf(lAllDayCount));

                liveJArray.add(resultObj);
            }

            returnObj.put("liveCount", liveJArray.size());
            returnObj.put("liveResult", liveJArray);
        }

        //------------------------------------------------------------------------
        // 심사중인 캠페인
        //------------------------------------------------------------------------
        {
            ArrayList<Map<String, Object>> reviewArr = new ArrayList<Map<String, Object>>();

            //------------------------------------------------------------------------
            // 회원사의 모든 캠페인을 조회한다.
            //------------------------------------------------------------------------
            reviewArr = cpaCampaign.GetCampaignForStatus(Long.parseLong(rq.getParameter("adId")), "03");
            for (int i = 0; i < reviewArr.size(); i++) {
                JSONObject resultObj = new JSONObject();

                resultObj.put("campaignName", reviewArr.get(i).get("name"));
                resultObj.put("updateDt", reviewArr.get(i).get("updateDt"));
                resultObj.put("progressStatus", "50%");
                resultObj.put("operId", reviewArr.get(i).get("operId"));
                resultObj.put("status", "Process");

                reviewJArray.add(resultObj);
            }

            returnObj.put("reviewCount", reviewJArray.size());
            returnObj.put("reviewResult", reviewJArray);
        }

        //------------------------------------------------------------------------
        // 심사 보류중인 캠페인
        //------------------------------------------------------------------------
        {
            ArrayList<Map<String, Object>> reviewDelayArr = new ArrayList<Map<String, Object>>();

            //------------------------------------------------------------------------
            // 회원사의 모든 캠페인을 조회한다.
            //------------------------------------------------------------------------
            reviewDelayArr = cpaCampaign.GetCampaignForStatus(Long.parseLong(rq.getParameter("adId")), "04");
            for (int i = 0; i < reviewDelayArr.size(); i++) {
                JSONObject resultObj = new JSONObject();

                resultObj.put("campaignName", reviewDelayArr.get(i).get("name"));
                resultObj.put("updateDt", reviewDelayArr.get(i).get("updateDt"));
                resultObj.put("progressStatus", "50%");
                resultObj.put("operId", reviewDelayArr.get(i).get("operId"));
                resultObj.put("status", "Process");

                reviewDelayJArray.add(resultObj);
            }

            returnObj.put("reviewDelayCount", reviewDelayJArray.size());
            returnObj.put("reviewDelayResult", reviewDelayJArray);
        }

        //------------------------------------------------------------------------
        // 일시정지 캠페인
        //------------------------------------------------------------------------
        {
            ArrayList<Map<String, Object>> pauseArr = new ArrayList<Map<String, Object>>();

            //------------------------------------------------------------------------
            // 회원사의 모든 캠페인을 조회한다.
            //------------------------------------------------------------------------
            pauseArr = cpaCampaign.GetCampaignForStatus(Long.parseLong(rq.getParameter("adId")), "04");
            for (int i = 0; i < pauseArr.size(); i++) {
                JSONObject resultObj = new JSONObject();

                resultObj.put("campaignName", pauseArr.get(i).get("name"));
                resultObj.put("updateDt", pauseArr.get(i).get("updateDt"));
                resultObj.put("progressStatus", "50%");
                resultObj.put("operId", pauseArr.get(i).get("operId"));
                resultObj.put("status", "Process");

                pauseJArray.add(resultObj);
            }

            returnObj.put("pauseCount", pauseJArray.size());
            returnObj.put("pauseResult", pauseJArray);
        }

        //------------------------------------------------------------------------
        // 종료 캠페인
        //------------------------------------------------------------------------
        {
            ArrayList<Map<String, Object>> finishArr = new ArrayList<Map<String, Object>>();

            //------------------------------------------------------------------------
            // 회원사의 모든 캠페인을 조회한다.
            //------------------------------------------------------------------------
            finishArr = cpaCampaign.GetCampaignForStatus(Long.parseLong(rq.getParameter("adId")), "04");
            for (int i = 0; i < finishArr.size(); i++) {
                JSONObject resultObj = new JSONObject();

                resultObj.put("campaignName"  , finishArr.get(i).get("name"));
                resultObj.put("updateDt"      , finishArr.get(i).get("updateDt"));
                resultObj.put("progressStatus", "50%");
                resultObj.put("operId"        , finishArr.get(i).get("operId"));
                resultObj.put("status"        , "Process");

                finishJArray.add(resultObj);
            }

            returnObj.put("finishCount", finishJArray.size());
            returnObj.put("finishResult", finishJArray);
        }

        long resTime = System.currentTimeMillis();
        String resTimeStr = dayTime.format(resTime);

        System.out.println("걸린시간 : " + (resTime - reqTime)/1000.000);

        return returnObj;
    }
}