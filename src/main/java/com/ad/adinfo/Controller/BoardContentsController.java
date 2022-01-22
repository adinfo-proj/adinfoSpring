package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.NOTIFY_BOARD;
import com.ad.adinfo.Mapper.NotifyBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardContentsController {
    private final NotifyBoardMapper notifyBoardMapper;

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
        return notifyBoardMapper.getNotifyTitleList(Long.parseLong(rq.getParameter("seqNo")));
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

        return notifyBoardMapper.insNotifyCreate(notifyBoard);
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
}
