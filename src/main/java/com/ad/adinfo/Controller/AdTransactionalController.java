package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.AD_USER_MASTER;
import com.ad.adinfo.Domain.TB_AD_TRANSACTIONAL;
import com.ad.adinfo.Mapper.AdTransactionalMapper;
import com.ad.adinfo.Mapper.AdUserMasterMapper;
import com.ad.adinfo.Mapper.AdUtilityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdTransactionalController {
    private final AdTransactionalMapper adTransactionalMapper;
    private final AdUserMasterMapper    adUserMasterMapper;
    private final AdUtilityMapper       adUtilityMapper;

    /*------------------------------------------------------------------------------------------------------------------
     * 결제 처리
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
    @RequestMapping(value = "/CreateAdTransactional", method = RequestMethod.GET)
    public Map<String, Object> CreateAdTransactional(HttpServletRequest params)
//            NativeWebRequest nativeWebRequest,
//            @RequestHeader                   Map<String, Object> rHeader,
//            @RequestPart (value = "dataObj") Map<String, Object> params ) throws Exception
    {
        System.out.println("\n############################################################################");
        System.out.println("CreateAdTransactional Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
//        System.out.println("헤더            : [" + rHeader + "]");
        System.out.println("dataObj 파라메터 : [" + params.toString() + "]");

        HashMap<String, Object> resMap          = new HashMap<String, Object>();
        Map<String, Object>     AdTransData     = new HashMap<String, Object>();
        AD_USER_MASTER          adUserMaster    = new AD_USER_MASTER();
        TB_AD_TRANSACTIONAL     tbAdTransactional = new TB_AD_TRANSACTIONAL();

        //-------------------------------------------------------------------
        // 결제 요청한 고객의 정보를 조회한다.
        //-------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adUserMasterMapper.getAdUserMaster Start");
        System.out.println("----------------------------------------------------------------------------");
        adUserMaster = adUserMasterMapper.getAdUserMaster(params.getParameter("clntId").toString());
        if(adUserMaster == null || adUserMaster.toString() == "") {
            resMap.put("status", false);
            resMap.put("message", "결제한 회원의 정보가 없습니다.");
            return resMap;
        }

        // kellyis@daum.net

        //-------------------------------------------------------------------
        // 기 결제내역을 조회한다.
        //-------------------------------------------------------------------
//        System.out.println("----------------------------------------------------------------------------");
//        System.out.println("  adTransactionalMapper.GetAdTransactionalLastOne Start");
//        System.out.println("----------------------------------------------------------------------------");
//        AdTransData = adTransactionalMapper.GetAdTransactionalLastOne(params.get("clntId").toString());

        //-------------------------------------------------------------------
        // 회원정보 만기일 기준으로 결제월수를 산출한다.
        //-------------------------------------------------------------------
        String sExpDt = "";
        String sToday = adUtilityMapper.getCurToday();
        String sTime  = adUtilityMapper.getCurTime();
        System.out.println("  adUserMaster.getExpDt() : [" + adUserMaster.getExpDt() + "]");

        if( Long.parseLong(sToday) > Long.parseLong(adUserMaster.getExpDt()) ) {
            sExpDt = adUtilityMapper.getCalcMonth(sToday, 1L);
        }
        // 만기일이 오늘 기준 같거나 남았다면?
        else {
            sExpDt = adUtilityMapper.getCalcMonth(adUserMaster.getExpDt(), 1L);
        }

        tbAdTransactional.setCreDt(sToday);
        tbAdTransactional.setCreTm(sTime);
        tbAdTransactional.setMbId(adUserMaster.getMbId());
        tbAdTransactional.setAdId(adUserMaster.getAdId());
        tbAdTransactional.setClntId(adUserMaster.getClntId());
        tbAdTransactional.setGradeCd(adUserMaster.getGradeCd());
        tbAdTransactional.setSummaryCd("00005");
        tbAdTransactional.setAmount(Long.parseLong(params.getParameter("price").toString()));
        tbAdTransactional.setAmount(Long.parseLong(params.getParameter("vat").toString()));

        try {
            Long lResult = adTransactionalMapper.InsAdTransactional(tbAdTransactional);
            if( lResult <= 0) {
                resMap.put("status" , false);
                resMap.put("message", "결제정보 생성에 실패하였습니다.");
                System.out.println("처리 메세지 : [" + resMap.toString() + "]");
                return resMap;
            }
        } catch (RuntimeException e) {
            log.error("로그인 실패", e);
            resMap.put("status" , false);
            resMap.put("message", "결제정보 생성에 실패하였습니다.");
            System.out.println("처리 메세지 : [" + resMap.toString() + "]");
            return resMap;
        }

        resMap.put("status" , true);
        resMap.put("message", "정상적으로 결제가 처리되었습니다.");
        System.out.println("처리 메세지 : [" + resMap.toString() + "]");
        return resMap;
    }
}
