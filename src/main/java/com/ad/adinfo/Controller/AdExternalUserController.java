package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.TB_AD_EXTERNAL_USER;
import com.ad.adinfo.Domain.TB_AD_POSTBACK_FORMAT;
import com.ad.adinfo.Domain.TB_CAMPAIGN_MASTER;
import com.ad.adinfo.Mapper.AdExternalUserMapper;
import com.ad.adinfo.Service.AdInfoUtil;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdExternalUserController {
    private final AdInfoUtil adInfoUtil;
    private final AdExternalUserMapper  adExternalUserMapper;

    @Autowired
    private PlatformTransactionManager trxManager;

    /*------------------------------------------------------------------------------------------------------------------
     * 신규 외부 사용자 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] AD_EXTERNAL_USER
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/CreExternalUser", method = RequestMethod.POST)
    public Map<String, Object> CreExternalUser(
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception
    {
        System.out.println("\n\n############################################################################");
        System.out.println("CreExternalUser Func Start...");
        System.out.println("############################################################################");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_EXTERNAL_USER tbAdExternalUser = new TB_AD_EXTERNAL_USER();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params.toString() + "]");
        // mbId, adId, caId, pgId 까지만 받는다.

        //------------------------------------------------------------------------------
        // 아이디 생성
        //------------------------------------------------------------------------------
        String sExternalClntId = adExternalUserMapper.getAdExternalUserNewClntId();

        if(sExternalClntId == null || sExternalClntId.isEmpty()) {
            sExternalClntId = "000001";
        }

        System.out.println("Max EXTERNAL_CLNT_ID 1 : [" + sExternalClntId + "]");

        Long newId = Long.parseLong(sExternalClntId) + 1;
        String sExternalClntIdFinal = adInfoUtil.padLeftZeros(String.valueOf(newId), 6);
        System.out.println("Max EXTERNAL_CLNT_ID 2 : [" + sExternalClntIdFinal + "]");

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
//        System.out.println("----------------------------------------------------------------------------");
//        System.out.println("  트랜잭션 Start");
//        System.out.println("----------------------------------------------------------------------------");
        //TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        tbAdExternalUser.setMbId(Long.parseLong(params.get("mbId").toString()));
        tbAdExternalUser.setAdId(Long.parseLong(params.get("adId").toString()));
        tbAdExternalUser.setCaId(Long.parseLong(params.get("caId").toString()));
        tbAdExternalUser.setPgId(Long.parseLong(params.get("pgId").toString()));
        tbAdExternalUser.setClntId(params.get("clntId").toString());
        tbAdExternalUser.setExternalClntId("DM" + sExternalClntIdFinal);
        tbAdExternalUser.setExternalClntPw(params.get("externalPw").toString());
        tbAdExternalUser.setStatus("00");
        tbAdExternalUser.setSrtDt(params.get("srtDt").toString().replaceAll("-", ""));
        tbAdExternalUser.setEndDt(params.get("endDt").toString().replaceAll("-", ""));
        tbAdExternalUser.setDescription(params.get("description").toString());

        System.out.println(tbAdExternalUser.toString());

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adExternalUserMapper.insAdExternalUser Start");
        System.out.println("----------------------------------------------------------------------------");

        Long    newAddUser = -1L;
        try {
            newAddUser = adExternalUserMapper.insAdExternalUser(tbAdExternalUser);
        } catch(Exception e) {
            System.out.println("adExternalUserMapper.insAdExternalUser Fail : [" + e + "]");

            resultMap.put("status", false);
            resultMap.put("externalClntId", "");
            resultMap.put("externalClntPw", "");
            resultMap.put("message", "신규 외부 사용자 등록이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            //trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("status", true);
        resultMap.put("externalClntId", "DM" + sExternalClntIdFinal);
        resultMap.put("externalClntPw", params.get("externalPw").toString());
        resultMap.put("message", "정상적으로 외부 사용자가 등록되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        //trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 신규 외부 사용자 변경
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
    @RequestMapping(value = "/UpdExternalUser", method = RequestMethod.POST)
    public Map<String, Object> UpdExternalUser(
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("UpdExternalUser Func Start...");
        System.out.println("############################################################################");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_EXTERNAL_USER tbAdExternalUser = new TB_AD_EXTERNAL_USER();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params.toString() + "]");

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  트랜잭션 Start");
        System.out.println("----------------------------------------------------------------------------");

        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adExternalUserMapper.selAdExternalUserOne Start");
        System.out.println("----------------------------------------------------------------------------");

        tbAdExternalUser = adExternalUserMapper.selAdExternalUserOne(Long.parseLong(params.get("seqNo").toString()));
        if(tbAdExternalUser == null) {
            resultMap.put("result", false);
            resultMap.put("message", "외부사용자 정보 조회가 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 adExternalUserMapper.selAdExternalUserOne : [" + "에러나씀 ㅠㅠ" + "]");

            trxManager.rollback(trxStatus);
            return resultMap;
        }
        else {
            tbAdExternalUser.setExternalClntPw(params.get("externalClntPw").toString());
            tbAdExternalUser.setStatus(params.get("status").toString());
            tbAdExternalUser.setSrtDt(params.get("srtDt").toString().replaceAll("-", ""));
            tbAdExternalUser.setSrtDt(params.get("endDt").toString().replaceAll("-", ""));
            tbAdExternalUser.setDescription(params.get("description").toString());

            try {
                adExternalUserMapper.updAdExternalUser(tbAdExternalUser);
            } catch(Exception e) {
                System.out.println("adExternalUserMapper.updAdExternalUser Fail : [" + e + "]");

                resultMap.put("result", false);
                resultMap.put("message", "외부 사용자 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
                System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

                trxManager.rollback(trxStatus);
                return resultMap;
            }
        }

        resultMap.put("result", true);
        resultMap.put("message", "외부 사용자 변경이 정상적으로 처리되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        trxManager.commit(trxStatus);
        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 외부 사용자 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.16
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_EXTERNAL_USER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetExternalUserList", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ArrayList<List<Map<String, Object>>> GetExternalUserList(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> userResult = new ArrayList<>();

        System.out.println("\n\n############################################################################");
        System.out.println("GetExternalUserList Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId   : [" + rq.getParameter("pgId") + "]");
        System.out.println("status : [" + rq.getParameter("status") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adExternalUserMapper.selAdExternalUserRowCount Start");
        System.out.println("----------------------------------------------------------------------------");

        List<Map<String, Object>> adExternalUserCount = adExternalUserMapper.selAdExternalUserRowCount(
                  Long.parseLong(rq.getParameter("mbId").toString())
                , Long.parseLong(rq.getParameter("adId").toString())
                , Long.parseLong(rq.getParameter("caId").toString())
                , Long.parseLong(rq.getParameter("pgId").toString())
                , rq.getParameter("status").toString()
        );

        userResult.add(0, adExternalUserCount);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adExternalUserMapper.selAdExternalUser Start");
        System.out.println("----------------------------------------------------------------------------");

        List<Map<String, Object>> adExternalUserArr = adExternalUserMapper.selAdExternalUser(
                  Long.parseLong(rq.getParameter("mbId").toString())
                , Long.parseLong(rq.getParameter("adId").toString())
                , Long.parseLong(rq.getParameter("caId").toString())
                , Long.parseLong(rq.getParameter("pgId").toString())
                , rq.getParameter("status").toString()
        );

        userResult.add(1, adExternalUserArr);

        System.out.println("리턴 메세지 : ["+ rq.toString() +"]");

        return userResult;
    }
}
