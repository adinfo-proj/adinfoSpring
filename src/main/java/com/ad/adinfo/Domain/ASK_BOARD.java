package com.ad.adinfo.Domain;

import lombok.Data;

@Data
public class ASK_BOARD {
    private     Long        SeqNo;
    private     String      createDt;
    private     String      updateDt;

    private     String      clntId;
    private     String      useTp;
    private     String      head;
    private     String      title;
    private     String      contents;

    private     Long        readCount;
    private     Long        modifyCount;
}
