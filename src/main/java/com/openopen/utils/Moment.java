package com.openopen.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Moment {


    /**
     * 取的系統時間
     */
    public Date getSysdate() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//設定日期格式
        df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        Date date = df.parse(df.format(new Date()));

        return date;
    }
}
