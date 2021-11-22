package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.CPA_MASTER;
import com.ad.adinfo.Mapper.CpaMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final CpaMaster cpaCampaign;

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
    @RequestMapping(value = "getCaName", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> getCampaignSymbolList(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();
        List<CPA_MASTER> cpaList = cpaCampaign.getCpaMasterByRownumAndLimit("DESC", 0L, 10L);

        for(int i = 0 ; i < cpaList.size(); i++) {
            Map<String, Object> result = new HashMap<String, Object>();

            result.put("id"    , cpaList.get(i).getCaCaId());
            result.put("caName", cpaList.get(i).getCaName());

            cpaResult.add(result);
        }

        return cpaResult;
    }

}
