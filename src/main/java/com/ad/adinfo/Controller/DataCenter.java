package com.ad.adinfo.Controller;

import com.ad.adinfo.Mapper.DataCenterMapper;
import com.ad.adinfo.Service.DateCalc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DataCenter {
    private final DateCalc              dateCalc;
    private final DataCenterMapper      dataCenterMapper;

    /*------------------------------------------------------------------------------------------------------------------
     * 회원사별 DB 정보 서머리
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.11.10
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
    @RequestMapping(value = "/DataCenter/Dashboard/Summary", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject DataCenterDashboardBySummary(HttpServletRequest rq) throws Exception {
        JSONObject      returnObj = new JSONObject();

        String      srtDt = rq.getParameter("srtDt");
        String      endDt = rq.getParameter("endDt");

        System.out.println("srtDt : [" + rq.getParameter("srtDt") + "]");
        System.out.println("endDt : [" + rq.getParameter("endDt") + "]");
        System.out.println("mbId  : [" + rq.getParameter("mbId" ) + "]");
        System.out.println("caId  : [" + rq.getParameter("caId" ) + "]");

        //------------------------------------------------------------------------
        // 대행사 기준 합산 예치금
        //   - AD_TRANSACTIONAL : 입출금 테이블의 입금내역을 합산한다.
        //------------------------------------------------------------------------
        String    depositAmt = null;
        if( (depositAmt = dataCenterMapper.DataCenterBySummaryForDepositToAd(Long.parseLong(rq.getParameter("mbId" )), Long.parseLong(rq.getParameter("adId" )))) == null )
            returnObj.put("depositAmt", 0);
        else
            returnObj.put("depositAmt", depositAmt);

        //------------------------------------------------------------------------
        // 잔여 예치금
        //   - AD_ADVERT_BALANCE : 충전금 마스터 테이블의 CPA 잔여 내역을 합산한다.
        //------------------------------------------------------------------------
        String    depositAmtRemain = null;
        if( (depositAmtRemain = dataCenterMapper.DataCenterBySummaryForChargeAmtToAd(Long.parseLong(rq.getParameter("mbId" )), Long.parseLong(rq.getParameter("adId" )), "T")) == null )
            returnObj.put("depositAmtRemain", 0);
        else
            returnObj.put("depositAmtRemain", depositAmtRemain);

        System.out.println("returnObj : [" + returnObj + "]");

        //------------------------------------------------------------------------
        // 잔여 합산 충전금
        //------------------------------------------------------------------------
        String    chargeAmt = null;
        if( (chargeAmt = dataCenterMapper.DataCenterBySummaryForChargeAmtToAd(Long.parseLong(rq.getParameter("mbId" )), Long.parseLong(rq.getParameter("adId" )), "A")) == null )
            returnObj.put("chargeAmt", 0);
        else
            returnObj.put("chargeAmt", chargeAmt);

        returnObj.put("returnAmt", "5,000");

        System.out.println("returnObj : [" + returnObj + "]");

        return returnObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * DASHBOARD -> Live 그리드
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.11.30
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
    @RequestMapping(value = "/DataCenter/Dashboard/LiveGrid", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> DataCenterDashboardByLiveGrid(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();

        System.out.println("mbId  : [" + rq.getParameter("mbId" ) + "]");
        System.out.println("adId  : [" + rq.getParameter("adId" ) + "]");

        //------------------------------------------------------------------------
        // 캠페인명 조회
        //------------------------------------------------------------------------
        List<Map<String, Object>> objList = dataCenterMapper.DataCenterByLiveGridToMaster(Long.parseLong(rq.getParameter("mbId" )), Long.parseLong(rq.getParameter("adId" )), "00");
        System.out.println("objList  : [" + objList + "]");

        for(int i = 0 ; i < objList.size(); i++) {
            Map<String, Object> result = new HashMap<String, Object>();

//            result.put("ptId", ptList.get(i).getPtId());
//            result.put("ptCd", ptList.get(i).getPtCd());
//            result.put("caId", ptList.get(i).getCaId());
//
//            cpaResult.add(result);

            Map<String, Object> maps = objList.get(i);
            System.out.println("maps name  : [" + maps.get("NAME") + "]");

            // 캠페인명
            result.put("name", maps.get("NAME"));

            // 총 충전광고비
            String exchangeAmt = dataCenterMapper.DataCenterBySummaryForExchangeAmt(Long.parseLong(maps.get("MB_ID").toString()), Long.parseLong(maps.get("AD_ID").toString()), Long.parseLong(maps.get("CA_ID").toString()));
            if(exchangeAmt == null)
                result.put("exchangeAmt", 0);
            else
                result.put("exchangeAmt", exchangeAmt);

            // 총 잔여광고비
            String remainAmt = dataCenterMapper.DataCenterBySummaryForAdAdvtyBalanceCampRemainAmt(Long.parseLong(maps.get("MB_ID").toString()), Long.parseLong(maps.get("AD_ID").toString()), Long.parseLong(maps.get("CA_ID").toString()));
            if(remainAmt == null)
                result.put("remainAmt", 0);
            else
                result.put("remainAmt", remainAmt);

            // 이벤트 광고비
            result.put("eventAmt", "300,000");

            // 금일수량
            result.put("todayQty", "10 / 52");

            // 전일수량
            result.put("befdayQty", "6 / 52");

            cpaResult.add(result);
        }

        return cpaResult;
    }
}