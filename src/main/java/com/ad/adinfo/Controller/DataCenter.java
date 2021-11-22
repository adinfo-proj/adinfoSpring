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
     *         [R] CPA_CAMPAIGN_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/DataCenter/Summary", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public JSONObject DataCenterBySummary(HttpServletRequest rq) throws Exception {
        JSONObject      returnObj = new JSONObject();

        String      srtDt = rq.getParameter("srtDt");
        String      endDt = rq.getParameter("endDt");

        System.out.println("srtDt : [" + rq.getParameter("srtDt") + "]");
        System.out.println("endDt : [" + rq.getParameter("endDt") + "]");
        System.out.println("mbId  : [" + rq.getParameter("mbId" ) + "]");
        System.out.println("caId  : [" + rq.getParameter("caId" ) + "]");

        //------------------------------------------------------------------------
        // 당일일자를 조회한다. (YYYYMMDD)
        //------------------------------------------------------------------------
        String toDateDate = dateCalc.DateInterval(0);

        //------------------------------------------------------------------------
        // Summary - Header
        //------------------------------------------------------------------------
        {
            ArrayList<Map<String, Object>> liveArr = new ArrayList<Map<String, Object>>();

            //------------------------------------------------------------------------
            // 참여 마케터의 수
            //------------------------------------------------------------------------
            returnObj.put("mktCount", dataCenterMapper.DataCenterBySummaryForMaketerCount(375L));
            liveArr.add(returnObj);

            System.out.println("liveArr 1 : [" + liveArr + "]");

            //------------------------------------------------------------------------
            // 당일 DB 접수건
            //------------------------------------------------------------------------
            returnObj.put("todayDbCount", dataCenterMapper.DataCenterBySummaryForTodayDBCount(0L, 0L, "11"));
            liveArr.add(returnObj);

            System.out.println("liveArr 2 : [" + liveArr + "]");

            //------------------------------------------------------------------------
            // 누적 DB 접수건
            //------------------------------------------------------------------------
            returnObj.put("allSumDbCount", dataCenterMapper.DataCenterBySummaryForSumDBCount(srtDt, endDt, 0L, 0L));
            liveArr.add(returnObj);

            System.out.println("liveArr 3 : [" + liveArr + "]");

            //------------------------------------------------------------------------
            // 누적 유효 DB 접수건
            //------------------------------------------------------------------------
            returnObj.put("allValidDbCount", dataCenterMapper.DataCenterBySummaryForValidDBCount(srtDt, endDt,0L, 0L, "Y"));
            liveArr.add(returnObj);

            System.out.println("liveArr 4 : [" + liveArr + "]");

            //------------------------------------------------------------------------
            // 누적 무효 DB 접수건
            //------------------------------------------------------------------------
            returnObj.put("allInValidDbCount", dataCenterMapper.DataCenterBySummaryForValidDBCount(srtDt, endDt, 0L, 0L, "C"));
            liveArr.add(returnObj);

            System.out.println("liveArr 5 : [" + liveArr + "]");

            //------------------------------------------------------------------------
            // 누적 전체 캠페인 중 중복 DB 접수건
            //------------------------------------------------------------------------
            returnObj.put("allDupDbCount", dataCenterMapper.DataCenterBySummaryForAllDupDBCount(srtDt, endDt, 0L, 0L));
            liveArr.add(returnObj);

            System.out.println("liveArr 6 : [" + liveArr + "]");

            //------------------------------------------------------------------------
            // 누적 해당 캠페인 중 중복 DB 접수건
            //------------------------------------------------------------------------
            returnObj.put("thisDupDbCount", dataCenterMapper.DataCenterBySummaryForThisDupDBCount(srtDt, endDt, 0L, 0L));
            liveArr.add(returnObj);

            System.out.println("liveArr 7 : [" + liveArr + "]");

//            returnObj.put("liveCount", liveArr.size());
//            returnObj.put("liveResult", liveArr);
        }

        System.out.println("returnObj : [" + "]");

        return returnObj;
    }
}