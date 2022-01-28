package com.ad.adinfo.Controller;

import com.ad.adinfo.Domain.NOTICE_BOARD;

import com.ad.adinfo.Mapper.NoticeBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardContentsController {
    private final NoticeBoardMapper     noticeBoardMapper;

    /*------------------------------------------------------------------------------------------------------------------
     * 게시판 내용 등록
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C] NOTICE_BOARD
     *         [R]
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notice/create", method = RequestMethod.GET)
    public Long notice_create(HttpServletRequest rq) throws Exception {
        NOTICE_BOARD noticeBoard = new NOTICE_BOARD();

        //---------------------------------------------------------------------------------------------------------
        // Max BodySeqNo를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        Long lMaxBodySeqNo = noticeBoardMapper.getNoticeMaxBodySeqNo(
                rq.getParameter("groupTp")
        );

        //---------------------------------------------------------------------------------------------------------
        // DB를 생성한다.
        //   - 본문은 COMMENT_SEQ_NO 값이 0으로 고정한다.
        //---------------------------------------------------------------------------------------------------------
        noticeBoard.setBodySeqNo   (lMaxBodySeqNo);
        noticeBoard.setCommentSeqNo(0L);
        noticeBoard.setClntId      (rq.getParameter("clntId"));
        noticeBoard.setGroupTp     (rq.getParameter("groupTp"));
        noticeBoard.setUseTp       ("R");
        noticeBoard.setBodyTp      ("M");
        noticeBoard.setHead        (rq.getParameter("head"));
        noticeBoard.setTitle       (rq.getParameter("title"));
        noticeBoard.setContents    (rq.getParameter("contents"));

        Long lCreateCount = noticeBoardMapper.insNoticeCreate(noticeBoard);
        System.out.println("공지사항이 "+ lCreateCount +"건 등록되었습니다.");

        return noticeBoard.getBodySeqNo();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 게시판 내용 변경
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.25
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] NOTICE_BOARD
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notice/update", method = RequestMethod.GET)
    public Long notice_update(HttpServletRequest rq) throws Exception {
        NOTICE_BOARD noticeBoard = new NOTICE_BOARD();

        noticeBoard.setBodySeqNo(Long.parseLong(rq.getParameter("seqNo")));
        noticeBoard.setClntId   (rq.getParameter("clntId"));
        noticeBoard.setUseTp    (rq.getParameter("useTp"));
        noticeBoard.setHead     (rq.getParameter("head"));
        noticeBoard.setTitle    (rq.getParameter("title"));
        noticeBoard.setContents (rq.getParameter("contents"));
        noticeBoard.setGroupTp  (rq.getParameter("groupTp"));

        System.out.println("공지사항이 변경 쓰따뚜~");
        Long lCreateCount = noticeBoardMapper.updNoticeCreate(noticeBoard);
        System.out.println("공지사항이 "+ lCreateCount +"건 변경되었습니다.");

        return noticeBoard.getBodySeqNo();
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 게시판 내용 삭제
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R]
     *         [U] NOTICE_BOARD
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notice/delete", method = RequestMethod.GET)
    public boolean notice_delete(HttpServletRequest rq) throws Exception {
        Long lReturn = noticeBoardMapper.delNoticeContents(
                Long.parseLong(rq.getParameter("seqNo"))
                , rq.getParameter("groupTp"));
        return (lReturn > 0) ? true : false;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 게시판 목록 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.21
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] NOTICE_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notice/titlelist", method = RequestMethod.GET)
    public ArrayList<List<Map<String, Object>>> notice_titlelist(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> noticeResult = new ArrayList<>();

        List<Map<String, Object>> currentCount = noticeBoardMapper.getNoticeRowTotalCount(
                  rq.getParameter("useTp")
                , rq.getParameter("groupTp")
        );
        noticeResult.add(0, currentCount);

        System.out.println("lCount : " + currentCount.toString());

        List<Map<String, Object>> currentList = noticeBoardMapper.getNoticeTitleList(
                  Long.parseLong(rq.getParameter("seqNo"))
                , rq.getParameter("groupTp")
                , rq.getParameter("useTp")
                , (Long.parseLong(rq.getParameter("curPage").toString()) - 1) * Long.parseLong(rq.getParameter("rowCount").toString())
                , Long.parseLong(rq.getParameter("rowCount")));
        noticeResult.add(1, currentList);

        return noticeResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     * 게시판 내용 조회
     *------------------------------------------------------------------------------------------------------------------
     * 작성일 : 2022.01.22
     * 작성자 : 박형준
     *------------------------------------------------------------------------------------------------------------------
     * 테이블 : [C]
     *         [R] NOTICE_BOARD
     *         [U]
     *         [D]
     *------------------------------------------------------------------------------------------------------------------
     * 코멘트 : 없음.
     -----------------------------------------------------------------------------------------------------------------*/
    @CrossOrigin
    @RequestMapping(value = "/notice/contents", method = RequestMethod.GET)
    public ArrayList<List<Map<String, Object>>> notice_contents(HttpServletRequest rq) throws Exception {
        ArrayList<List<Map<String, Object>>> noticeResult = new ArrayList<>();

        //---------------------------------------------------------------------------------------------------------
        // 조회건수를 1증가시킨다.
        //---------------------------------------------------------------------------------------------------------
        if( rq.getParameter("dataOnly").equals("N")) {
            Long lbodySeqNo = noticeBoardMapper.updNoticeContentsReadCount(
                    Long.parseLong(rq.getParameter("seqNo"))
                    , rq.getParameter("groupTp")
            );
        }

        //---------------------------------------------------------------------------------------------------------
        // 현재글 정보를 조회한다.
        //---------------------------------------------------------------------------------------------------------
        List<Map<String, Object>> currentList = new ArrayList<Map<String, Object>>();
        currentList =  noticeBoardMapper.getNoticeContents(
                  Long.parseLong(rq.getParameter("seqNo"))
                , rq.getParameter("groupTp")
                , rq.getParameter("useTp")
        );
        noticeResult.add(0, currentList);

        List<Map<String, Object>> beforeList = new ArrayList<Map<String, Object>>();
        if( rq.getParameter("dataOnly").equals("N")) {
            //---------------------------------------------------------------------------------------------------------
            // 이전글 정보를 조회한다.
            //---------------------------------------------------------------------------------------------------------
            beforeList = noticeBoardMapper.getNoticeTitleListBefore(
                    Long.parseLong(rq.getParameter("seqNo"))
                    , rq.getParameter("groupTp")
                    , rq.getParameter("useTp")
            );
        }
        noticeResult.add(1, beforeList);

        List<Map<String, Object>> afterList = new ArrayList<Map<String, Object>>();
        if( rq.getParameter("dataOnly").equals("N")) {
            //---------------------------------------------------------------------------------------------------------
            // 다음글 정보를 조회한다.
            //---------------------------------------------------------------------------------------------------------
            afterList = noticeBoardMapper.getNoticeTitleListAfter(
                    Long.parseLong(rq.getParameter("seqNo"))
                    , rq.getParameter("groupTp")
                    , rq.getParameter("useTp")
            );
        }

        noticeResult.add(2, afterList);

        return noticeResult;
    }

    /*------------------------------------------------------------------------------------------------------------------
     *  게시판 DB 페이지 건수 조회
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
    @RequestMapping(value = "/GetNoticeForAllPageCount", method = RequestMethod.GET)
    public Map<String, Object> GetNoticeForAllPageCount(HttpServletRequest rq) throws Exception {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        System.out.println("useTp : [" + rq.getParameter("useTp") + "]");

        List<Map<String, Object>> rowTotalCount = noticeBoardMapper.getNoticeRowTotalCount(
                rq.getParameter("useTp")
              , rq.getParameter("groupTp"));
        resultObj.put("rowTotalCount", rowTotalCount);

        return resultObj;
    }
}