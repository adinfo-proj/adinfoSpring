package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class NOTICE_BOARD {
    private     Long        bodySeqNo;
    private     Long        commentSeqNo;

    private     String      createDt;
    private     String      updateDt;

    private     String      clntId;

    private     String      groupTp;
    private     String      useTp;
    private     String      bodyTp;

    private     String      head;
    private     String      title;
    private     String      contents;

    private     Long        readCount;
    private     Long        modifyCount;
}
