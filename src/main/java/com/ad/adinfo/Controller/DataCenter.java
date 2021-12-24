package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.CPA_DATA;
import com.ad.adinfo.Mapper.CpaData;
import com.ad.adinfo.Mapper.DataCenterMapper;
import com.ad.adinfo.Service.DateCalc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DataCenter {
    private final DateCalc              dateCalc;
    private final DataCenterMapper      dataCenterMapper;
    private final CpaData               cpaData;

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

    /*------------------------------------------------------------------------------------------------------------------
     *  캠페인별 DB 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
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
    @RequestMapping(value = "/GetCpaDataForAll", method = RequestMethod.GET)
    //public List<CPA_DATA> GetCampaignNameLst(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
    public List<CPA_DATA> GetCpaDataForAll(HttpServletRequest params) throws Exception {
        System.out.println("mbId : [" + params.getParameter("mbId") + "]");
        System.out.println("adId : [" + params.getParameter("adId") + "]");
        System.out.println("caId : [" + params.getParameter("caId") + "]");
        System.out.println("mkId : [" + params.getParameter("ptId") + "]");

        System.out.println("srtDt  : [" + params.getParameter("srtDt") + "]");
        System.out.println("endDt  : [" + params.getParameter("endDt") + "]");

        System.out.println("curPage  : [" + params.getParameter("curPage") + "]");
        System.out.println("rowCount : [" + params.getParameter("rowCount") + "]");

        //------------------------------------------------------------------------
        // 라이브중인 캠페인
        //------------------------------------------------------------------------
        //ArrayList<Map<String, Object>> liveArr = new ArrayList<Map<String, Object>>();
        //------------------------------------------------------------------------
        // 회원사의 모든 캠페인을 조회한다.
        //------------------------------------------------------------------------
        List<CPA_DATA> cpaDataArr = cpaData.getCpaDataForMbIdCaIdAdId(
                                                     Long.parseLong(params.getParameter("mbId").toString())
                                                   , Long.parseLong(params.getParameter("adId").toString())
                                                   , Long.parseLong(params.getParameter("caId").toString())
                                                   , 0L
                                                   , "00"
                                                   , params.getParameter("srtDt").toString().replaceAll("-", "")
                                                   , params.getParameter("endDt").toString().replaceAll("-", "")
                                                   , (Long.parseLong(params.getParameter("curPage").toString()) - 1) * Long.parseLong(params.getParameter("rowCount").toString())
                                                   , Long.parseLong(params.getParameter("rowCount").toString()));

        return cpaDataArr;
    }

    /*------------------------------------------------------------------------------------------------------------------
     *  캠페인별 DB 페이지 건수 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.24
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
    @RequestMapping(value = "/GetCpaDataForAllPageCount", method = RequestMethod.GET)
    //public List<CPA_DATA> GetCampaignNameLst(@RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
    public Map<String, Object> GetCpaDataForAllPageCount(HttpServletRequest params) throws Exception {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        System.out.println("mbId : [" + params.getParameter("mbId") + "]");
        System.out.println("adId : [" + params.getParameter("adId") + "]");
        System.out.println("caId : [" + params.getParameter("caId") + "]");

        System.out.println("curPage  : [" + params.getParameter("curPage") + "]");
        System.out.println("rowCount : [" + params.getParameter("rowCount") + "]");

        Long rowTotalCount = cpaData.getCpaDataForMbIdCaIdAdIdRowTotalCount(
                  Long.parseLong(params.getParameter("mbId").toString())
                , Long.parseLong(params.getParameter("adId").toString())
                , Long.parseLong(params.getParameter("caId").toString())
                , 0L
                , "00"
                , params.getParameter("srtDt").toString().replaceAll("-", "")
                , params.getParameter("endDt").toString().replaceAll("-", ""));
        resultObj.put("rowTotalCount", rowTotalCount);
















        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 캠페인별 DB 정보 서머리
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CAMPAIGN_MASTER, CPA_DATA, MAKETER_MASTER, CPA_PAGE_USING_COUNT
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/DataCenter/TopSummary", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject DataCenterSummaryByTop(HttpServletRequest params) throws Exception {
        JSONObject      returnObj = new JSONObject();

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("/DataCenter/TopSummary");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("mbId       : [" + params.getParameter("mbId") + "]");
        System.out.println("adId       : [" + params.getParameter("adId") + "]");
        System.out.println("caId       : [" + params.getParameter("caId") + "]");

        System.out.println("srtDt      : [" + params.getParameter("srtDt") + "]");
        System.out.println("endDt      : [" + params.getParameter("endDt") + "]");

        System.out.println("curPosPage : [" + params.getParameter("curPosPage") + "]");
        System.out.println("rowCount   : [" + params.getParameter("rowCount") + "]");
        System.out.println("----------------------------------------------------------------------------------------------");

        //------------------------------------------------------------------------
        // 캠페인별 참여 마케터의 수
        //------------------------------------------------------------------------
        Long    maketerCount = 0L;
        if( (maketerCount = dataCenterMapper.DataCenterSummaryByTopToMaketer( Long.parseLong(params.getParameter("mbId" ))
                                                                            , Long.parseLong(params.getParameter("caId" )))) == null )
            returnObj.put("maketerCount", 0);
        else
            returnObj.put("maketerCount", maketerCount);

        //------------------------------------------------------------------------
        // 캠페인별 당일 DB 접수 (당일건수 / 전체건수)
        //------------------------------------------------------------------------
        Long    totalDbCount = 0L;
        if( (totalDbCount = dataCenterMapper.DataCenterSummaryByTopToSumDBCount( Long.parseLong(params.getParameter("mbId" ))
                                                                               , Long.parseLong(params.getParameter("adId" ))
                                                                               , Long.parseLong(params.getParameter("caId" )))) == null )
            returnObj.put("totalDbCount", "0");
        else
            returnObj.put("totalDbCount", totalDbCount);

        Long    todayDbCount = 0L;
        try {
            todayDbCount = dataCenterMapper.DataCenterBySummaryForTodayDBCount( Long.parseLong(params.getParameter("mbId" ))
                    , Long.parseLong(params.getParameter("adId" ))
                    , Long.parseLong(params.getParameter("caId" ))
                    , params.getParameter("endDt").replaceAll("-", ""));

        } catch (Exception e) {
            System.out.println(e);
        }

        returnObj.put("todayDbCount", todayDbCount);

        //------------------------------------------------------------------------
        // 캠페인별 유효 DB 건수 (당일건수 / 전체건수 * 100)
        //------------------------------------------------------------------------
        Long  validDbCount = 0L;
        try {
            validDbCount = dataCenterMapper.DataCenterBySummaryForValidDBCount(
                      Long.parseLong(params.getParameter("mbId" ).toString())
                    , Long.parseLong(params.getParameter("adId" ).toString())
                    , Long.parseLong(params.getParameter("caId" ).toString())
                    , "Y");
        } catch (Exception e) {
            System.out.println(e);
        }

        if( validDbCount <= 0L)
            returnObj.put("validDbCount", 0.00);
        else
            returnObj.put("validDbCount", (Double)(Double.longBitsToDouble(validDbCount) / Double.longBitsToDouble(todayDbCount) * 100));

        //------------------------------------------------------------------------
        // 캠페인별 무효 DB 건수 (당일건수 / 전체건수 * 100)
        //------------------------------------------------------------------------
        Long  invalidDbCount = 0L;
        try {
            invalidDbCount = dataCenterMapper.DataCenterBySummaryForInvalidDBCount(
                    Long.parseLong(params.getParameter("mbId" ).toString())
                    , Long.parseLong(params.getParameter("adId" ).toString())
                    , Long.parseLong(params.getParameter("caId" ).toString())
                    , "Y");
        } catch (Exception e) {
            System.out.println(e);
        }

        if( validDbCount <= 0L)
            returnObj.put("invalidDbCount", 0.00);
        else
            returnObj.put("invalidDbCount", (Double)(Double.longBitsToDouble(invalidDbCount) / Double.longBitsToDouble(todayDbCount) * 100));

        //------------------------------------------------------------------------
        // 캠페인별 전체 중복 DB 건수
        //------------------------------------------------------------------------
        Long  allDupDBCount = 0L;
        try {
            allDupDBCount = dataCenterMapper.DataCenterBySummaryForAllDupDBCount(
                      Long.parseLong(params.getParameter("mbId" ).toString())
                    , Long.parseLong(params.getParameter("adId" ).toString())
                    , Long.parseLong(params.getParameter("caId" ).toString()));
        } catch (Exception e) {
            System.out.println(e);
        }

        if( allDupDBCount <= 0L)
            returnObj.put("allDupDBCount", 0L);
        else
            returnObj.put("allDupDBCount", allDupDBCount);

        //------------------------------------------------------------------------
        // 캠페인별 클릭율
        //------------------------------------------------------------------------
        Double  clickPer = 0.00;
        try {
            clickPer = dataCenterMapper.DataCenterBySummaryForClickPer(
                      Long.parseLong(params.getParameter("mbId" ).toString())
                    , Long.parseLong(params.getParameter("adId" ).toString())
                    , Long.parseLong(params.getParameter("caId" ).toString()));
        } catch (Exception e) {
            System.out.println(e);
        }

        if( clickPer <= 0L)
            returnObj.put("clickPer", 0.00);
        else
            returnObj.put("clickPer", clickPer);










        System.out.println("returnObj : [" + returnObj + "]");

        return returnObj;
    }

}