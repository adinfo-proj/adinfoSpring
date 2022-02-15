package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.TB_AD_POSTBACK_FORMAT;
import com.ad.adinfo.Domain.TB_CAMPAIGN_MASTER;
import com.ad.adinfo.Domain.TB_LANDING_PAGE;
import com.ad.adinfo.Mapper.AdPostbackFormatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostBackController {
    private final AdPostbackFormatMapper adPostbackFormatMapper;

    @Autowired
    private PlatformTransactionManager trxManager;

    /*------------------------------------------------------------------------------------------------------------------
     * 신규 POSTBACK 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.13
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
    @RequestMapping(value = "/newSendPostback", method = RequestMethod.POST)
    public Map<String, Object> newSendPostback(
            NativeWebRequest nativeWebRequest,
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("newSendPostback Func Start...");
        System.out.println("############################################################################");

        String sAccessFlag = new String();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_POSTBACK_FORMAT   tbAdPostbackFormat = new TB_AD_POSTBACK_FORMAT();
        ArrayList inputArr  = new ArrayList();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params + "]");

        inputArr  = (ArrayList)params.get("inputParam");

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  트랜잭션 Start");
        System.out.println("----------------------------------------------------------------------------");
        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        tbAdPostbackFormat.setClntId(params.get("clntId").toString());
        tbAdPostbackFormat.setMbId(Long.parseLong(params.get("mbId").toString()));
        tbAdPostbackFormat.setAdId(Long.parseLong(params.get("adId").toString()));
        tbAdPostbackFormat.setCaId(Long.parseLong(params.get("caId").toString()));
        tbAdPostbackFormat.setPgId(Long.parseLong(params.get("pgId").toString()));

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.getPostbackMaxpbId Start");
        System.out.println("----------------------------------------------------------------------------");

        Long newPbId = adPostbackFormatMapper.getPostbackMaxpbId(
                  tbAdPostbackFormat.getMbId()
                , tbAdPostbackFormat.getAdId()
                , tbAdPostbackFormat.getCaId()
                , tbAdPostbackFormat.getPgId()
        );
        System.out.println("Max PB_ID : [" + newPbId + "]");
        newPbId = (newPbId == null) ? 10000L : newPbId + 1L;

        tbAdPostbackFormat.setPbId       (newPbId);
        tbAdPostbackFormat.setStatus     ("00");
        tbAdPostbackFormat.setPostbackIo ("O");
        tbAdPostbackFormat.setPostbackUrl(params.get("sendUrl").toString());
        tbAdPostbackFormat.setSendType   (params.get("postBack").toString());
        tbAdPostbackFormat.setSslYn      (params.get("encrypt").toString());

        for(int i = 0 ; i < inputArr.size(); i++) {
            Map<String, Object> inputValue = new HashMap<String, Object>();
            inputValue = (Map<String, Object>) inputArr.get(i);

            //------------------------------------------------------------------------------
            // 고정값 여부
            //------------------------------------------------------------------------------
            if(inputValue.get("tp").toString().equals("true"))
                sAccessFlag += "Y";
            else
                sAccessFlag += "N";

            switch (i) {
                case 0:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=1");
                    }
                    break;
                case 1:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=2");
                    }
                    break;
                case 2:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=3");
                    }
                    break;
                case 3:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=4");
                    }
                    break;
                case 4:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=5");
                    }
                    break;
                case 5:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=6");
                    }
                    break;
                case 6:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=7");
                    }
                    break;
                case 7:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=8");
                    }
                    break;
                case 8:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=9");
                    }
                    break;
                case 9:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=10");
                    }
                    break;
            }
        }

        for(int i = 0 ; i < 10 - inputArr.size(); i++) {
            sAccessFlag += "_";
        }

        tbAdPostbackFormat.setAccessFlag(sAccessFlag);

        Long    rets = 0L;
        try {
            rets = adPostbackFormatMapper.insPostback(tbAdPostbackFormat);
        } catch(Exception e) {
            System.out.println("adPostbackFormatMapper.insPostback Fail : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "신규 포스트백 등록이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("result", true);
        resultMap.put("message", "정상적으로 신규 포스트백이 등록되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * POSTBACK 변경
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.14
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
    @RequestMapping(value = "/updSendPostback", method = RequestMethod.POST)
    public Map<String, Object> updSendPostback(
            NativeWebRequest nativeWebRequest,
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("updSendPostback Func Start...");
        System.out.println("############################################################################");

        String sAccessFlag = new String();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_POSTBACK_FORMAT   tbAdPostbackFormat = new TB_AD_POSTBACK_FORMAT();
        ArrayList inputArr  = new ArrayList();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params + "]");

        inputArr  = (ArrayList)params.get("inputParam");

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  트랜잭션 Start");
        System.out.println("----------------------------------------------------------------------------");

        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        tbAdPostbackFormat.setClntId(params.get("clntId").toString());
        tbAdPostbackFormat.setMbId(Long.parseLong(params.get("mbId").toString()));
        tbAdPostbackFormat.setAdId(Long.parseLong(params.get("adId").toString()));
        tbAdPostbackFormat.setCaId(Long.parseLong(params.get("caId").toString()));
        tbAdPostbackFormat.setPgId(Long.parseLong(params.get("pgId").toString()));
        tbAdPostbackFormat.setPbId(Long.parseLong(params.get("pbId").toString()));

        tbAdPostbackFormat.setStatus     ("00");
        tbAdPostbackFormat.setPostbackIo ("O");
        tbAdPostbackFormat.setPostbackUrl(params.get("sendUrl").toString());
        tbAdPostbackFormat.setSendType   (params.get("postBack").toString());
        tbAdPostbackFormat.setSslYn      (params.get("encrypt").toString());

        for(int i = 0 ; i < inputArr.size(); i++) {
            Map<String, Object> inputValue = new HashMap<String, Object>();
            inputValue = (Map<String, Object>) inputArr.get(i);

            //------------------------------------------------------------------------------
            // 고정값 여부
            //------------------------------------------------------------------------------
            if(inputValue.get("tp").toString().equals("true"))
                sAccessFlag += "Y";
            else
                sAccessFlag += "N";

            switch (i) {
                case 0:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=1");
                    }
                    break;
                case 1:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=2");
                    }
                    break;
                case 2:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=3");
                    }
                    break;
                case 3:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=4");
                    }
                    break;
                case 4:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=5");
                    }
                    break;
                case 5:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=6");
                    }
                    break;
                case 6:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=7");
                    }
                    break;
                case 7:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=8");
                    }
                    break;
                case 8:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=9");
                    }
                    break;
                case 9:
                    if(inputValue.get("tp").toString().equals("true")) {
                        tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    }
                    else {
                        tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=10");
                    }
                    break;
            }
        }

        for(int i = 0 ; i < 10 - inputArr.size(); i++) {
            sAccessFlag += "_";
        }

        tbAdPostbackFormat.setAccessFlag(sAccessFlag);

        Long    rets = 0L;
        try {
            rets = adPostbackFormatMapper.updPostback(tbAdPostbackFormat);
        } catch(Exception e) {
            System.out.println("adPostbackFormatMapper.updPostback Fail : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "포스트백 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("result", true);
        resultMap.put("message", "정상적으로 포스트백이 변경되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포트스백 리스트 (캠페인번호로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_POSTBACK_FORMAT
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetSelPostbackList", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetSelPostbackList(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("\n\n############################################################################");
        System.out.println("GetSelPostbackList Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId   : [" + rq.getParameter("pgId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.selPostbackList Start");
        System.out.println("----------------------------------------------------------------------------");

        resultObj = adPostbackFormatMapper.selPostbackList(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
                , Long.parseLong(rq.getParameter("pgId"))
        );

        System.out.println("리턴 메세지 : ["+ resultObj.toString() +"]");

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포트스백 리스트 (캠페인번호로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_POSTBACK_FORMAT
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetSelPostbackOne", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> GetSelPostbackOne(HttpServletRequest rq) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("\n\n############################################################################");
        System.out.println("GetSelPostbackOne Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId   : [" + rq.getParameter("pgId") + "]");
        System.out.println("pbId   : [" + rq.getParameter("pbId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.selPostbackOne Start");
        System.out.println("----------------------------------------------------------------------------");

        resultMap = adPostbackFormatMapper.selPostbackOne(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
                , Long.parseLong(rq.getParameter("pgId"))
                , Long.parseLong(rq.getParameter("pbId"))
        );

        System.out.println("리턴 메세지 : ["+ resultMap.toString() +"]");

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포스트백 삭제 (상태를 변경함).
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.14
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] AD_POSTBACK_FORMAT
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "ChangePostbackStatus", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> ChangePostbackStatus(HttpServletRequest    rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("ChangePostbackStatus Func Start...");
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
        System.out.println("pgId     : [" + rq.getParameter("pgId") + "]");
        System.out.println("pbId     : [" + rq.getParameter("pbId") + "]");


        System.out.println("status   : [" + rq.getParameter("status") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.changeStatusPostbackMaster Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            lReturn = adPostbackFormatMapper.changeStatusPostbackMaster(
                    Long.parseLong(rq.getParameter("mbId")),
                    Long.parseLong(rq.getParameter("adId")),
                    Long.parseLong(rq.getParameter("caId")),
                    Long.parseLong(rq.getParameter("pgId")),
                    Long.parseLong(rq.getParameter("pbId")),
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

//                adInfoUtil.InsAdOperationHistory("O"
//                        , Long.parseLong(rq.getParameter("mbId"))
//                        , rq.getParameter("clntId").toString()
//                        , "00"
//                        , "["+ rq.getParameter("campName").toString() +"] 캠페인의 상태를 변경하였습니다."
//                );
            }
        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
            resultMap.put("status", false);
            resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");
        }

        System.out.println("리턴 데이터 : ["+ resultMap.toString() +"]");

        return resultMap;
    }
}