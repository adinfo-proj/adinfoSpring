package com.ad.adinfo.Controller;

import com.ad.adinfo.Mapper.AdUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
            @RequestParam(value = "upFile", required = false) List<MultipartFile> upFile,
            @RequestPart (value = "dataObj")                  Map<String, Object> params) throws Exception
    {
        //UUID uuid                = UUID.randomUUID();

        ArrayList   contentArr  = new ArrayList();
        ArrayList   textArr     = new ArrayList();

        Integer contentCount = 0;
        Integer imgCount = 0;
        Integer textCount = 0;
        Integer formCount = 0;

        contentArr  = (ArrayList)params.get("formType");
        textArr     = (ArrayList)params.get("textData");

        System.out.println("  ==> newlandingpage");
        System.out.println("params       : [" + params + "]");
        System.out.println("upFile       : [" + upFile.size() + "]");
        System.out.println("params1       : [" + contentArr + "]");

        //---------------------------------------------------------------------------------------------------------
        // php 파일을 생성한다.
        //   - 생성규칙은 "http://land.dbmaster.co.kr/mb_id/caid/pg_id?....
        //---------------------------------------------------------------------------------------------------------
        OutputStream file = new FileOutputStream("D:/example/file.html");

        //---------------------------------------------------------------------------------------------------------
        // php 파일의 <HEAD> 영역을 설정한다.
        //---------------------------------------------------------------------------------------------------------
        String  headTag =
                "<!DOCTYPE html>"
                        +"\n<html lang=''>"
                        +"\n  <head>"
                        +"\n    <meta charset='utf-8'>"
                        +"\n    <meta http-equiv='X-UA-Compatible' content='IE=edge'>"
                        +"\n    <meta name='viewport' content='width=device-width,initial-scale=1.0'>"
                        +"\n    <title>랜딩페이지 테스트</title>"
                        +"\n    <!-- fontawesome -->"
                        +"\n    <script src='https://kit.fontawesome.com/9448297042.js' crossorigin='anonymous'></script>"
                        +"\n    <!-- icomoon -->"
                        +"\n    <link rel='stylesheet' href='https://d1azc1qln24ryf.cloudfront.net/238202/adinfo/style-cf.css?ycip75'>"
                        +"\n  </head>";

        for(int i = 0 ; i < contentArr.size(); i++) {
            System.out.println("image.get       : [" + contentArr.get(i) + "]");
            if( contentArr.get(i).equals("01")) {
                System.out.println("upFile       : [" + upFile.get(imgCount).getOriginalFilename() + "]");

                UUID uuid                = UUID.randomUUID();
                String ImgExtention = FilenameUtils.getExtension(upFile.get(imgCount).getOriginalFilename());

                //-------------------------------------------------------------------
                // 업로드되는 파일이 있는 경우 파일을 저장한다.
                //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
                //-------------------------------------------------------------------
                // 디렉토리 + 임의값 + .확장자
                String srcFullName = "D:/WebFile/MB_001/banner/" + uuid + "." + ImgExtention;
                upFile.get(imgCount).transferTo(new File(srcFullName));

                headTag += "\n<img src='" + srcFullName + "'></img>";

                imgCount++;
            }
            else if( contentArr.get(i).equals("02")) {
                System.out.println("text.get       : [" + textArr.get(textCount) + "]");

                headTag += "\n" + textArr.get(textCount);

                textCount++;
            }
            else if( contentArr.get(i).equals("03")) {
                formCount++;
            }
        }

        byte[] by=headTag.getBytes();
        file.write(by);
        file.close();

        return rHeader;
    }
}