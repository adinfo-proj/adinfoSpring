package com.ad.adinfo.Controller;

import com.ad.adinfo.Mapper.AdUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class LandingPageController {
    private final AdUtility utility;

    /*------------------------------------------------------------------------------------------------------------------
     * 신규 랜딩페이지 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.01.05
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
    @RequestMapping(value = "/newlandingpage", method = RequestMethod.POST)
    public Map<String, Object> insCampaignMaster(
            @RequestHeader Map<String, Object> rHeader,
            @RequestParam(value = "upFile", required = false) MultipartFile upFile,
            @RequestPart (value = "dataObj")                  Map<String, Object> params) throws Exception
    {
        //UUID uuid                = UUID.randomUUID();

        System.out.println("  ==> newlandingpage");
        System.out.println("params       : [" + params + "]");
        System.out.println("upFile       : [" + upFile.getSize() + "]");

        return rHeader;
    }
}