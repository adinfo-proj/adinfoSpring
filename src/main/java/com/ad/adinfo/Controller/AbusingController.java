package com.ad.adinfo.Controller;

import com.ad.adinfo.Mapper.CampaignMasterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
public class AbusingController {

    private final CampaignMasterMapper campaignMasterMapper;

    /*------------------------------------------------------------------------------------------------------------------
     * 입력항목 정보 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.28
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
    @RequestMapping(value = "AbusingInputDataCondition", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String AbusingInputDataCondition(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("AbusingInputDataCondition Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterAskList Start");
        System.out.println("----------------------------------------------------------------------------");

        List<Map<String, Object>> stAskList = campaignMasterMapper.getCampaignMasterAskList(
                  Long.parseLong(rq.getParameter("mbId").toString())
                , Long.parseLong(rq.getParameter("adId").toString())
                , Long.parseLong(rq.getParameter("caId").toString())
        );
        String  parStr = stAskList.get(0).get("askList").toString();
        System.out.println("리턴 메세지 : ["+ parStr.replaceAll(",-", "") +"]");
        return parStr.replaceAll(",-", "");
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 입력항목 값 정보 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.28
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
    @RequestMapping(value = "GetInputValueList", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> GetInputValueList(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetInputValueList Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId      : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId      : [" + rq.getParameter("adId") + "]");
        System.out.println("caId      : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId      : [" + rq.getParameter("pgId") + "]");
        System.out.println("askSelect : [" + rq.getParameter("askSelect") + "]");

        List<String>   resultMap = new ArrayList<String>();

        if     ( Long.parseLong(rq.getParameter("askSelect").toString()) == 0 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList03 Start");
            System.out.println("----------------------------------------------------------------------------");

            resultMap = campaignMasterMapper.getInputValueList03(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else if( Long.parseLong(rq.getParameter("askSelect").toString()) == 1 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList04 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList04(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else if( Long.parseLong(rq.getParameter("askSelect").toString()) == 2 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList05 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList05(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else if( Long.parseLong(rq.getParameter("askSelect").toString()) == 3 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList06 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList06(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else if( Long.parseLong(rq.getParameter("askSelect").toString()) == 4 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList07 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList07(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else if( Long.parseLong(rq.getParameter("askSelect").toString()) == 5 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList08 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList08(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else if( Long.parseLong(rq.getParameter("askSelect").toString()) == 6 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList09 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList09(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }
        else { // if( Long.parseLong(rq.getParameter("askSelect").toString()) == 7 ) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getInputValueList10 Start");
            System.out.println("----------------------------------------------------------------------------");
            resultMap = campaignMasterMapper.getInputValueList10(
                      Long.parseLong(rq.getParameter("mbId").toString())
                    , Long.parseLong(rq.getParameter("adId").toString())
                    , Long.parseLong(rq.getParameter("caId").toString())
                    , Long.parseLong(rq.getParameter("pgId").toString())
            );
        }

        System.out.println("리턴 메세지 : ["+ resultMap.toString() +"]");

        return resultMap;
    }
}





















