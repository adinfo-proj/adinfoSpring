package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.TB_LANDING_PAGE;
import com.ad.adinfo.Mapper.AdPostbackFormatMapper;
import com.ad.adinfo.Mapper.CampaignMasterMapper;
import com.ad.adinfo.Mapper.LandingPageMapper;
import com.ad.adinfo.Service.AdInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class LandingPageController {
    private final AdInfoUtil  adInfoUtil;
    private final LandingPageMapper landingPageMapper;
    private final CampaignMasterMapper campaignMasterMapper;
    private final AdPostbackFormatMapper adPostbackFormatMapper;

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
    public Map<String, Object> newlandingpage(
            NativeWebRequest nativeWebRequest,
            @RequestHeader Map<String, Object> rHeader,
            @RequestParam(value = "upFile", required = false) List<MultipartFile> upFile,
            @RequestPart (value = "dataObj"                 ) Map<String, Object> params) throws Exception
    {
        System.out.println("\n\n############################################################################");
        System.out.println("newlandingpage Func Start...");
        System.out.println("############################################################################");

        Map<String, Object> resultObj = new HashMap<String, Object>();
        TB_LANDING_PAGE tbLandingPage = new TB_LANDING_PAGE();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("헤더        : [" + rHeader + "]");
        System.out.println("입력 파라메터 : [" + params + "]");

        ArrayList   contentArr  = new ArrayList();
        ArrayList   textArr     = new ArrayList();
        ArrayList   formArr     = new ArrayList();
        ArrayList   inputArr    = new ArrayList();

        Integer     imgCount    = 0;
        Integer     textCount   = 0;
        Integer     formCount   = 0;

        String      randChar    = "";

        contentArr  = (ArrayList)params.get("formType");
        textArr     = (ArrayList)params.get("textData");
        formArr     = (ArrayList)params.get("formData");

        try {
            Map<String, Object> inputFormArr = new HashMap<String, Object>();
            if( formArr.isEmpty() == false ) {
                inputFormArr = (Map<String, Object>)formArr.get(0);
                inputArr     = (ArrayList)inputFormArr.get("inputBox");
            }

            //---------------------------------------------------------------------------------------------------------
            // 랜딩페이지 테이블 저장용 버퍼
            //---------------------------------------------------------------------------------------------------------
            tbLandingPage.setMbId(Long.parseLong(params.get("mbId").toString()));
            tbLandingPage.setAdId(Long.parseLong(params.get("adId").toString()));
            tbLandingPage.setMkId(Long.parseLong(params.get("mkId").toString()));
            tbLandingPage.setCaId(Long.parseLong(params.get("caId").toString()));

            //---------------------------------------------------------------------------------------------------------
            // pgId는 최종 정보로 처리하고 최초이면 10,000번부터 시작하자!
            //---------------------------------------------------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  landingPageMapper.selLandingPageMaxCaId Start");
            System.out.println("----------------------------------------------------------------------------");
            Long lNewPgId = landingPageMapper.selLandingPageMaxCaId(
                      Long.parseLong(params.get("mbId").toString())
                    , Long.parseLong(params.get("adId").toString())
                    , Long.parseLong(params.get("mkId").toString())
                    , Long.parseLong(params.get("caId").toString()) ) + 1;
            tbLandingPage.setPgId(lNewPgId);
            tbLandingPage.setUseTp("R");

            //---------------------------------------------------------------------------------------------------------
            // 동일한 랜딩페이지명이 있으면 등록이 불가하다.
            //---------------------------------------------------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  landingPageMapper.selLandingPageDupName Start");
            System.out.println("----------------------------------------------------------------------------");
            Long lDupCount = landingPageMapper.selLandingPageDupName(
                      Long.parseLong(params.get("mbId").toString())
                    , Long.parseLong(params.get("adId").toString())
                    , Long.parseLong(params.get("mkId").toString())
                    , Long.parseLong(params.get("caId").toString())
                    ,                params.get("landingNm").toString());

            if( lDupCount > 0) {
                resultObj.put("status", false);
                resultObj.put("message", "동일한 랜딩페이지명이 존재합니다.");
                System.out.println("리턴 메세지 : ["+ resultObj.toString() +"]");
                return resultObj;
            }
            else {
                tbLandingPage.setName(params.get("landingNm").toString());
            }

            //---------------------------------------------------------------------------------------------------------
            // 캠페인명을 조회한다.
            //---------------------------------------------------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  campaignMasterMapper.getCampaignMasterByMbAdCa Start");
            System.out.println("----------------------------------------------------------------------------");
            String adName = campaignMasterMapper.getCampaignMasterByMbAdCa(
                      Long.parseLong(params.get("mbId").toString())
                    , Long.parseLong(params.get("adId").toString())
                    , Long.parseLong(params.get("caId").toString())
            );

            System.out.println("------------------ Step F");

            if( adName.isEmpty() || (adName == null) ) {
                resultObj.put("status", false);
                resultObj.put("message", "등록된 캠페인정보가 없습니다.");
                System.out.println("리턴 메세지 : ["+ resultObj.toString() +"]");
                return resultObj;
            }
            else {
                tbLandingPage.setAdName(adName);
            }

            //---------------------------------------------------------------------------------------------------------
            // 비지니스 로직 Start
            //---------------------------------------------------------------------------------------------------------
            HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

            String clientIp = request.getHeader("X-Forwarded-For");
            if (StringUtils.isEmpty(clientIp)|| "unknown".equalsIgnoreCase(clientIp)) {
                //Proxy 서버인 경우
                clientIp = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                //Weblogic 서버인 경우
                clientIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getRemoteAddr();
            }

            tbLandingPage.setRegClntId(params.get("clntId").toString());
            tbLandingPage.setRegIp(clientIp);

            //System.out.println("upFile       : [" + upFile.get(0).getSize() + "]");

            //---------------------------------------------------------------------------------------------------------
            // URL주소를 생성 후 사용가능한지 확인한다.
            //---------------------------------------------------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  random.ints Start");
            System.out.println("----------------------------------------------------------------------------");
            Long        dupUrl = 0L;
            do {
                randChar = "";
                Random random = new Random();
                randChar = random.ints(58,123)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(10)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
//                System.out.println("------------------ Step 055 : " + randChar);

                dupUrl = landingPageMapper.selLandingPageDupUrl(randChar);
            } while(dupUrl > 0);

            tbLandingPage.setUrl(randChar);

            //---------------------------------------------------------------------------------------------------------
            // php 파일을 생성한다.
            //   - 생성규칙 : "http://landing.dbmaster.co.kr/mb_id/caid/pg_id?....
            //---------------------------------------------------------------------------------------------------------
            String      indexPath           = "/WebFileClient/ad/" + randChar;
            String      phpTag              = "";
            String      indexFullFileName   = "";

            // Directory Create
            // RandamChar() 함수로 생성한다.
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  전체 경로명 : [" + indexPath + "]");
            System.out.println("----------------------------------------------------------------------------");

            // Main Directory
            Path    mainPath = Paths.get(indexPath);
            Files.createDirectories(mainPath);

            // Image Directory
            Path    imgPath = Paths.get(indexPath + "/img");
            Files.createDirectories(imgPath);

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  이미지 경로명 : [" + imgPath.toString() + "]");
            System.out.println("----------------------------------------------------------------------------");

            // CSS/JS/icomoon Directory Copy
            File    orgFolder = new File("/WebFileClient/Form");
            File    trgFolder = new File(indexPath);
            adInfoUtil.FolderCopy(orgFolder, trgFolder);
            indexFullFileName = indexPath + "/index.php";

            OutputStream file = new FileOutputStream(indexFullFileName);

            //---------------------------------------------------------------------------------------------------------
            // PHP 기본 추가
            //---------------------------------------------------------------------------------------------------------
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/WebFileClient/Script/PostScript.php"), "UTF-8"));

            String strBuf;
            Integer     nRows = 1;
            while ((strBuf = reader.readLine()) != null) {
                phpTag += strBuf + "\n";

                if(nRows == 13) {
                    phpTag += "  $pgUrl  = " + randChar + ";\n";
                    phpTag += "  $mbId   = " + params.get("mbId") + ";\n";
                    phpTag += "  $adId   = " + params.get("mbId") + ";\n";
                    phpTag += "  $mkId   = " + params.get("mbId") + ";\n";
                    phpTag += "  $caId   = " + params.get("caId") + ";\n";
                    phpTag += "  $pgId   = " + lNewPgId + ";\n";
                }

                nRows++;
            }
            reader.close();

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  헤더안에 valid function 추가 Start");
            System.out.println("----------------------------------------------------------------------------");

            //---------------------------------------------------------------------------------------------------------
            // 헤더안에 valid function 추가
            //---------------------------------------------------------------------------------------------------------
            phpTag += "    <script type='text/javascript'>\n";
            phpTag += "      function chk_validate(e) {\n";
            phpTag += "        if(e.agree.checked == false) {\n";
            phpTag += "          alert('개인정보 수집에 동의해주세요')\n";
            phpTag += "          e.agree.focus();\n";
            phpTag += "          return false;\n";
            phpTag += "        }\n";
//            phpTag += "        if(e.value2.value == '') {\n";
//            phpTag += "          alert('이름을 입력해주세요');\n";
//            phpTag += "          e.value2.focus();\n";
//            phpTag += "          return false;\n";
//            phpTag += "        }\n";
//            phpTag += "        if(e.value1.value == '') {\n";
//            phpTag += "          alert('연락처를 입력해주세요');\n";
//            phpTag += "          e.value1.focus();\n";
//            phpTag += "          return false;\n";
//            phpTag += "        }\n";

//            System.out.println("inputArr.size() : " + inputArr.size());

            //---------------------------------------------------------------------------------------------------------
            // 헤더 추가
            //---------------------------------------------------------------------------------------------------------
            for(int j = 0 ; j < inputArr.size() ; j++) {
                Map<String, Object> arrGab = new HashMap<String, Object>();
                arrGab = (Map<String, Object>)inputArr.get(j);

//                System.out.println("arrGab : " + arrGab);

                //-------------------------------------------------------------------
                // valid 체크를 위한 문자열을 만든다.
                //-------------------------------------------------------------------
                String nameValue = "value"  + (j + 1);

//                System.out.println("nameValue : " + nameValue);

                //-------------------------------------------------------------------
                // 입력항목 추가만 되어있고 입력정보가 없는 경우
                //-------------------------------------------------------------------
                if(arrGab.get("values") == null) {
                    System.out.println("arrGab : NULL");
                    continue;
                }
                //-------------------------------------------------------------------
                // 텍스트 박스
                //-------------------------------------------------------------------
                if(arrGab.get("values").equals("textForm")) {
                    String   textNames = arrGab.get("names").toString();

                    phpTag += "        if(e." + nameValue + ".value == '') {\n";
                    phpTag += "          alert('" + textNames + " 정보를 입력해 주세요.');\n";
                    phpTag += "          e." +  nameValue + ".focus();\n";
                    phpTag += "          return false;\n";
                    phpTag += "        }\n";

                }
                //-------------------------------------------------------------------
                // 라디오 박스
                //-------------------------------------------------------------------
                //else if(arrGab.get("values").equals("radioForm")) {
                //    String[] strArr    = arrGab.get("lab").toString().split(",");
                //    String   formNames = arrGab.get("names").toString();
                //
                //    phpTag += "\n        <div class='formInput'>";
                //    phpTag += "\n          <span class='formInputName'>" + formNames + "</span>";
                //
                //    for(int k = 0 ; k < strArr.length; k++) {
                //        phpTag += "\n          <input type='radio' name='" + nameValue + "' id='" + strArr[k] + "'>";
                //        phpTag += "\n          <label for='" + strArr[k] + "'>" + strArr[k] + "</label>";
                //    }
                //    phpTag += "\n        </div>";
                //}
                //-------------------------------------------------------------------
                // 체크 박스
                //-------------------------------------------------------------------
                //else if(arrGab.get("values").equals("checkForm")) {
                //    String[] strArr    = arrGab.get("lab").toString().split(",");
                //    String   formNames = arrGab.get("names").toString();
                //
                //    phpTag += "\n        <div class='formInput'>";
                //    phpTag += "\n          <span class='formInputName'>" + formNames + "</span>";
                //
                //    for(int k = 0 ; k < strArr.length; k++) {
                //        phpTag += "\n          <input type='checkbox' name='" + nameValue + "' id='" + strArr[k] + "'>";
                //        phpTag += "\n          <label for='" + strArr[k] + "'>" + strArr[k] + "</label>";
                //    }
                //    phpTag += "\n        </div>";
                //}
                //-------------------------------------------------------------------
                // 셀렉트 박스
                //-------------------------------------------------------------------
                //else { // if(arrGab.get("values").equals("selForm")) {
                //    String[] strArr    = arrGab.get("lab").toString().split(",");
                //    String   formNames = arrGab.get("names").toString();
                //
                //    phpTag += "\n        <div class='formInput'>";
                //    phpTag += "\n          <span class='formInputName'>" + formNames + "</span>";
                //    phpTag += "\n          <select name='" + nameValue + "'>";
                //
                //    for(int k = 0 ; k < strArr.length; k++) {
                //        phpTag += "\n          <option value='" + strArr[k] + "'>" + strArr[k] + "</option>";
                //    }
                //    phpTag += "\n          </select>";
                //    phpTag += "\n        </div>";
                //}
            }

            phpTag += "        if(confirm('상담을 신청하시겠습니까?') == false) {\n";
            phpTag += "          return false;\n";
            phpTag += "        }\n";
            phpTag += "        e.action = './submit.php';\n";
            phpTag += "      }\n";
            phpTag += "    </script>\n";
            phpTag += "\n  <title>" + params.get("landingNm") + "</title>";
            phpTag += "\n</head>";
            phpTag += "\n<body>";
            phpTag += "\n  <div class='landPrev'>";

//            System.out.println("phpTag : " + phpTag);

            //---------------------------------------------------------------------------------------------------------
            // 헤더안에 valid function 추가
            //---------------------------------------------------------------------------------------------------------

            //---------------------------------------------------------------------------------------------------------
            // 헤더 추가
            //---------------------------------------------------------------------------------------------------------
//            System.out.println("contentArr.size() : " + contentArr.size());
//            System.out.println("contentArr.data() : " + contentArr.toString());

            for(int i = 0 ; i < contentArr.size(); i++) {
                switch(i) {
                    case 0 : tbLandingPage.setType01(contentArr.get(i).toString()); break;
                    case 1 : tbLandingPage.setType02(contentArr.get(i).toString()); break;
                    case 2 : tbLandingPage.setType03(contentArr.get(i).toString()); break;
                    case 3 : tbLandingPage.setType04(contentArr.get(i).toString()); break;
                    case 4 : tbLandingPage.setType05(contentArr.get(i).toString()); break;
                    case 5 : tbLandingPage.setType06(contentArr.get(i).toString()); break;
                    case 6 : tbLandingPage.setType07(contentArr.get(i).toString()); break;
                    case 7 : tbLandingPage.setType08(contentArr.get(i).toString()); break;
                    case 8 : tbLandingPage.setType09(contentArr.get(i).toString()); break;
                    case 9 : tbLandingPage.setType10(contentArr.get(i).toString()); break;
                }

                //-------------------------------------------------------------------
                // 이미지 추가
                //-------------------------------------------------------------------
                if( contentArr.get(i).equals("01")) {
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("  업로드 이미지 파일명 : [" + upFile.get(imgCount).getOriginalFilename() + "]");
                    System.out.println("----------------------------------------------------------------------------");

    //                UUID uuid                = UUID.randomUUID();
                    String ImgExtention = FilenameUtils.getExtension(upFile.get(imgCount).getOriginalFilename());
    //
    //                //-------------------------------------------------------------------
    //                // 업로드되는 파일이 있는 경우 파일을 저장한다.
    //                //   - 파일명이 중복되는 경우가 분명 발생하므로 UUID를 통해 임의 파일명을 만든다.
    //                //-------------------------------------------------------------------
    //                // 디렉토리 + 임의값 + .확장자
                    //String srcFullName = "D:/WebFile/MB_001/banner/" + upFile.get(imgCount).getOriginalFilename() + "." + ImgExtention;
                    String srcFullName = indexPath + "/img/" + upFile.get(imgCount).getOriginalFilename();
                    upFile.get(imgCount).transferTo(new File(srcFullName));

                    phpTag += "\n    <div>";
                    phpTag += "\n      <img src='" + "./img/"+ upFile.get(imgCount).getOriginalFilename() + "'></img>";
                    phpTag += "\n    </div>";
                    phpTag += "\n";

                    switch(i) {
                        case 0 : tbLandingPage.setValue01(srcFullName); break;
                        case 1 : tbLandingPage.setValue02(srcFullName); break;
                        case 2 : tbLandingPage.setValue03(srcFullName); break;
                        case 3 : tbLandingPage.setValue04(srcFullName); break;
                        case 4 : tbLandingPage.setValue05(srcFullName); break;
                        case 5 : tbLandingPage.setValue06(srcFullName); break;
                        case 6 : tbLandingPage.setValue07(srcFullName); break;
                        case 7 : tbLandingPage.setValue08(srcFullName); break;
                        case 8 : tbLandingPage.setValue09(srcFullName); break;
                        case 9 : tbLandingPage.setValue10(srcFullName); break;
                    }

                    imgCount++;
                }
                //-------------------------------------------------------------------
                // 텍스트 추가
                //-------------------------------------------------------------------
                else if( contentArr.get(i).equals("02")) {
//                    System.out.println("text.get       : [" + textArr.get(textCount) + "]");
                    phpTag += "\n    <div>";
                    phpTag += "\n      " + textArr.get(textCount).toString();
                    phpTag += "\n    </div>";
                    phpTag += "\n";

                    switch(i) {
                        case 0 : tbLandingPage.setValue01(textArr.get(textCount).toString()); break;
                        case 1 : tbLandingPage.setValue02(textArr.get(textCount).toString()); break;
                        case 2 : tbLandingPage.setValue03(textArr.get(textCount).toString()); break;
                        case 3 : tbLandingPage.setValue04(textArr.get(textCount).toString()); break;
                        case 4 : tbLandingPage.setValue05(textArr.get(textCount).toString()); break;
                        case 5 : tbLandingPage.setValue06(textArr.get(textCount).toString()); break;
                        case 6 : tbLandingPage.setValue07(textArr.get(textCount).toString()); break;
                        case 7 : tbLandingPage.setValue08(textArr.get(textCount).toString()); break;
                        case 8 : tbLandingPage.setValue09(textArr.get(textCount).toString()); break;
                        case 9 : tbLandingPage.setValue10(textArr.get(textCount).toString()); break;
                    }

                    textCount++;
                }
                //-------------------------------------------------------------------
                // 폼 추가
                //-------------------------------------------------------------------
                else if( contentArr.get(i).equals("03")) {
//                    System.out.println("resultMap() : In");

                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("  입력 폼 추가 Start");
                    System.out.println("----------------------------------------------------------------------------");

                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap    = (Map<String, Object>)formArr.get(formCount);

//                    System.out.println("resultMap() : " + resultMap.toString());

                    phpTag += "\n    <div class='formPrev'>";
                    phpTag += "\n      <form method='post' onsubmit='return chk_validate(this)'>";
                    phpTag += "\n        <input type='hidden' id='mbId" + i + "'          name='mbId'          value='<?php echo $mbId?>'>";
                    phpTag += "\n        <input type='hidden' id='adId" + i + "'          name='adId'          value='<?php echo $adId?>'>";
                    phpTag += "\n        <input type='hidden' id='caId" + i + "'          name='caId'          value='<?php echo $caId?>'>";
                    phpTag += "\n        <input type='hidden' id='mkId" + i + "'          name='mkId'          value='<?php echo $mkId?>'>";
                    phpTag += "\n        <input type='hidden' id='pgId" + i + "'          name='pgId'          value='<?php echo $pgId?>'>";
                    phpTag += "\n        <input type='hidden' id='httpReferer" + i + "'   name='httpReferer'   value='<?php echo $_SERVER['HTTP_REFERER']?>'>";
                    phpTag += "\n        <input type='hidden' id='remoteAddr" + i + "'    name='remoteAddr'    value='<?php echo $_SERVER['REMOTE_ADDR']?>'>";
                    phpTag += "\n        <input type='hidden' id='serverName" + i + "'    name='serverName'    value='<?php echo $_SERVER['SERVER_NAME']?>'>";
                    phpTag += "\n        <input type='hidden' id='requestUri" + i + "'    name='requestUri'    value='<?php echo $_SERVER['REQUEST_URI']?>'>";
                    phpTag += "\n        <input type='hidden' id='httpUserAgent" + i + "' name='httpUserAgent' value='<?php echo $_SERVER['HTTP_USER_AGENT']?>'>";
//                    phpTag += "\n        <input type='text'   id='value2" + i + "'        name='value2'        placeholder='이름을 입력하세요.'>";
//                    phpTag += "\n        <input type='text'   id='value1" + i + "'        name='value1'        placeholder='연락처 (구분없이 입력해주세요)'>";
                    phpTag += "\n";

//                    System.out.println("inputArr In");
//                    System.out.println("inputArr : " + inputArr.toString());

                    for(int j = 0 ; j < inputArr.size() ; j++) {
                        Map<String, Object> arrGab = new HashMap<String, Object>();
                        arrGab = (Map<String, Object>)inputArr.get(j);

//                        System.out.println("arrGab : " + arrGab.toString());

                        //-------------------------------------------------------------------
                        // html 테그중 name을 설정하기 위해 문자열을 만든다.
                        //-------------------------------------------------------------------
                        String namevalue = "value"  + (j + 1);

                        //-------------------------------------------------------------------
                        // 입력항목 추가만 되어있고 입력정보가 없는 경우
                        //-------------------------------------------------------------------
                        if(arrGab.get("values") == null) {
                            System.out.println("arrGab : NULL");
                            continue;
                        }
                        //-------------------------------------------------------------------
                        // 텍스트 박스
                        //-------------------------------------------------------------------
                        if(arrGab.get("values").equals("textForm")) {
//                            System.out.println("textForm In");
                            phpTag += "\n        <input type='text'   name='" + namevalue + "' placeholder='" + arrGab.get("names") + "'>";

                            switch(i) {
                                case 0 : tbLandingPage.setPage01(arrGab.get("names").toString()); break;
                                case 1 : tbLandingPage.setPage02(arrGab.get("names").toString()); break;
                                case 2 : tbLandingPage.setPage03(arrGab.get("names").toString()); break;
                                case 3 : tbLandingPage.setPage04(arrGab.get("names").toString()); break;
                                case 4 : tbLandingPage.setPage05(arrGab.get("names").toString()); break;
                                case 5 : tbLandingPage.setPage06(arrGab.get("names").toString()); break;
                                case 6 : tbLandingPage.setPage07(arrGab.get("names").toString()); break;
                                case 7 : tbLandingPage.setPage08(arrGab.get("names").toString()); break;
                                case 8 : tbLandingPage.setPage09(arrGab.get("names").toString()); break;
                                case 9 : tbLandingPage.setPage10(arrGab.get("names").toString()); break;
                            }

//                            System.out.println("textForm 1 : " + arrGab.get("names").toString());
                        }
                        //-------------------------------------------------------------------
                        // 라디오 박스
                        //-------------------------------------------------------------------
                        else if(arrGab.get("values").equals("radioForm")) {
                            String[] strArr    = arrGab.get("lab").toString().split(",");
                            String   formNames = arrGab.get("names").toString();

//                            System.out.println("radioForm 1 : " + strArr);
//                            System.out.println("radioForm 2 : " + formNames);

                            phpTag += "\n        <div class='formInput'>";
                            phpTag += "\n          <span class='formInputName'>" + formNames + "</span>";

                            for(int k = 0 ; k < strArr.length; k++) {
                                phpTag += "\n          <input type='radio' name='" + namevalue + "' id='" + strArr[k] + "'" + "value='" + strArr[k] + "'>";
                                phpTag += "\n          <label for='" + strArr[k] + "'>" + strArr[k] + "</label>";
                            }
                            phpTag += "\n        </div>";
                        }
                        //-------------------------------------------------------------------
                        // 체크 박스
                        //-------------------------------------------------------------------
                        else if(arrGab.get("values").equals("checkForm")) {
                            String[] strArr    = arrGab.get("lab").toString().split(",");
                            String   formNames = arrGab.get("names").toString();

//                            System.out.println("checkForm 1 : " + strArr);
//                            System.out.println("checkForm 2 : " + formNames);

                            phpTag += "\n        <div class='formInput'>";
                            phpTag += "\n          <span class='formInputName'>" + formNames + "</span>";

                            for(int k = 0 ; k < strArr.length; k++) {
                                phpTag += "\n          <input type='checkbox' name='" + namevalue + "' id='" + strArr[k] + "'" + "value='" + strArr[k] + "'>";
                                phpTag += "\n          <label for='" + strArr[k] + "'>" + strArr[k] + "</label>";
                            }
                            phpTag += "\n        </div>";
                        }
                        //-------------------------------------------------------------------
                        // 셀렉트 박스
                        //-------------------------------------------------------------------
                        else if(arrGab.get("values").equals("selForm")) {
//                            System.out.println("selForm In");



                            String[] strArr    = arrGab.get("lab").toString().split(",");
                            String   formNames = arrGab.get("names").toString();

//                            System.out.println("selForm 1 : " + strArr);
//                            System.out.println("selForm 2 : " + formNames);

                            phpTag += "\n        <div class='formInput'>";
                            phpTag += "\n          <span class='formInputName'>" + formNames + "</span>";
                            phpTag += "\n          <select name='" + namevalue + "'>";

                            for(int k = 0 ; k < strArr.length; k++) {
                                phpTag += "\n          <option value='" + strArr[k] + "'>" + strArr[k] + "</option>";
                            }
                            phpTag += "\n          </select>";
                            phpTag += "\n        </div>";
                        }
                    }

                    //System.out.println("phpTag 11 : " + phpTag);

//                    System.out.println("check 1 : ");

                    phpTag += "\n        <input type='checkbox' name='agree' id='agree" + i + "'>";
                    phpTag += "\n        <label for='agree" + i + "'>개인정보 수집 동의 <span>[보러가기]</span></label>";

                    phpTag += "\n        <div class='centerBox'>";
                    phpTag += "\n          <button style='background: " + resultMap.get("btnColor").toString() + "; ";
                    phpTag += "border-radius: " + resultMap.get("btnShape").toString() +  ";'>";
                    phpTag += resultMap.get("btnNm") + "</button>";

                    phpTag += "\n        </div>";
                    phpTag += "\n      </form>";

//                    System.out.println("check 4 : ");

                    phpTag += "\n      <div class='priBox'>";
                    phpTag += "\n        <h6>" + params.get("stipulationTitle").toString() + "</h6>";
                    phpTag += "\n        <div>" + params.get("stipulationDesc").toString() + "</div>";
                    phpTag += "\n        <button>확인</button>";
                    phpTag += "\n      </div>";
                    phpTag += "\n    </div>";

//                    System.out.println("phpTag 12 : ");

                    formCount++;
                }
            }

//            System.out.println("phpTag 12 : " + phpTag);
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  푸터 추가 Start");
            System.out.println("----------------------------------------------------------------------------");

            //-------------------------------------------------------------------
            // 푸터 추가
            //-------------------------------------------------------------------
            phpTag += "\n    <div class='bgColor'></div>";
            phpTag += "\n  </body>";
            phpTag += "\n</div>";
            phpTag += "\n</html>";

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  최종 파일 PHP 생성 : [" + phpTag + "]");
            System.out.println("----------------------------------------------------------------------------");

//            System.out.println("steps 3 : ");

            //-------------------------------------------------------------------
            // 최종 저장
            //-------------------------------------------------------------------
            byte[] by=phpTag.getBytes();
            file.write(by);
            file.close();

            System.out.println("----------------------------------------------------------------------------");
            System.out.println("  landingPageMapper.insLandingPage Start");
            System.out.println("----------------------------------------------------------------------------");

            landingPageMapper.insLandingPage(tbLandingPage);
        } catch (Exception e) {
            System.out.println(e);

            resultObj.put("status", false);
            resultObj.put("message", "신규 랜딩페이지 생성이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("리턴 메세지 : ["+ resultObj.toString() +"]");
            return resultObj;
        }

        resultObj.put("status", true);
        resultObj.put("message", "신규 랜딩페이지가 생성되었습니다.");
        resultObj.put("landingUrl", "http://www.dbmaster.co.kr/ad/" + randChar);

        System.out.println("리턴 메세지 : ["+ resultObj.toString() +"]");

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 랜딩페이지 리스트 (캠페인별 조회 - 이름과 id만 전달)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.24
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] LANDING_PAGE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetLandingListForMbAdCaCode", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetLandingListForMbAdCaCode(HttpServletRequest rq) throws Exception {
        System.out.println("GetLandingListForMbAdCaCode----------------------");

        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("mkId   : [" + rq.getParameter("mkId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");

        //---------------------------------------------------------------------------------------------------------
        // 랜딩페이지 목록을 조회한다.
        //---------------------------------------------------------------------------------------------------------
        if(rq.getParameter("caId").equals("-1")) {
            return landingPageMapper.GetLandingListForMbAdCaCode(
                      Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("mkId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , rq.getParameter("useTp")
            );
        }
        else {
            return landingPageMapper.GetLandingListForMbAdCaCode(
                      Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("mkId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , rq.getParameter("useTp")
            );
        }
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 랜딩페이지 리스트 (캠페인별 조회 - 데이터포함)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.24
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] LANDING_PAGE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetLandingListForMbAdCa", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ArrayList<List<Map<String, Object>>> GetLandingListForMbAdCa(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetLandingListForMbAdCa Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("mkId   : [" + rq.getParameter("mkId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");

        ArrayList<List<Map<String, Object>>> cpaResult = new ArrayList<>();

        //---------------------------------------------------------------------------------------------------------
        // 랜딩페이지 목록을 조회한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  landingPageMapper.GetLandingListForMbAdCa Start");
        System.out.println("----------------------------------------------------------------------------");

        List<Map<String, Object>> landingObj = new ArrayList<Map<String, Object>>();
        landingObj = landingPageMapper.GetLandingListForMbAdCa(
                      Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("mkId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , rq.getParameter("useTp")
                    , (Long.parseLong(rq.getParameter("curPage").toString()) - 1) * Long.parseLong(rq.getParameter("rowCount").toString())
                    , Long.parseLong(rq.getParameter("rowCount"))
        );

        cpaResult.add(0, landingObj);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  landingPageMapper.GetLandingListForMbAdCaRowCount Start");
        System.out.println("----------------------------------------------------------------------------");

        List<Map<String, Object>> landingCountObj = new ArrayList<Map<String, Object>>();
        landingCountObj = landingPageMapper.GetLandingListForMbAdCaRowCount(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("mkId"))
                , Long.parseLong(rq.getParameter("caId"))
                , rq.getParameter("useTp")
        );

        cpaResult.add(1, landingCountObj);

        return cpaResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 랜딩페이지 포멧 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.24
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] LANDING_PAGE
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetLandingPageFormat", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetLandingPageFormat(HttpServletRequest rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("GetLandingPageFormat Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId   : [" + rq.getParameter("pgId") + "]");

        Map<String, Object>       resultObj = new HashMap<String, Object>();
        List<Map<String, Object>> cpaResult = new ArrayList<Map<String, Object>>();

        //---------------------------------------------------------------------------------------------------------
        // 랜딩페이지 목록을 조회한다.
        //---------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  campaignMasterMapper.getCampaignMasterForMbAdCaOne Start");
        System.out.println("----------------------------------------------------------------------------");

        Map<String, Object>       landingObj = new HashMap<String, Object>();
        landingObj = campaignMasterMapper.getCampaignMasterForMbAdCaOne(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
        );

        if(landingObj.isEmpty() || landingObj == null) {
            resultObj.put("status", false);
            resultObj.put("message", "랜딩페이지 규격 조회가 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");

            cpaResult.add(0, resultObj);
        }
        else {
            resultObj.put("status", true);
            resultObj.put("message", "정상적으로 조회되었습니다.");

            cpaResult.add(0, resultObj);
            cpaResult.add(1, landingObj);
        }

        System.out.println("리턴 메세지 : ["+ cpaResult.toString() +"]");

        return cpaResult;
    }
}