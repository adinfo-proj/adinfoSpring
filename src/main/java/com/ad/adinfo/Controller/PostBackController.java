package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.*;
import com.ad.adinfo.Mapper.AdExternalUrlMapper;
import com.ad.adinfo.Mapper.AdPostbackFormatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostBackController {
    private final AdPostbackFormatMapper adPostbackFormatMapper;
    private final AdExternalUrlMapper adExternalUrlMapper;

    @Autowired
    private PlatformTransactionManager trxManager;

    /*------------------------------------------------------------------------------------------------------------------
     * 신규 POSTBACK 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.13
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
    @RequestMapping(value = "/newSendPostback", method = RequestMethod.POST)
    public Map<String, Object> newSendPostback(
            NativeWebRequest nativeWebRequest,
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("newSendPostback Func Start...");
        System.out.println("############################################################################");

        String sAccessFlag = new String();
        String sUseTpArr   = new String();
        String sSendFlag   = new String();


        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_POSTBACK_FORMAT   tbAdPostbackFormat = new TB_AD_POSTBACK_FORMAT();
        ArrayList inputArr  = new ArrayList();
        ArrayList serverArr  = new ArrayList();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params.toString() + "]");

        inputArr  = (ArrayList)params.get("inputParam");
        serverArr = (ArrayList)params.get("serverParam");

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  트랜잭션 Start");
        System.out.println("----------------------------------------------------------------------------");
        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        tbAdPostbackFormat.setClntId(params.get("clntId").toString());
        tbAdPostbackFormat.setMbId(Long.parseLong(params.get("mbId").toString()));
        tbAdPostbackFormat.setAdId(Long.parseLong(params.get("adId").toString()));
        tbAdPostbackFormat.setCaId(Long.parseLong(params.get("caId").toString()));
        tbAdPostbackFormat.setPgId(Long.parseLong(params.get("pgId").toString()));

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.getPostbackMaxpbId Start");
        System.out.println("----------------------------------------------------------------------------");

        Long newPbId = adPostbackFormatMapper.getPostbackMaxpbId(
                  tbAdPostbackFormat.getMbId()
                , tbAdPostbackFormat.getAdId()
                , tbAdPostbackFormat.getCaId()
                , tbAdPostbackFormat.getPgId()
        );
        System.out.println("Max PB_ID : [" + newPbId + "]");
        newPbId = (newPbId == null) ? 10000L : newPbId + 1L;

        tbAdPostbackFormat.setPbId       (newPbId);
        tbAdPostbackFormat.setStatus     ("00");
        tbAdPostbackFormat.setPostbackIo ("O");
        tbAdPostbackFormat.setPostbackUrl(params.get("sendUrl").toString());
        tbAdPostbackFormat.setSendType   (params.get("postBack").toString());
        tbAdPostbackFormat.setSslYn      (params.get("encrypt").toString());

        //------------------------------------------------------------------------------
        // 헤더값 전송 정보 처리
        //------------------------------------------------------------------------------
        for(int i = 0 ; i < serverArr.size(); i++) {
            Map<String, Object> inputServer = new HashMap<String, Object>();
            inputServer = (Map<String, Object>) serverArr.get(i);

            inputServer.get("memberId").toString();

            switch(i) {
                case 0: tbAdPostbackFormat.setHttpUserAgent(inputServer.get("memberId").toString());
                        break;
                case 1: tbAdPostbackFormat.setRemoteAddr(inputServer.get("memberId").toString());
                        break;
                case 2: tbAdPostbackFormat.setHttpReferer(inputServer.get("memberId").toString());
                        break;
                case 3: tbAdPostbackFormat.setHttpHost(inputServer.get("memberId").toString());
                        break;
                case 4: tbAdPostbackFormat.setRequestUri(inputServer.get("memberId").toString());
                        break;
            }

            if(inputServer.get("useYn").toString().equals("true"))
                sUseTpArr += "Y";
            else
                sUseTpArr += "N";
        }

        //------------------------------------------------------------------------------
        // 수집데이터 전송 정보 처리
        //------------------------------------------------------------------------------
        for(int i = 0 ; i < inputArr.size(); i++) {
            Map<String, Object> inputValue = new HashMap<String, Object>();
            inputValue = (Map<String, Object>) inputArr.get(i);

            //------------------------------------------------------------------------------
            // 고정값 여부
            //   - sAccessFlag값이 Y이면 고정값, N이면 DB데이터값을 전송한다.
            //     S값이면 사용안함임.
            //------------------------------------------------------------------------------
            if (inputValue.get("tp").toString().equals("Y"))
                sAccessFlag += "Y";
            else
                sAccessFlag += "N";

            //------------------------------------------------------------------------------
            // 전송 여부
            //   - sSendFlag값이 Y이면 실제 전송하고, N이면 발송하지 않는다.
            //     S값이면 사용안함 임.
            //------------------------------------------------------------------------------
            if(inputValue.get("useYn").toString().equals("true")) {
                sSendFlag  += "Y";
            }
            else {
                sSendFlag  += "N";
            }

            switch (i) {
                case 0:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=1");
                        }
                        break;
                case 1:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=2");
                        }
                        break;
                case 2:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=3");
                        }
                        break;
                case 3:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=4");
                        }
                        break;
                case 4:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=5");
                        }
                        break;
                case 5:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=6");
                        }
                        break;
                case 6:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=7");
                        }
                        break;
                case 7:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=8");
                        }
                        break;
                case 8:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=9");
                        }
                        break;
                case 9:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=10");
                        }
                        break;
                case 10:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue11(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue11(inputValue.get("memberId").toString() + "=11");
                        }
                        break;
                case 11:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue12(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue12(inputValue.get("memberId").toString() + "=12");
                        }
                        break;
                case 12:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue13(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue13(inputValue.get("memberId").toString() + "=13");
                        }
                        break;
                case 13:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue14(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue14(inputValue.get("memberId").toString() + "=14");
                        }
                        break;
                case 14:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue15(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue15(inputValue.get("memberId").toString() + "=15");
                        }
                        break;
                case 15:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue16(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue16(inputValue.get("memberId").toString() + "=16");
                        }
                        break;
                case 16:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue17(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue17(inputValue.get("memberId").toString() + "=17");
                        }
                        break;
                case 17:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue18(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue18(inputValue.get("memberId").toString() + "=18");
                        }
                        break;
                case 18:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue19(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue19(inputValue.get("memberId").toString() + "=19");
                        }
                        break;
                case 19:
                        if (inputValue.get("tp").toString().equals("Y")) {
                            tbAdPostbackFormat.setValue20(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                        } else {
                            tbAdPostbackFormat.setValue20(inputValue.get("memberId").toString() + "=20");
                        }
                        break;
            }
        }

        tbAdPostbackFormat.setAccessFlag(sAccessFlag);
        for(int i = 0 ; i < 20 - inputArr.size(); i++) {
            sAccessFlag += "S";
            sSendFlag   += "S";

        }
        tbAdPostbackFormat.setSendFlag  (sSendFlag);
        tbAdPostbackFormat.setAccessFlag(sAccessFlag);

        for(int i = 0 ; i < 20 - serverArr.size(); i++) {
            sUseTpArr += "S";
        }
        tbAdPostbackFormat.setUseTpArr  (sUseTpArr);

        Long    rets = 0L;
        try {
            rets = adPostbackFormatMapper.insPostback(tbAdPostbackFormat);
        } catch(Exception e) {
            System.out.println("adPostbackFormatMapper.insPostback Fail : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "신규 포스트백 등록이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("result", true);
        resultMap.put("message", "정상적으로 신규 포스트백이 등록되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * POSTBACK 변경
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.14
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
    @RequestMapping(value = "/updSendPostback", method = RequestMethod.POST)
    public Map<String, Object> updSendPostback(
            NativeWebRequest nativeWebRequest,
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("updSendPostback Func Start...");
        System.out.println("############################################################################");

        String sAccessFlag = new String();
        String sUseTpArr   = new String();
        String sSendFlag   = new String();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_POSTBACK_FORMAT   tbAdPostbackFormat = new TB_AD_POSTBACK_FORMAT();
        ArrayList inputArr  = new ArrayList();
        ArrayList serverArr  = new ArrayList();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params.toString() + "]");

        inputArr  = (ArrayList)params.get("inputParam");
        serverArr = (ArrayList)params.get("serverParam");

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  트랜잭션 Start");
        System.out.println("----------------------------------------------------------------------------");
        TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        tbAdPostbackFormat.setClntId(params.get("clntId").toString());
        tbAdPostbackFormat.setMbId(Long.parseLong(params.get("mbId").toString()));
        tbAdPostbackFormat.setAdId(Long.parseLong(params.get("adId").toString()));
        tbAdPostbackFormat.setCaId(Long.parseLong(params.get("caId").toString()));
        tbAdPostbackFormat.setPgId(Long.parseLong(params.get("pgId").toString()));
        tbAdPostbackFormat.setPbId(Long.parseLong(params.get("pbId").toString()));
        tbAdPostbackFormat.setStatus     ("00");
        tbAdPostbackFormat.setPostbackIo ("O");
        tbAdPostbackFormat.setPostbackUrl(params.get("sendUrl").toString());
        tbAdPostbackFormat.setSendType   (params.get("postBack").toString());
        tbAdPostbackFormat.setSslYn      (params.get("encrypt").toString());

        //------------------------------------------------------------------------------
        // 헤더값 전송 정보 처리
        //------------------------------------------------------------------------------
        for(int i = 0 ; i < serverArr.size(); i++) {
            Map<String, Object> inputServer = new HashMap<String, Object>();
            inputServer = (Map<String, Object>) serverArr.get(i);

            inputServer.get("memberId").toString();

            switch(i) {
                case 0: tbAdPostbackFormat.setHttpUserAgent(inputServer.get("memberId").toString());
                    break;
                case 1: tbAdPostbackFormat.setRemoteAddr(inputServer.get("memberId").toString());
                    break;
                case 2: tbAdPostbackFormat.setHttpReferer(inputServer.get("memberId").toString());
                    break;
                case 3: tbAdPostbackFormat.setHttpHost(inputServer.get("memberId").toString());
                    break;
                case 4: tbAdPostbackFormat.setRequestUri(inputServer.get("memberId").toString());
                    break;
            }

            if(inputServer.get("useYn").toString().equals("true"))
                sUseTpArr += "Y";
            else
                sUseTpArr += "N";
        }

        //------------------------------------------------------------------------------
        // 수집데이터 전송 정보 처리
        //------------------------------------------------------------------------------
        for(int i = 0 ; i < inputArr.size(); i++) {
            Map<String, Object> inputValue = new HashMap<String, Object>();
            inputValue = (Map<String, Object>) inputArr.get(i);

            //------------------------------------------------------------------------------
            // 고정값 여부
            //   - sAccessFlag값이 Y이면 고정값, N이면 DB데이터값을 전송한다.
            //     S값이면 사용안함임.
            //------------------------------------------------------------------------------
            if (inputValue.get("tp").toString().equals("Y"))
                sAccessFlag += "Y";
            else
                sAccessFlag += "N";

            //------------------------------------------------------------------------------
            // 전송 여부
            //   - sSendFlag값이 Y이면 실제 전송하고, N이면 발송하지 않는다.
            //     S값이면 사용안함 임.
            //------------------------------------------------------------------------------
            if(inputValue.get("useYn").toString().equals("true")) {
                sSendFlag  += "Y";
            }
            else {
                sSendFlag  += "N";
            }

            switch (i) {
                case 0:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue01(inputValue.get("memberId").toString() + "=1");
                    }
                    break;
                case 1:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue02(inputValue.get("memberId").toString() + "=2");
                    }
                    break;
                case 2:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue03(inputValue.get("memberId").toString() + "=3");
                    }
                    break;
                case 3:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue04(inputValue.get("memberId").toString() + "=4");
                    }
                    break;
                case 4:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue05(inputValue.get("memberId").toString() + "=5");
                    }
                    break;
                case 5:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue06(inputValue.get("memberId").toString() + "=6");
                    }
                    break;
                case 6:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue07(inputValue.get("memberId").toString() + "=7");
                    }
                    break;
                case 7:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue08(inputValue.get("memberId").toString() + "=8");
                    }
                    break;
                case 8:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue09(inputValue.get("memberId").toString() + "=9");
                    }
                    break;
                case 9:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue10(inputValue.get("memberId").toString() + "=10");
                    }
                    break;
                case 10:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue11(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue11(inputValue.get("memberId").toString() + "=11");
                    }
                    break;
                case 11:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue12(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue12(inputValue.get("memberId").toString() + "=12");
                    }
                    break;
                case 12:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue13(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue13(inputValue.get("memberId").toString() + "=13");
                    }
                    break;
                case 13:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue14(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue14(inputValue.get("memberId").toString() + "=14");
                    }
                    break;
                case 14:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue15(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue15(inputValue.get("memberId").toString() + "=15");
                    }
                    break;
                case 15:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue16(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue16(inputValue.get("memberId").toString() + "=16");
                    }
                    break;
                case 16:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue17(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue17(inputValue.get("memberId").toString() + "=17");
                    }
                    break;
                case 17:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue18(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue18(inputValue.get("memberId").toString() + "=18");
                    }
                    break;
                case 18:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue19(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue19(inputValue.get("memberId").toString() + "=19");
                    }
                    break;
                case 19:
                    if (inputValue.get("tp").toString().equals("Y")) {
                        tbAdPostbackFormat.setValue20(inputValue.get("memberId").toString() + "=" + inputValue.get("memberValue").toString());
                    } else {
                        tbAdPostbackFormat.setValue20(inputValue.get("memberId").toString() + "=20");
                    }
                    break;
            }
        }

        tbAdPostbackFormat.setAccessFlag(sAccessFlag);
        for(int i = 0 ; i < 20 - inputArr.size(); i++) {
            sAccessFlag += "S";
            sSendFlag   += "S";

        }
        tbAdPostbackFormat.setSendFlag  (sSendFlag);
        tbAdPostbackFormat.setAccessFlag(sAccessFlag);

        for(int i = 0 ; i < 20 - serverArr.size(); i++) {
            sUseTpArr += "S";
        }
        tbAdPostbackFormat.setUseTpArr  (sUseTpArr);

        Long    rets = 0L;
        try {
            rets = adPostbackFormatMapper.updPostback(tbAdPostbackFormat);
        } catch(Exception e) {
            System.out.println("adPostbackFormatMapper.updPostback Fail : [" + e + "]");

            resultMap.put("result", false);
            resultMap.put("message", "포스트백 변경이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("result", true);
        resultMap.put("message", "정상적으로 포스트백이 변경되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포트스백 리스트 (캠페인번호로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_POSTBACK_FORMAT
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetSelPostbackList", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Map<String, Object>> GetSelPostbackList(HttpServletRequest rq) throws Exception {
        List<Map<String, Object>> resultObj = new ArrayList<Map<String, Object>>();

        System.out.println("\n\n############################################################################");
        System.out.println("GetSelPostbackList Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId   : [" + rq.getParameter("pgId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.selPostbackList Start");
        System.out.println("----------------------------------------------------------------------------");

        resultObj = adPostbackFormatMapper.selPostbackList(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
                , Long.parseLong(rq.getParameter("pgId"))
        );

        System.out.println("리턴 메세지 : ["+ resultObj.toString() +"]");

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포트스백 리스트 (캠페인번호로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2021.12.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_POSTBACK_FORMAT
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetSelPostbackOne", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> GetSelPostbackOne(HttpServletRequest rq) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("\n\n############################################################################");
        System.out.println("GetSelPostbackOne Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId   : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId   : [" + rq.getParameter("adId") + "]");
        System.out.println("caId   : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId   : [" + rq.getParameter("pgId") + "]");
        System.out.println("pbId   : [" + rq.getParameter("pbId") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.selPostbackOne Start");
        System.out.println("----------------------------------------------------------------------------");

        resultMap = adPostbackFormatMapper.selPostbackOne(
                  Long.parseLong(rq.getParameter("mbId"))
                , Long.parseLong(rq.getParameter("adId"))
                , Long.parseLong(rq.getParameter("caId"))
                , Long.parseLong(rq.getParameter("pgId"))
                , Long.parseLong(rq.getParameter("pbId"))
        );

        System.out.println("리턴 메세지 : ["+ resultMap.toString() +"]");

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포스트백 삭제 (상태를 변경함).
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.14
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] AD_POSTBACK_FORMAT
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "ChangePostbackStatus", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> ChangePostbackStatus(HttpServletRequest    rq) throws Exception {
        System.out.println("\n\n############################################################################");
        System.out.println("ChangePostbackStatus Func Start...");
        System.out.println("############################################################################");

        Long lReturn = -1L;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("clntId   : [" + rq.getParameter("clntId") + "]");
        System.out.println("campName : [" + rq.getParameter("campName") + "]");
        System.out.println("mbId     : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId     : [" + rq.getParameter("adId") + "]");
        System.out.println("caId     : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId     : [" + rq.getParameter("pgId") + "]");
        System.out.println("pbId     : [" + rq.getParameter("pbId") + "]");


        System.out.println("status   : [" + rq.getParameter("status") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.changeStatusPostbackMaster Start");
        System.out.println("----------------------------------------------------------------------------");
        try {
            lReturn = adPostbackFormatMapper.changeStatusPostbackMaster(
                    Long.parseLong(rq.getParameter("mbId")),
                    Long.parseLong(rq.getParameter("adId")),
                    Long.parseLong(rq.getParameter("caId")),
                    Long.parseLong(rq.getParameter("pgId")),
                    Long.parseLong(rq.getParameter("pbId")),
                    rq.getParameter("status")
            );

            System.out.println("lReturn : " + lReturn);

            if( lReturn <= 0) {
                resultMap.put("status", false);
                resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");
            }
            else {
                resultMap.put("status", true);
                resultMap.put("comment", "캠페인의 상태를 변경하였습니다.");

//                adInfoUtil.InsAdOperationHistory("O"
//                        , Long.parseLong(rq.getParameter("mbId"))
//                        , rq.getParameter("clntId").toString()
//                        , "00"
//                        , "["+ rq.getParameter("campName").toString() +"] 캠페인의 상태를 변경하였습니다."
//                );
            }
        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
            resultMap.put("status", false);
            resultMap.put("comment", "시스템 오류로 관리자에게 연락바랍니다.");
        }

        System.out.println("리턴 데이터 : ["+ resultMap.toString() +"]");

        return resultMap;
    }









































    /*------------------------------------------------------------------------------------------------------------------
     * 외부 도메인 연결 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.18
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] AD_EXTERNAL_USER
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/CreExternalUrl", method = RequestMethod.POST)
    public Map<String, Object> CreExternalUrl(
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception
    {
        System.out.println("\n\n############################################################################");
        System.out.println("CreExternalUrl Func Start...");
        System.out.println("############################################################################");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        TB_AD_EXTERNAL_URL tbAdExternalUrl = new TB_AD_EXTERNAL_URL();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params.toString() + "]");
        // mbId, adId, caId, pgId 까지만 받는다.

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
//        System.out.println("----------------------------------------------------------------------------");
//        System.out.println("  트랜잭션 Start");
//        System.out.println("----------------------------------------------------------------------------");
        //TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        tbAdExternalUrl.setMbId(Long.parseLong(params.get("mbId").toString()));
        tbAdExternalUrl.setAdId(Long.parseLong(params.get("adId").toString()));
        tbAdExternalUrl.setCaId(Long.parseLong(params.get("caId").toString()));
        tbAdExternalUrl.setPgId(Long.parseLong(params.get("pgId").toString()));
        tbAdExternalUrl.setExternalUrl        (params.get("externalUrl").toString());
        tbAdExternalUrl.setStatus             ("00");
        tbAdExternalUrl.setDescription        (params.get("description").toString());

        System.out.println(tbAdExternalUrl.toString());

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adExternalUserMapper.insAdExternalUrl Start");
        System.out.println("----------------------------------------------------------------------------");

        Long    newAddUrl = -1L;
        try {
            newAddUrl = adExternalUrlMapper.insAdExternalUrl(tbAdExternalUrl);
        } catch(Exception e) {
            System.out.println("adExternalUrlMapper.insAdExternalUrl Fail : [" + e + "]");

            resultMap.put("status", false);
            resultMap.put("message", "신규 외부 도메인 연결 신청이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            //trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("status", true);
        resultMap.put("message", "신규 외부 도메인 연결 신청이 처리되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        //trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 외부 도메인 연결 해제
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.21
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] AD_EXTERNAL_USER
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/ModifyExternalUrl", method = RequestMethod.POST)
    public Map<String, Object> ModifyExternalUrl(
            @RequestPart(value = "dataObj") Map<String, Object> params) throws Exception
    {
        System.out.println("\n\n############################################################################");
        System.out.println("ModifyExternalUrl Func Start...");
        System.out.println("############################################################################");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("입력 파라메터 : [" + params.toString() + "]");
        // mbId, adId, caId, pgId 까지만 받는다.

        //------------------------------------------------------------------------------
        // 트랜잭션 시작
        //------------------------------------------------------------------------------
//        System.out.println("----------------------------------------------------------------------------");
//        System.out.println("  트랜잭션 Start");
//        System.out.println("----------------------------------------------------------------------------");
        //TransactionStatus trxStatus = trxManager.getTransaction(new DefaultTransactionDefinition());

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adExternalUrlMapper.updAdExternalUrl Start");
        System.out.println("----------------------------------------------------------------------------");

        Long    newAddUrl = -1L;
        try {
            newAddUrl = adExternalUrlMapper.updAdExternalUrl(
                      Long.parseLong(params.get("mbId").toString())
                    , Long.parseLong(params.get("adId").toString())
                    , Long.parseLong(params.get("caId").toString())
                    , Long.parseLong(params.get("pgId").toString())
                    , params.get("status").toString()
            );
        } catch(Exception e) {
            System.out.println("adExternalUrlMapper.updAdExternalUrl Fail : [" + e + "]");

            resultMap.put("status", false);
            resultMap.put("message", "외부 도메인 연결 해제 신청이 실패되었습니다.\n\n고객센터에 문의주세요.\n\nTel : 1533-3757");
            System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

            //trxManager.rollback(trxStatus);
            return resultMap;
        }

        resultMap.put("status", true);
        resultMap.put("message", "외부 도메인이 연결 해제 처리되었습니다.");
        System.out.println("처리 메세지 : [" + resultMap.toString() + "]");

        //trxManager.commit(trxStatus);

        return resultMap;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 포트스백 전송 결과 (캠페인번호로 조회)
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.02.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] AD_POSTBACK_FORMAT
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "GetSelPostbackResult", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ArrayList<List<Map<String, Object>>> GetSelPostbackResult(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> userResult = new ArrayList<>();

        System.out.println("\n\n############################################################################");
        System.out.println("GetSelPostbackResult Func Start...");
        System.out.println("############################################################################");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  화면에서 수신된 입력값");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("mbId         : [" + rq.getParameter("mbId") + "]");
        System.out.println("adId         : [" + rq.getParameter("adId") + "]");
        System.out.println("caId         : [" + rq.getParameter("caId") + "]");
        System.out.println("pgId         : [" + rq.getParameter("pgId") + "]");
        System.out.println("resultSelect : [" + rq.getParameter("resultSelect") + "]");

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.selPostbackResultRowCount Start");
        System.out.println("----------------------------------------------------------------------------");

        System.out.println("resultSelect : [" + rq.getParameter("resultSelect") + "]");

        //------------------------------------------------------------------------------
        // 페이지 처리용 총 건수 조회
        //------------------------------------------------------------------------------
        List<Map<String, Object>> pageObj = new ArrayList<Map<String, Object>>();

        if(rq.getParameter("resultSelect").toString().equals("-1")) {
            pageObj = adPostbackFormatMapper.selPostbackResultRowCount0(
                      Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , Long.parseLong(rq.getParameter("pgId"))
            );
        }
        else if(rq.getParameter("resultSelect").toString().equals("0")) {
            pageObj = adPostbackFormatMapper.selPostbackResultRowCount1(
                    Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , Long.parseLong(rq.getParameter("pgId"))
            );
        }
        else {
            pageObj = adPostbackFormatMapper.selPostbackResultRowCount2(
                    Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , Long.parseLong(rq.getParameter("pgId"))
            );
        }

        userResult.add(0, pageObj);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("  adPostbackFormatMapper.selPostbackResultTp Start");
        System.out.println("----------------------------------------------------------------------------");

        //------------------------------------------------------------------------------
        // 실 데이터 조회
        //------------------------------------------------------------------------------
        List<Map<String, Object>> dataObj = new ArrayList<Map<String, Object>>();
        if(rq.getParameter("resultSelect").toString().equals("-1")) {
            dataObj = adPostbackFormatMapper.selPostbackResultTp0(
                    Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , Long.parseLong(rq.getParameter("pgId"))
            );
        }
        else if(rq.getParameter("resultSelect").toString().equals("0")) {
            dataObj = adPostbackFormatMapper.selPostbackResultTp1(
                    Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , Long.parseLong(rq.getParameter("pgId"))
            );
        }
        else {
            dataObj = adPostbackFormatMapper.selPostbackResultTp2(
                    Long.parseLong(rq.getParameter("mbId"))
                    , Long.parseLong(rq.getParameter("adId"))
                    , Long.parseLong(rq.getParameter("caId"))
                    , Long.parseLong(rq.getParameter("pgId"))
            );
        }

        userResult.add(1, dataObj);

        System.out.println("리턴 메세지 : ["+ userResult.toString() +"]");

        return userResult;
    }
}