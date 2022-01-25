package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.TB_LANDING_PAGE;
import com.ad.adinfo.Mapper.LandingPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
    private final LandingPageMapper landingPageMapper;

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
        Map<String, Object> resultObj = new HashMap<String, Object>();

        System.out.println("------------------ newlandingpage Start ----------------------------------");

        System.out.println("params : [" + params.toString() + "]");

        TB_LANDING_PAGE tbLandingPage = new TB_LANDING_PAGE();

        ArrayList   contentArr  = new ArrayList();
        ArrayList   textArr     = new ArrayList();
        ArrayList   formArr     = new ArrayList();
        ArrayList   inputArr    = new ArrayList();

        Integer contentCount = 0;
        Integer imgCount     = 0;
        Integer textCount    = 0;
        Integer formCount    = 0;

        contentArr  = (ArrayList)params.get("formType");
        textArr     = (ArrayList)params.get("textData");
        formArr     = (ArrayList)params.get("formData");

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
        Long lNewPgId = landingPageMapper.selLandingPageMaxCaId(
                  Long.parseLong(params.get("mbId").toString())
                , Long.parseLong(params.get("adId").toString())
                , Long.parseLong(params.get("mkId").toString())
                , Long.parseLong(params.get("caId").toString()) );
        tbLandingPage.setPgId(lNewPgId + 1);
        tbLandingPage.setUseTp("0");

        //---------------------------------------------------------------------------------------------------------
        // 동일한 랜딩페이지명이 있으면 등록이 불가하다.
        //---------------------------------------------------------------------------------------------------------
        Long lDupCount = landingPageMapper.selLandingPageDupName(
                  Long.parseLong(params.get("mbId").toString())
                , Long.parseLong(params.get("adId").toString())
                , Long.parseLong(params.get("mkId").toString())
                , Long.parseLong(params.get("caId").toString())
                ,                params.get("landingNm").toString());

        if( lDupCount > 0) {
            resultObj.put("status", "failuer");
            resultObj.put("comment", "동일한 랜딩페이지명이 존재합니다.");
            return resultObj;
        }
        else {
            tbLandingPage.setName(params.get("landingNm").toString());
            System.out.println("------------------ Step 04");
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

        System.out.println("params       : [" + params + "]");
        //System.out.println("upFile       : [" + upFile.get(0).getSize() + "]");

        //---------------------------------------------------------------------------------------------------------
        // php 파일을 생성한다.
        //   - 생성규칙 : "http://landing.dbmaster.co.kr/mb_id/caid/pg_id?....
        //---------------------------------------------------------------------------------------------------------
        String      indexPath = "";
        String      indexFullFileName = "";
        String      phpTag    = "";

        // Directory Create
        indexPath = "/WebFile/ad"
                  + "/" + params.get("mbId")
                  + "/" + params.get("adId")
                  + "/" + params.get("caId");

        // Main Directory
        Path    mainPath = Paths.get(indexPath);
        Files.createDirectories(mainPath);

        // Image Directory
        Path    imgPath = Paths.get(indexPath + "/img");

        System.out.println("imgPath : " + imgPath.toString());
        Files.createDirectories(imgPath);

        indexFullFileName = indexPath + "/index.php";

        OutputStream file = new FileOutputStream(indexFullFileName);

        //---------------------------------------------------------------------------------------------------------
        // PHP 기본 추가
        //---------------------------------------------------------------------------------------------------------
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/ad/top_php.php"), "UTF-8"));

        String strBuf;
        Integer     nRows = 1;
        while ((strBuf = reader.readLine()) != null) {
            phpTag += strBuf + "\n";

            if(nRows == 84)
                phpTag += params.get("landingNm") + "\n";

            nRows++;
        }
        reader.close();

        phpTag += "  <div class='landPrev'>";

        //---------------------------------------------------------------------------------------------------------
        // 헤더 추가
        //---------------------------------------------------------------------------------------------------------
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

                System.out.println("upFile       : [" + upFile.get(imgCount).getOriginalFilename() + "]");

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
                System.out.println("text.get       : [" + textArr.get(textCount) + "]");
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
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap    = (Map<String, Object>)formArr.get(formCount);

                phpTag += "\n    <div class='formPrev'>";
                phpTag += "\n      <div class='priBox'>";
                phpTag += "\n        <h6>" + resultMap.get("priNm") + "</h6>";
                phpTag += "\n        <div>" + resultMap.get("priCon") + "</div>";
                phpTag += "\n        <button>확인</button>";
                phpTag += "\n      </div>";
                phpTag += "\n      <form method='post'>";
                phpTag += "\n        <input type='hidden' name='mbId' value='<?php echo $mbId?>'>";
                phpTag += "\n        <input type='hidden' name='adId' value='<?php echo $adId?>'>";
                phpTag += "\n        <input type='hidden' name='caId' value='<?php echo $caId?>'>";
                phpTag += "\n        <input type='hidden' name='mkId' value='<?php echo $mkId?>'>";
                phpTag += "\n        <input type='hidden' name='pgId' value='<?php echo $pgId?>'>";
                phpTag += "\n        <input type='text' name='value01' placeholder='이름을 입력하세요.'>";
                phpTag += "\n        <input type='text' name='value02' placeholder='연락처 '-'없이 입력해주세요.'>";
                phpTag += "\n";

                for(int j = 0 ; j < inputArr.size() ; j++) {
                    Map<String, Object> arrGab = new HashMap<String, Object>();
                    arrGab = (Map<String, Object>)inputArr.get(j);

                    //System.out.println("arrGab : " + arrGab.toString());

                    //-------------------------------------------------------------------
                    // html 테그중 name을 설정하기 위해 문자열을 만든다.
                    //-------------------------------------------------------------------
                    String namevalue = "";
                    if(j == 9) {
                        namevalue = "value"  + (j + 3);
                    }
                    else {
                        namevalue = "value0" + (j + 3);
                    }

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
                        phpTag += "\n        <input type='text' name='" + namevalue + "' placeholder='" + arrGab.get("names") + "'>";
                    }
                    //-------------------------------------------------------------------
                    // 라디오 박스
                    //-------------------------------------------------------------------
                    else if(arrGab.get("values").equals("radioForm")) {
                        String[] strArr    = arrGab.get("lab").toString().split(",");
                        String   formNames = arrGab.get("names").toString();

                        phpTag += "\n        <div class='formInput'>";
                        phpTag += "\n          <span class='fornInputName'>" + formNames + "</span>";

                        for(int k = 0 ; k < strArr.length; k++) {
                            phpTag += "\n          <input type='radio' name='" + namevalue + "' id='" + strArr[k] + "'>";
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

                        phpTag += "\n        <div class='formInput'>";
                        phpTag += "\n          <span class='fornInputName'>" + formNames + "</span>";

                        for(int k = 0 ; k < strArr.length; k++) {
                            phpTag += "\n          <input type='checkbox' name='" + namevalue + "' id='" + strArr[k] + "'>";
                            phpTag += "\n          <label for='" + strArr[k] + "'>" + strArr[k] + "</label>";
                        }
                        phpTag += "\n        </div>";
                    }
                    //-------------------------------------------------------------------
                    // 셀렉트 박스
                    //-------------------------------------------------------------------
                    else { // if(arrGab.get("values").equals("selForm")) {
                        String[] strArr    = arrGab.get("lab").toString().split(",");
                        String   formNames = arrGab.get("names").toString();

                        phpTag += "\n        <div class='formInput'>";
                        phpTag += "\n          <span class='fornInputName'>" + formNames + "</span>";
                        phpTag += "\n          <select name='" + namevalue + "'>";

                        for(int k = 0 ; k < strArr.length; k++) {
                            phpTag += "\n          <option value='" + strArr[k] + "'>" + strArr[k] + "</option>";
                        }
                        phpTag += "\n          </select>";
                        phpTag += "\n        </div>";
                    }
                }

                phpTag += "\n        <input type='checkbox' name='agree01' id='agree01'>";
                phpTag += "\n        <label for='agree01'>개인정보 수집 동의 <span>[보러가기]</span></label>";

                phpTag += "\n        <div class='centerBox'>";
                phpTag += "\n          <button style='background: " + resultMap.get("btnColor").toString() + "; ";
                phpTag += "border-radius: " + resultMap.get("btmShape").toString() +  ";'>";
                phpTag += resultMap.get("btnNm") + "</button>";
                phpTag += "\n        </div>";
                phpTag += "\n      </form>";
                phpTag += "\n    </div>";

                switch(i) {
                    case 0 : tbLandingPage.setPage01("aass"); break;
                    case 1 : tbLandingPage.setPage02("aass"); break;
                    case 2 : tbLandingPage.setPage03("aass"); break;
                    case 3 : tbLandingPage.setPage04("aass"); break;
                    case 4 : tbLandingPage.setPage05("aass"); break;
                    case 5 : tbLandingPage.setPage06("aass"); break;
                    case 6 : tbLandingPage.setPage07("aass"); break;
                    case 7 : tbLandingPage.setPage08("aass"); break;
                    case 8 : tbLandingPage.setPage09("aass"); break;
                    case 9 : tbLandingPage.setPage10("aass"); break;
                }

                formCount++;
            }
        }

        //-------------------------------------------------------------------
        // 푸터 추가
        //-------------------------------------------------------------------
        phpTag += "\n    <div class='bgColor'></div>";
        phpTag += "\n  </body>";
        phpTag += "\n</html>";

        //-------------------------------------------------------------------
        // 최종 저장
        //-------------------------------------------------------------------
        byte[] by=phpTag.getBytes();
        file.write(by);
        file.close();

        System.out.println("------------------ newlandingpage Create Finish ---------------------------------");

        System.out.println("------------------ newlandingpage DB Start ---------------------------------");
        landingPageMapper.insLandingPage(tbLandingPage);

        System.out.println("------------------ newlandingpage DB Finish ---------------------------------");

        resultObj.put("status", "success");
        resultObj.put("comment", "신규 랜딩페이지가 생성되었습니다.");
        return resultObj;
    }
}