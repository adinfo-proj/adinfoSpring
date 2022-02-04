package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.COMMON_CODE;
import com.ad.adinfo.Domain.CPA_MASTER;
import com.ad.adinfo.Mapper.CommonCodeMapper;
import com.ad.adinfo.Mapper.CpaMasterMapper;
import com.ad.adinfo.Service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CommonAPI {

    private final CommonCodeMapper commonCode;
    private final CpaMasterMapper cpaCampaign;

    @Autowired
    private JwtService jwtService;

    /*------------------------------------------------------------------------------------------------------------------
     * 광고주 캠페인명 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.08.10
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] CPA_MASTER
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/CommonCode/GetCaName", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> getCampaignSymbolList(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();
        List<CPA_MASTER> cpaList = cpaCampaign.getCpaMasterByRownumAndLimit("DESC", 0L, 10L);

        cpaList.forEach(cpaLst -> {
            Map<String, Object> result = new HashMap<String, Object>();

            result.put("id", cpaLst.getCaCaId());
            result.put("caName", cpaLst.getCaName());

            cpaResult.add(result);
        });

        return cpaResult;
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
    @RequestMapping(value = "/CommonCode/getCommonByTp", method = RequestMethod.GET)
    public List<COMMON_CODE> getCommonByTp(HttpServletRequest rq) throws Exception {
        System.out.println("tp       : [" + rq.getParameter("tp") + "]");
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
    @RequestMapping(value = "/CommonCode/getCommonCodeByCode", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<COMMON_CODE> getCommonCodeByCode(HttpServletRequest rq) throws Exception {
        System.out.println("tp : [" + rq.getParameter("tp") + "]");
        System.out.println("code : [" + rq.getParameter("code") + "]");
        return commonCode.getCommonCodeByCode(rq.getParameter("tp"), rq.getParameter("code"));
    }
}
