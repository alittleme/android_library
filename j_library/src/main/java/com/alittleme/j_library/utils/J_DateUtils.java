package com.alittleme.j_library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Guojie on 2018/1/3.
 * 关于时间封装的处理类
 */
public class J_DateUtils {
    //2018-01-09
    public final static String yyyy_MM_dd = "yyyy-MM-dd";

    /**
     * 获取现在的时间：
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getTodayDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = format.format(new Date());
        return nowTime;
    }

    /**
     * 获取当天日期
     *
     * @param strFormat
     * @return
     */
    public static String getTodayDateStr(String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        return format.format(new Date());
    }

    /**
     * 格式转换(yyyy-MM-dd HH:mm:ss 到其它格式)
     *
     * @param strDate   yyyy-MM-dd HH:mm:ss(2018-01-09 13:41:00)
     * @param strFormat
     * @return
     */
    public static Date getTodayDate(String strDate, String strFormat) {
        Date returnDate = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(strDate);
            format = new SimpleDateFormat(strFormat);
            returnDate = format.parse(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 格式转换(yyyy-MM-dd-HH:mm:ss 到Date格式)
     *
     * @param strDate
     * @return
     */
    public static Date getTodayDate(String strDate) {
        Date returnDate = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            returnDate = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }
}
