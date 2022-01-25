package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.NOTIFY_BOARD;
import com.ad.adinfo.Domain.INPROVE_BOARD;
import com.ad.adinfo.Domain.ASK_BOARD;

import com.ad.adinfo.Mapper.NotifyBoardMapper;
import com.ad.adinfo.Mapper.AskBoardMapper;
import com.ad.adinfo.Mapper.InproveBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardContentsController {
    private final NotifyBoardMapper     notifyBoardMapper;
    private final AskBoardMapper        askBoardMapper;
    private final InproveBoardMapper    inproveBoardMapper;

    /*------------------------------------------------------------------------------------------------------------------
     * 공지사항 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.21
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] NOTIFY_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notify/titlelist", method = RequestMethod.GET)
    public List<Map<String, Object>> notify_titlelist(HttpServletRequest rq) throws Exception {
        return notifyBoardMapper.getNotifyTitleList(
                  Long.parseLong(rq.getParameter("seqNo"))
                , (Long.parseLong(rq.getParameter("curPage").toString()) - 1) * Long.parseLong(rq.getParameter("rowCount").toString())
                , Long.parseLong(rq.getParameter("rowCount")));
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 공지사항 내용 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] NOTIFY_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notify/contents", method = RequestMethod.GET)
    public ArrayList<List<Map<String, Object>>> notify_contents(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> notifyResult = new ArrayList<>();

        //---------------------------------------------------------------------------------------------------------
        // 조회건수를 1증가시킨다.
        //---------------------------------------------------------------------------------------------------------
        notifyBoardMapper.updNotifyContentsReadCount(Long.parseLong(rq.getParameter("seqNo")));

        //---------------------------------------------------------------------------------------------------------
        // 현재글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> currentList = new ArrayList<Map<String, Object>>();
        currentList =  notifyBoardMapper.getNotifyContents(Long.parseLong(rq.getParameter("seqNo")));

        notifyResult.add(0, currentList);

        //---------------------------------------------------------------------------------------------------------
        // 이전글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> beforeList = new ArrayList<Map<String, Object>>();
        beforeList = notifyBoardMapper.getNotifyTitleListBefore(Long.parseLong(rq.getParameter("seqNo")));
        notifyResult.add(1, beforeList);

        //---------------------------------------------------------------------------------------------------------
        // 다음글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> afterList = new ArrayList<Map<String, Object>>();
        afterList = notifyBoardMapper.getNotifyTitleListAfter(Long.parseLong(rq.getParameter("seqNo")));
        notifyResult.add(2, afterList);

        return notifyResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 공지사항 내용 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] NOTIFY_BOARD
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notify/create", method = RequestMethod.GET)
    public Long notify_create(HttpServletRequest rq) throws Exception {
        NOTIFY_BOARD notifyBoard = new NOTIFY_BOARD();

        notifyBoard.setClntId  (rq.getParameter("clntId"));
        notifyBoard.setUseTp   (rq.getParameter("useTp"));
        notifyBoard.setHead    (rq.getParameter("head"));
        notifyBoard.setTitle   (rq.getParameter("title"));
        notifyBoard.setContents(rq.getParameter("contents"));

        Long lCreateCount = notifyBoardMapper.insNotifyCreate(notifyBoard);
        System.out.println("공지사항이 "+ lCreateCount +"건 등록되었습니다.");

        return notifyBoard.getSeqNo();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 공지사항 내용 삭제
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] NOTIFY_BOARD
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notify/delete", method = RequestMethod.GET)
    public boolean notify_delete(HttpServletRequest rq) throws Exception {
        Long lReturn = notifyBoardMapper.delNotifyContents(Long.parseLong(rq.getParameter("seqNo")));
        return (lReturn > 0) ? true : false;
    }

    /*------------------------------------------------------------------------------------------------------------------
     *  공지사항 DB 페이지 건수 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.24
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
    @RequestMapping(value = "/GetNotifyForAllPageCount", method = RequestMethod.GET)
    public Map<String, Object> GetNotifyForAllPageCount(HttpServletRequest params) throws Exception {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        System.out.println("useTp : [" + params.getParameter("useTp") + "]");

        Long rowTotalCount = notifyBoardMapper.getNotifyRowTotalCount(params.getParameter("useTp"));
        resultObj.put("rowTotalCount", rowTotalCount);

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 문의하기 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.21
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] ASK_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/ask/titlelist", method = RequestMethod.GET)
    public List<Map<String, Object>> ask_titlelist(HttpServletRequest rq) throws Exception {
        return askBoardMapper.getAskTitleList(
                Long.parseLong(rq.getParameter("seqNo"))
                , (Long.parseLong(rq.getParameter("curPage").toString()) - 1) * Long.parseLong(rq.getParameter("rowCount").toString())
                , Long.parseLong(rq.getParameter("rowCount")));
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 문의하기 내용 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] ASK_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/ask/contents", method = RequestMethod.GET)
    public ArrayList<List<Map<String, Object>>> ask_contents(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> askResult = new ArrayList<>();

        //---------------------------------------------------------------------------------------------------------
        // 조회건수를 1증가시킨다.
        //---------------------------------------------------------------------------------------------------------
        askBoardMapper.updAskContentsReadCount(Long.parseLong(rq.getParameter("seqNo")));

        //---------------------------------------------------------------------------------------------------------
        // 현재글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> currentList = new ArrayList<Map<String, Object>>();
        currentList =  askBoardMapper.getAskContents(Long.parseLong(rq.getParameter("seqNo")));

        askResult.add(0, currentList);

        //---------------------------------------------------------------------------------------------------------
        // 이전글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> beforeList = new ArrayList<Map<String, Object>>();
        beforeList = askBoardMapper.getAskTitleListBefore(Long.parseLong(rq.getParameter("seqNo")));
        askResult.add(1, beforeList);

        //---------------------------------------------------------------------------------------------------------
        // 다음글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> afterList = new ArrayList<Map<String, Object>>();
        afterList = askBoardMapper.getAskTitleListAfter(Long.parseLong(rq.getParameter("seqNo")));
        askResult.add(2, afterList);

        return askResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 문의하기 내용 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] ASK_BOARD
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/ask/create", method = RequestMethod.GET)
    public Long ask_create(HttpServletRequest rq) throws Exception {
        ASK_BOARD askBoard = new ASK_BOARD();

        askBoard.setClntId  (rq.getParameter("clntId"));
        askBoard.setUseTp   (rq.getParameter("useTp"));
        askBoard.setHead    (rq.getParameter("head"));
        askBoard.setTitle   (rq.getParameter("title"));
        askBoard.setContents(rq.getParameter("contents"));

        return askBoardMapper.insAskCreate(askBoard);
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 문의하기 내용 삭제
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] ASK_BOARD
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/ask/delete", method = RequestMethod.GET)
    public boolean ask_delete(HttpServletRequest rq) throws Exception {
        Long lReturn = askBoardMapper.delAskContents(Long.parseLong(rq.getParameter("seqNo")));
        return (lReturn > 0) ? true : false;
    }

    /*------------------------------------------------------------------------------------------------------------------
     *  문의하기 DB 페이지 건수 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.24
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
    @RequestMapping(value = "/GetAskForAllPageCount", method = RequestMethod.GET)
    public Map<String, Object> GetAskForAllPageCount(HttpServletRequest params) throws Exception {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        System.out.println("useTp : [" + params.getParameter("useTp") + "]");

        Long rowTotalCount = askBoardMapper.getAskRowTotalCount(params.getParameter("useTp"));
        resultObj.put("rowTotalCount", rowTotalCount);

        return resultObj;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 개선요청하기 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.21
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] INPROVE_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/inprove/titlelist", method = RequestMethod.GET)
    public List<Map<String, Object>> inprove_titlelist(HttpServletRequest rq) throws Exception {
        return inproveBoardMapper.getInproveTitleList(
                Long.parseLong(rq.getParameter("seqNo"))
                , (Long.parseLong(rq.getParameter("curPage").toString()) - 1) * Long.parseLong(rq.getParameter("rowCount").toString())
                , Long.parseLong(rq.getParameter("rowCount")));
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 개선요청하기 내용 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] INPROVE_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/inprove/contents", method = RequestMethod.GET)
    public ArrayList<List<Map<String, Object>>> inprove_contents(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> inproveResult = new ArrayList<>();

        //---------------------------------------------------------------------------------------------------------
        // 조회건수를 1증가시킨다.
        //---------------------------------------------------------------------------------------------------------
        inproveBoardMapper.updInproveContentsReadCount(Long.parseLong(rq.getParameter("seqNo")));

        //---------------------------------------------------------------------------------------------------------
        // 현재글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> currentList = new ArrayList<Map<String, Object>>();
        currentList =  inproveBoardMapper.getInproveContents(Long.parseLong(rq.getParameter("seqNo")));

        inproveResult.add(0, currentList);

        //---------------------------------------------------------------------------------------------------------
        // 이전글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> beforeList = new ArrayList<Map<String, Object>>();
        beforeList = inproveBoardMapper.getInproveTitleListBefore(Long.parseLong(rq.getParameter("seqNo")));
        inproveResult.add(1, beforeList);

        //---------------------------------------------------------------------------------------------------------
        // 다음글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> afterList = new ArrayList<Map<String, Object>>();
        afterList = inproveBoardMapper.getInproveTitleListAfter(Long.parseLong(rq.getParameter("seqNo")));
        inproveResult.add(2, afterList);

        return inproveResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 개선요청하기 내용 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] INPROVE_BOARD
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/inprove/create", method = RequestMethod.GET)
    public Long inprove_create(HttpServletRequest rq) throws Exception {
        INPROVE_BOARD askBoard = new INPROVE_BOARD();

        askBoard.setClntId  (rq.getParameter("clntId"));
        askBoard.setUseTp   (rq.getParameter("useTp"));
        askBoard.setHead    (rq.getParameter("head"));
        askBoard.setTitle   (rq.getParameter("title"));
        askBoard.setContents(rq.getParameter("contents"));

        return inproveBoardMapper.insInproveCreate(askBoard);
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 개선요청하기 내용 삭제
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] INPROVE_BOARD
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/inprove/delete", method = RequestMethod.GET)
    public boolean inprove_delete(HttpServletRequest rq) throws Exception {
        Long lReturn = inproveBoardMapper.delInproveContents(Long.parseLong(rq.getParameter("seqNo")));
        return (lReturn > 0) ? true : false;
    }

    /*------------------------------------------------------------------------------------------------------------------
     *  개선요청하기 DB 페이지 건수 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.24
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
    @RequestMapping(value = "/GetInproveForAllPageCount", method = RequestMethod.GET)
    public Map<String, Object> GetInproveForAllPageCount(HttpServletRequest params) throws Exception {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        System.out.println("useTp : [" + params.getParameter("useTp") + "]");

        Long rowTotalCount = inproveBoardMapper.getInproveRowTotalCount(params.getParameter("useTp"));
        resultObj.put("rowTotalCount", rowTotalCount);

        return resultObj;
    }
}
