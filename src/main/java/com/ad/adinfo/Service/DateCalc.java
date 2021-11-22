package com.ad.adinfo.Service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@Service
public class DateCalc {
    public String DateInterval(Integer lIntervalDays) {

        //-------------------------------------------------------------------
        // 현재 일자를 구한다.
        //-------------------------------------------------------------------
        SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
        Date getDate = new Date();

System.out.println(String.valueOf(lIntervalDays));

        //-------------------------------------------------------------------
        // 입력받은 숫자만큼 이전일자로 조회한다.
        //-------------------------------------------------------------------
        if( lIntervalDays == 0) {
            return dateFormat.format(getDate);
        }
        else {
            Calendar cal = new GregorianCalendar(Locale.KOREA);
            cal.setTime(getDate);
            cal.add(Calendar.DATE, lIntervalDays);
            return dateFormat.format(cal.getTime());
        }
    }
}
